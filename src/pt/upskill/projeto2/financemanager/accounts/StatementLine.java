package pt.upskill.projeto2.financemanager.accounts;

import com.sun.glass.ui.EventLoop;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

public class StatementLine implements Comparable<StatementLine>{
    // Attributes
    private final Date date;
    private final Date valueDate;
    private final String description;
    private final double draft;
    private final double credit;
    private double accountingBalance;
    private double availableBalance;
    private Category category;

    // Constructor
    public StatementLine(Date date, Date valueDate, String description,
                         double draft, double credit, double accountingBalance,
                         double availableBalance, Category category) throws IllegalArgumentException {

        if (date == null || valueDate == null) {
            throw new IllegalArgumentException("Data inválida.");
        }
        if (description == null || description.equals("")) {
            throw new IllegalArgumentException("O movimento tem de ter descrição.");
        }
        if (credit < 0) {
            throw new IllegalArgumentException("O valor de crédito tem de ser positivo.");
        }
        if (draft > 0) {
            throw new IllegalArgumentException("O valor de débito tem de ser negativo.");
        }

        this.date = date;
        this.valueDate = valueDate;

        this.description = description;
        this.draft = draft;
        this.credit = credit;
        this.accountingBalance = accountingBalance;
        this.availableBalance = availableBalance;
        this.category = category;
    }

    // Class Methods
    public static StatementLine newStatementLine(String lineOfData, Category category){
        String[] dataTokens = lineOfData.split(";");    // create list of values

        Date date = Account.stringToDate(dataTokens[0].trim(), "-");
        Date valueDate = Account.stringToDate(dataTokens[1].trim(), "-");
        String description = dataTokens[2].trim();

        double draft;
        if (dataTokens[3].trim().equals("")) {
            draft = 0.0;
        } else {
            draft = Double.parseDouble(dataTokens[3].trim());
        }

        double credit;
        if (dataTokens[4].trim().equals("")) {
            credit = 0.0;
        } else {
            credit = Double.parseDouble(dataTokens[4].trim());
        }

        double accountingBalance = Double.parseDouble(dataTokens[5].trim());
        double availableBalance = Double.parseDouble(dataTokens[6].trim());

        StatementLine line = new StatementLine(date, valueDate, description, draft,
                credit, accountingBalance, availableBalance, category);

        return line;
    }

    // Methods
    // Get Methods
    public Date getDate() {
        return date;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public String getDescription() {
        return description;
    }

    public double getCredit() {
        return credit;
    }

    public double getDraft() {
        return draft;
    }

    public double getAccountingBalance() {
        return accountingBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public Category getCategory() {
        return category;
    }

    // Set Methods
    public void setCategory(Category cat) {
        category = cat;
    }

    public void setAccountingBalance(double accountingBalance) {
        this.accountingBalance = accountingBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    // Other Methods
    @Override
    public String toString() {
        return "StatementLine{" +
                "date=" + date +
                ", valueDate=" + valueDate +
                ", description='" + description + '\'' +
                ", draft=" + draft +
                ", credit=" + credit +
                ", accountingBalance=" + accountingBalance +
                ", availableBalance=" + availableBalance +
                ", category=" + category +
                "}\n";
    }

    @Override
    public boolean equals(Object lineObj) {
        if (this == lineObj) {
            return true;
        }
        if (lineObj == null) {
            return false;
        }
        if (this.getClass() != lineObj.getClass()) {
            return false;
        }

        StatementLine line = (StatementLine) lineObj;
        // Test Date
        if(!this.date.equals(line.date)){
            return false;
        }
        // Test Description
        if(!this.description.equals(line.description)){
            return false;
        }
        // Test draft and credit
        return (this.draft == line.draft && this.credit == line.credit);
    }

    @Override
    public int compareTo(StatementLine line2) {
        return this.date.compareTo(line2.date);
    }
}
