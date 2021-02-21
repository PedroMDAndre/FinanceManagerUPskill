package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.BanksConstants;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.accounts.formats.AccountMovFormat;
import pt.upskill.projeto2.financemanager.accounts.formats.CategoryListFormat;
import pt.upskill.projeto2.financemanager.accounts.formats.GlobalPositionFormat;
import pt.upskill.projeto2.financemanager.accounts.formats.analyze.GlobalEvoAccountMonthFormat;
import pt.upskill.projeto2.financemanager.accounts.formats.analyze.InterestEstimateFormat;
import pt.upskill.projeto2.financemanager.accounts.formats.analyze.PredictionPerCategoryFormat;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.utils.Menu;

import java.io.File;
import java.util.*;

public class PersonalFinanceManager {
    // Attributes
    private final List<Account> accountsList;
    private final List<Category> categoryList;

    // Constructor
    public PersonalFinanceManager() {
        this.accountsList = getAccounts();
        this.categoryList = Category.readCategories();

        // Categorize
        autoCategorizeAccounts();
        if (needsCategorize()) {
            askToCategorize();
        }
    }


    // Methods
    public List<Account> getAccountsList() {
        return accountsList;
    }

    public boolean hasAccounts() {
        return accountsList != null && accountsList.size() != 0;
    }

    private List<Account> getAccounts() {
        File dirAccountInfo = FileDirPath.dirAccountInfo;   // Directório com os ficheiros de contas
        File dirStatements = FileDirPath.dirStatements;     // Directório com os ficheiros de Statements

        List<Account> accountsList = new ArrayList<>();
        List<Account> statementAccountList = new ArrayList<>();        // Statements read from files in "statements" directory, but with already existing accounts

        // Read Account and Statement Files
        readAccountFiles(dirAccountInfo, accountsList, statementAccountList);
        readAccountFiles(dirStatements, accountsList, statementAccountList);


        // Add statements to Accounts
        addStatementsToAccounts(accountsList, statementAccountList);

        // Sort statement lines in the Accounts
        for (Account account : accountsList) {
            account.getStatementLines().sort(null);
        }

        // Update begin date and end date and check for consistency
        checkConsistency(accountsList);

        return accountsList;
    }

    public void saveAccounts() {
        Account.save(accountsList);
    }

    public static void deleteStatementFiles() {
        File path = FileDirPath.dirStatements;
        String fileName = "";

        // Read Account or Statement Files
        for (File file : path.listFiles()) {
            if (file.isFile() && file.getName().contains(".csv")) {
                file.delete();
            }
        }
    }

    // First Menu Methods
    public String globalPosition() {
        // Obriga a verificar antes se existem contas
        GlobalPositionFormat globalPositionFormat = new GlobalPositionFormat();
        String result = globalPositionFormat.format(accountsList);

        System.out.println(result);

        return result;
    }

    public String accountStatement() {
        // Obriga a verificar antes se existem contas
        String[] contas = accountsListTokens();

        String option = Menu.requestSelection("Escolha a conta:", contas);
        int i;
        for (i = 0; i < contas.length; i++) {
            if (option.equals(contas[i])) {
                break;
            }
        }

        AccountMovFormat fileaf = new AccountMovFormat();
        String result = fileaf.format(accountsList.get(i));

        System.out.println(result);

        return result;
    }

    public void listCategories() {
        List<Category> categories = Category.readCategories();
        CategoryListFormat catFormat = new CategoryListFormat();
        System.out.println(catFormat.fields());
        System.out.println(catFormat.format(categories));
    }

    // Analyse Methods
    public String monthlySummary() {
        String result = "";

        // Check the years with information available
        Set<String> yearsString = new HashSet<>();
        for (Account account : accountsList) {
            for (StatementLine line : account.getStatementLines()) {
                yearsString.add(Integer.toString(line.getDate().getYear()));
            }
        }
        String[] yearStringTokens = new String[yearsString.size()];

        yearsString.toArray(yearStringTokens);
        Arrays.sort(yearStringTokens, Comparator.reverseOrder());

        // Asks to select the year to present the Global Evolution per Month
        String option = Menu.requestSelection("Selecione o ano:", yearStringTokens);
        try {
            if (option == null) {
                throw new NullPointerException();
            }
            int year = Integer.parseInt(option);

            // Returns results to the console
            result += "\nEvolução Global por mês para o ano: " + year + "\n";
            result += "-------------------------------------------------------------\n\n";

            System.out.println(result);

            for (Account account : accountsList) {
                GlobalEvoAccountMonthFormat globalEvoAccountMonthFormat = new GlobalEvoAccountMonthFormat(year);
                String loopResult = globalEvoAccountMonthFormat.format(account);
                result += loopResult + "\n";
                System.out.println(loopResult);
            }

        } catch (NullPointerException e) {

        }

        return result;
    }

