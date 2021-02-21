package pt.upskill.projeto2.financemanager.accounts.formats.analyze;

import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.accounts.formats.Format;
import pt.upskill.projeto2.financemanager.date.Month;
import pt.upskill.projeto2.financemanager.filters.Filter;
import pt.upskill.projeto2.financemanager.filters.YearSelector;

import java.util.ArrayList;

public class GlobalEvoAccountMonthFormat implements Format<Account> {
    // Attributes
    private final int year;

    // Constructor
    public GlobalEvoAccountMonthFormat(int year) {
        this.year = year;
    }

    // Methods
    @Override
    public String format(Account account) {
        String nl = System.getProperty("line.separator");
        String result = "";

        String head1 = "Account     " + account.getId() + "  " + account.getCurrency() + "  " + account.getName() + "  " + account.getType() + nl;
        String head2 = nl + "Month             Draft    Credit    Accounting     Available" + nl;
        String head3 = "                                        Balance       Balance" + nl;
        String head4 = "-------------------------------------------------------------" + nl;
        result = head1 + head2 + head3 + head4;

        Filter filter = new Filter(new YearSelector(year));
        ArrayList<StatementLine> lines = (ArrayList<StatementLine>) filter.apply(account.getStatementLines());
        double[] draftTotalPerMonth = new double[12];
        double draftTotalYear = 0;
        double[] creditTotalPerMonth = new double[12];
        double creditTotalYear = 0;
        double[] accountingBalancePerMonth = new double[12];
        double accountingBalanceYear = 0;
        double[] availableBalancePerMonth = new double[12];
        double availableBalanceYear = 0;

        for (StatementLine line : lines) {
            int month = line.getDate().getMonth().ordinal();
            draftTotalPerMonth[month - 1] += line.getDraft();
            draftTotalYear += line.getDraft();
            creditTotalPerMonth[month - 1] += line.getCredit();
            creditTotalYear += line.getCredit();
            accountingBalancePerMonth[month - 1] = line.getAccountingBalance();
            accountingBalanceYear = line.getAccountingBalance();
            availableBalancePerMonth[month - 1] = line.getAvailableBalance();
            availableBalanceYear = line.getAvailableBalance();
        }

        for (int i = 1; i < Month.values().length; i++) {
            result += String.format("%-13s %9.2f %9.2f %13.2f %13.2f\n", Month.values()[i], draftTotalPerMonth[i - 1], creditTotalPerMonth[i - 1], accountingBalancePerMonth[i - 1], availableBalancePerMonth[i - 1]);
        }

        result += "-------------------------------------------------------------" + nl;
        result += String.format("TOTAL         %9.2f %9.2f %13.2f %13.2f\n\n", draftTotalYear, creditTotalYear, accountingBalanceYear, availableBalanceYear);

        return result;
    }
}
