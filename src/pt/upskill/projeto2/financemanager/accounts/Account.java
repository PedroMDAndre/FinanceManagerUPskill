package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.FileDirPath;
import pt.upskill.projeto2.financemanager.accounts.formats.FileAccountFormat;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.financemanager.filters.BeforeDateSelector;
import pt.upskill.projeto2.financemanager.filters.CurrentYearDateSelector;
import pt.upskill.projeto2.financemanager.filters.Filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Account {
    //private
    private Date infoDate;          // Data da informação
    private long id;                // Nº da conta
    private String currency;        // Moeda
    private String accountName;     // Nome da conta
    private String type;            // Tipo de conta
    private Date startDate;         // Data do primeiro movimento registado
    private Date endDate;           // Data do último movimento registado
    private String additionalInfo;  // Info adicional
    private ArrayList<StatementLine> statementLines;


    // Constructor
    public Account() {
    }

    public Account(long id, String accountName, String type) {
        this.infoDate = new Date();
        this.id = id;
        this.currency = "";
        this.accountName = accountName;
        this.type = type;
        this.startDate = null;
        this.endDate = null;
        this.additionalInfo = "";
        this.statementLines = new ArrayList<>();
    }

    // Class Methods
    public static Account newAccount(File file) throws UnknownAccountException {
        Account newAccount = null;

        try {
            long id = 0;
            String currency = "";
            String accountName = "";
            String type = "";
            Date startDate = null;
            Date endDate = null;
            String additionalInfo = "";
            ArrayList<StatementLine> statementLines = new ArrayList<>();

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String lineOfData = fileScanner.nextLine();
                String[] dataTokens = lineOfData.split(";");    // create list of values


                if (dataTokens[0].contains("Date") && dataTokens.length > 4) {

                } else if (dataTokens[0].contains("Account Info")) {

                } else if (dataTokens[0].contains("Account")) {
                    try {
                        id = Long.parseLong(dataTokens[1].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Número de conta inválido");
                        throw new UnknownAccountException();
                    }

                    currency = dataTokens[2].trim();
                    accountName = dataTokens[3].trim();
                    type = dataTokens[4].trim();
                    if (dataTokens.length == 6) {
                        additionalInfo = dataTokens[5];
                    }

                } else if (dataTokens.length == 7) {
                    // Lê o valor de cada linha de StatementLine e adiciona à lista
                    Category category = null;
                    if (type.equals("SavingsAccount")) {
                        category = SavingsAccount.savingsCategory;
                    }
                    try {
                        StatementLine line = StatementLine.newStatementLine(lineOfData, category);
                        statementLines.add(line);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Não foi possível ler uma linha de Movimentos da conta: " + accountName);
                        System.out.println(e.getMessage());
                    }
                }
            }

            fileScanner.close();

            statementLines.sort(null);

            if (statementLines != null && statementLines.size() != 0) {
                startDate = statementLines.get(0).getDate();
                endDate = statementLines.get(statementLines.size() - 1).getDate();
            }

            // Cria conta com base no tipo

            if (type.equals("SavingsAccount")) {
                newAccount = new SavingsAccount(id, accountName);
            } else if (type.equals("DraftAccount")) {
                newAccount = new DraftAccount(id, accountName);
            } else {
                throw new UnknownAccountException();
            }

            newAccount.setCurrency(currency);
            newAccount.setAdditionalInfo(additionalInfo);
            newAccount.setStartDate(startDate);
            newAccount.setEndDate(endDate);
            newAccount.setStatementLines(statementLines);

        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível criar conta. O ficheiro de entrada não foi encontrado.");
        }

        return newAccount;
    }

    public static void save(List<Account> accountList) {
        String dirSavePath = FileDirPath.dirSavePath;

        for (Account account : accountList) {
            String fileSavePath = dirSavePath + account.getId() + ".csv";
            File file = new File(fileSavePath);
            try {
                FileAccountFormat fileAccountFormat = new FileAccountFormat();
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(fileAccountFormat.format(account));
                printWriter.close();
                System.out.println("Contas guardadas.");
            } catch (FileNotFoundException e) {
                System.out.println("Não foi possível salver a conta: " + account.getId());
            }
        }
    }

    public static Date stringToDate(String stringDate, String separator) {
        String[] dateTokens = stringDate.split(separator);
        int day = Integer.parseInt(dateTokens[0]);
        int month = Integer.parseInt(dateTokens[1]);
        int year = Integer.parseInt(dateTokens[2]);

        return new Date(day, month, year);
    }

    /*public static boolean isAccountStatementFile(File file){
        // Tests
        boolean hasHeader = false; // Account; 1234567890987; EUR; ORDEM; DraftAccount; Additional Info
        boolean hasStatementLine = false; //Date; Value Date; Description; Draft; Credit; Accounting balance; Available balance

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext() && (!hasHeader || !hasStatementLine)) {
                String lineOfData = fileScanner.nextLine();
                String[] dataTokens = lineOfData.split(";");    // create list of values

                if (dataTokens[0].contains("Account") && dataTokens.length > 5) {
                    hasHeader = true;
                } else if (dataTokens.length == 7) {
                    hasStatementLine = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found! File: " + file.getName());
        }

        return hasHeader && hasStatementLine;
    }*/

    // Methods
    // Get
    public Date getInfoDate() {
        return infoDate;
    }

    public long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return accountName;
    }

    public String getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String additionalInfo() {
        return additionalInfo;
    }

    public List<StatementLine> getStatementLines() {
        return statementLines;
    }

    public double getInterestRate() {
        switch (this.getClass().getSimpleName()) {
            case "DraftAccount":
                return BanksConstants.normalInterestRate();
            case "SavingsAccount":
                return BanksConstants.savingsInterestRate();
        }
        return 0.0;
    }

    public double currentBalance() {
        if (statementLines.size() == 0) {
            return 0.0;
        } else {
            return statementLines.get(statementLines.size() - 1).getAvailableBalance();
        }
    }


    // Set
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setName(String newName) {
        accountName = newName;
    }

    public void setInfoDate(Date infoDate) {
        this.infoDate = infoDate;
    }

    private void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setStatementLines(ArrayList<StatementLine> statementLines) {
        this.statementLines = statementLines;
    }

    // Others
    public void addStatementLine(StatementLine line) {
        statementLines.add(line);
        setStartDate(statementLines.get(0).getDate());
        setEndDate(statementLines.get(statementLines.size() - 1).getDate());
    }

    public void autoCategorizeStatements(List<Category> categories) {
        for (StatementLine line : statementLines) {
            for (Category category : categories) {
                if (this instanceof SavingsAccount) {
                    line.setCategory(SavingsAccount.savingsCategory);
                } else if (category.hasTag(line.getDescription())) {
                    line.setCategory(category);
                }
            }
        }
    }

    public void removeStatementLinesBefore(Date date) {
        Filter<StatementLine, BeforeDateSelector> filter = new Filter<>(new BeforeDateSelector(date));
        statementLines = (ArrayList<StatementLine>) filter.apply(statementLines);
    }

    public double totalDraftsForCategorySince(Category savingsCategory, Date date) {
        double result = 0;
        for (StatementLine line : statementLines) {
            if (line.getCategory() != null) {
                if (line.getCategory().equals(savingsCategory) && line.getDate().after(Date.lastDayOfPreviousMonth(date))) {
                    result += line.getDraft();
                }
            }
        }
        return result;
    }

    /**
     * Função que calcula o Total Draft para um dado mês.
     */
    public double totalForMonth(int month, int year) {
        double result = 0;
        for (StatementLine line : statementLines) {
            if (line.getDate().getMonth().ordinal() == month && line.getDate().getYear() == year) {
                result += line.getDraft();
            }
        }
        return result;
    }

    /**
     * Estimated average balance for current year.
     * <p>Uses the <b>Available Balance</b> field for the calculations.
     */
    public double estimatedAverageBalance() {
        // Use Available Balance
        Date todayDate = new Date();
        Date beginYear = new Date(1, 1, todayDate.getYear());
        int totalNrDays = todayDate.diffInDays(beginYear);      // Total number of days between begin of year and today

        Date intervalBegin = new Date(1, 1, todayDate.getYear());
        Date intervalEnd;

        double result = 0.0;    // Default result

        if (statementLines.size() == 0) {
            return result;
        }

        double valueToSum = 0.0;

        Filter<StatementLine, BeforeDateSelector> filterBeforeDate = new Filter<>(new BeforeDateSelector(intervalBegin));
        Filter<StatementLine, CurrentYearDateSelector> filterCurrentYear = new Filter<>(new CurrentYearDateSelector());

        // List with statement lines from previous years
        List<StatementLine> previousYears = (List<StatementLine>) filterBeforeDate.apply(statementLines);

        // List with statement lines from current year
        List<StatementLine> currentYear = (List<StatementLine>) filterCurrentYear.apply(statementLines);

        if (previousYears.size() > 0) {
            valueToSum = previousYears.get(previousYears.size() - 1).getAvailableBalance();
        }

        int nrDays;              // Number of days in a time interval
        for (StatementLine line : currentYear) {
            intervalEnd = line.getDate();
            nrDays = intervalEnd.diffInDays(intervalBegin);

            result += valueToSum * nrDays;

            intervalBegin = line.getDate();
            valueToSum = line.getAvailableBalance();
        }

        nrDays = todayDate.diffInDays(intervalBegin);
        result += valueToSum * nrDays;

        result /= totalNrDays;      // Average calculation

        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", balance=" + currentBalance() +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", \nstatementLines=" + statementLines +
                '}';
    }

    @Override
    public boolean equals(Object account) {
        if (this == account) {
            return true;
        }
        if (account == null) {
            return false;
        }
        if (this.getClass() != account.getClass()) {
            return false;
        }
        // Test ID - Should be Unique
        return this.id == ((Account) account).id;
    }
}
