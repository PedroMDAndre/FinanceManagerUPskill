package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

import java.util.List;

public class FileAccountFormat implements Format<Account> {
    // Attributes

    // Constructor
    public FileAccountFormat() {

    }

    // Methods
    @Override
    public String format(Account account) {
        String nl = System.getProperty("line.separator");
        List<StatementLine> statementLines = account.getStatementLines();

        // Headings
        String head1 = "Account Info - " + account.getInfoDate() + nl;
        String head2 = "Account  ;" + account.getId() + " ; " + account.getCurrency() + "  ;" + account.getName() + " ;" + account.getType() + " ;" + nl;
        String head3 = "Start Date ;" + account.getStartDate() + nl;
        String head4 = "End Date ;" + account.getEndDate() + nl;
        String movFields = "Date ;Value Date ;Description ;Draft ;Credit ;Accounting balance ;Available balance" + nl;

        // List of Statement Lines
        String stringStatements = "";
        for (StatementLine line : statementLines) {
            Date date = line.getDate();
            Date valueDate = line.getValueDate();
            String description = line.getDescription();
            Double draft = line.getDraft();
            Double credit = line.getCredit();
            Double accountingBalance = line.getAccountingBalance();
            Double availableBalance = line.getAvailableBalance();

            stringStatements += date + " ;" + valueDate + " ;" + description + " ;" + draft + " ;" + credit + " ;"
                    + accountingBalance + " ;" + availableBalance + nl;
        }

        return head1 + head2 + head3 + head4 + movFields + stringStatements;
    }
}
