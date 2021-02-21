package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class LongStatementFormat implements StatementLineFormat {
    // Attributes

    // Constructor
    public LongStatementFormat() {

    }

    // Methods
    @Override
    public String fields() {
        return "Date \tValue Date \tDescription \tDraft \tCredit \tAccounting balance \tAvailable balance ";
    }

    @Override
    public String format(StatementLine objectToFormat) {
        Date date = objectToFormat.getDate();
        Date valueDate = objectToFormat.getValueDate();
        String description = objectToFormat.getDescription();
        Double draft = objectToFormat.getDraft();
        Double credit = objectToFormat.getCredit();
        Double accountingBalance = objectToFormat.getAccountingBalance();

        String availableBalance = Double.toString(objectToFormat.getAvailableBalance());

        return date + " \t" + valueDate + " \t" + description + " \t" + draft + " \t" + credit + " \t" + accountingBalance + " \t" + availableBalance;
    }
}
