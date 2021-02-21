package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class SimpleStatementFormat implements StatementLineFormat {
    // Attributes

    // Constructor
    public SimpleStatementFormat() {

    }

    // Methods
    @Override
    public String fields() {
        return "Date \tDescription \tDraft \tCredit \tAvailable balance ";
    }

    @Override
    public String format(StatementLine objectToFormat) {
        Date date = objectToFormat.getDate();
        String description = objectToFormat.getDescription();
        Double draft = objectToFormat.getDraft();
        Double credit = objectToFormat.getCredit();
        Double availableBalance = objectToFormat.getAvailableBalance();

        return date + " \t" + description + " \t" + draft + " \t" + credit + " \t" + availableBalance;
    }
}
