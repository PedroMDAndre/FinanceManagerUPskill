package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

import java.util.ArrayList;

public class SavingsAccount extends Account {
    public static Category savingsCategory = new Category("SAVINGS");

    // Attributes

    // Constructors
    public SavingsAccount(long id, String accountName) {
        super(id, accountName, "SavingsAccount");
    }

    // Methods
    @Override
    public void addStatementLine(StatementLine line) {
        line.setCategory(savingsCategory);
        super.addStatementLine(line);
    }
}