    public String predictionPerCategory() {
        // Obriga a verificar antes se existem contas
        String[] contas = accountsListTokens();

        String option = Menu.requestSelection("Escolha a conta:", contas);
        int i;
        for (i = 0; i < contas.length; i++) {
            if (option.equals(contas[i])) {
                break;
            }
        }

        // Returns results to the console
        Account account = accountsList.get(i);
        PredictionPerCategoryFormat predictionPerCategoryFormat = new PredictionPerCategoryFormat(categoryList);
        String result = predictionPerCategoryFormat.format(account);

        System.out.println(result);

        return result;
    }

    public String anualInterest() {
        InterestEstimateFormat interestEstimateFormat = new InterestEstimateFormat();

        // Returns results to the console
        String result = interestEstimateFormat.format(accountsList);

        System.out.println(result);
        return result;
    }

    // Auxiliar Methods
    private String[] accountsListTokens() {
        String[] accounts = new String[accountsList.size()];

        for (int i = 0; i < accountsList.size(); i++) {
            accounts[i] = accountsList.get(i).getId() + " | " + accountsList.get(i).getName();
        }
        return accounts;
    }

    // Auxiliar getAccounts
    private void readAccountFiles(File path, List<Account> accountsList, List<Account> statementAccountList) {
        // statementAccountList - Statements read from files in "statements" directory, but with already existing accounts

        String fileName = "";
        try {
            // Read Account or Statement Files
            for (File file : path.listFiles()) {
                fileName = file.getName();
                if (file.isFile() && file.getName().contains(".csv")) {
                    Account account = Account.newAccount(file);
                    // Add account to list
                    if (!accountsList.contains(account)) {
                        accountsList.add(account);
                    } else {
                        statementAccountList.add(account);
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Não existem ficheiros de conta!");
        } catch (UnknownAccountException e) {
            System.out.println("Não foi possível determinar o tipo de conta. Ficheiro: " + fileName);
        }
    }

    private void addStatementsToAccounts(List<Account> accountsList, List<Account> statementAccountList) {
        // Add statements to Accounts
        for (Account statementAccount : statementAccountList) {
            for (Account account : accountsList) {
                if (statementAccount.equals(account)) {
                    for (StatementLine line : statementAccount.getStatementLines()) {
                        if (!account.getStatementLines().contains(line)) {
                            account.getStatementLines().add(line);
                        }
                        {
                            System.out.println("Statement Line already in the Account.");
                        }
                    }
                }
            }
        }
    }

    private void checkConsistency(List<Account> accountsList) {
        for (Account account : accountsList) {
            List<StatementLine> statementLines = account.getStatementLines();

            // Update begin date and end date
            account.setStartDate(statementLines.get(0).getDate());
            account.setEndDate(statementLines.get(statementLines.size() - 1).getDate());

            // Check for consistency
            // Assuming 1st Statement Lines is always correct
            StatementLine firstLine = statementLines.get(0);

            double accountingBalanceLineBefore = firstLine.getAccountingBalance();
            double availableBalanceLineBefore = firstLine.getAvailableBalance();


            for (StatementLine line : statementLines) {
                if (!line.equals(firstLine)) {
                    double totalMov = line.getDraft() + line.getCredit();
                    if (line.getAccountingBalance() != accountingBalanceLineBefore + totalMov) {
                        line.setAccountingBalance(accountingBalanceLineBefore + totalMov);
                    }
                    if (line.getAvailableBalance() != availableBalanceLineBefore + totalMov) {
                        line.setAvailableBalance(availableBalanceLineBefore + totalMov);
                    }
                    accountingBalanceLineBefore = line.getAccountingBalance();
                    availableBalanceLineBefore = line.getAvailableBalance();
                }
            }
        }
    }

    // Categorize Methods
    private boolean needsCategorize() {
        for (Account account : accountsList) {
            for (StatementLine line : account.getStatementLines()) {
                if (line.getCategory() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private void autoCategorizeAccounts() {
        for (Account account : accountsList) {
            account.autoCategorizeStatements(categoryList);
        }
    }

    private void askToCategorize() {
        if (Menu.yesOrNoInput("Deseja categorizar as descrições em falta?")) {
            manualCategorize();
        }
    }

    private void manualCategorize() {
        for (Account account : accountsList) {
            for (StatementLine line : account.getStatementLines()) {
                if (line.getCategory() == null) {
                    String categoryName = Menu.requestInput("Qual o nome da Categoria para a descrição " + line.getDescription() + "?");
                    if (categoryName == null || categoryName.replace(" ", "").equals("")) {
                        continue;
                    }
                    Category newCategory = new Category(categoryName);
                    if (categoryList.contains(newCategory)) {
                        int i = categoryList.indexOf(newCategory);
                        categoryList.get(i).addTag(line.getDescription());

                    } else {
                        newCategory.addTag(line.getDescription());
                        categoryList.add(newCategory);
                    }
                }
                autoCategorizeAccounts();
            }
        }
        if (Menu.yesOrNoInput("Deseja guardar as categorias?")) {
            Category.writeCategories(categoryList);
        }

    }

}
