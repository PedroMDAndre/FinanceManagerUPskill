package pt.upskill.projeto2.financemanager.accounts.formats.analyze;

import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.BanksConstants;
import pt.upskill.projeto2.financemanager.accounts.SavingsAccount;
import pt.upskill.projeto2.financemanager.accounts.formats.Format;

import java.util.List;

public class InterestEstimateFormat implements Format<List<Account>> {
    // Attributes

    // Constructor
    public InterestEstimateFormat() {

    }

    // Methods
    @Override
    public String format(List<Account> accounts) {
        String nl = System.getProperty("line.separator");
        String result = "";

        result += nl + "Previsão juros anuais" + nl;
        result += "----------------------------------------------------------------------------------\n" + nl;
        result += String.format("Interest Rates:\nSaving Accounts: %.2f %s\nDraft  Accounts: %.2f %s\n\n",
                BanksConstants.savingsInterestRate() * 100, "%", BanksConstants.normalInterestRate() * 100, "%");

        String[] fields1T = {"Account", "Name", "Type", "Average", "Anual", "Currency"};
        String[] fields2T = {"", "", "", "Balance", "Interest", ""};

        String fields1 = String.format("%-15s %-15s %-16s %9s %11s %11s\n",
                fields1T[0], fields1T[1], fields1T[2], fields1T[3], fields1T[4], fields1T[5]);
        String fields2 = String.format("%-15s %-15s %-16s %9s %11s %11s\n",
                fields2T[0], fields2T[1], fields2T[2], fields2T[3], fields2T[4], fields2T[5]);;
        String head1 = "----------------------------------------------------------------------------------" + nl;
        result += fields1 + fields2 + head1;
        double interest;
        double averageBalance;
        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                averageBalance = account.estimatedAverageBalance();
                interest = averageBalance * BanksConstants.normalInterestRate();
            } else {
                averageBalance = account.currentBalance();
                interest = averageBalance * BanksConstants.savingsInterestRate();
            }
            String line = String.format("%-15d %-15s %-16s %9.2f %11.2f %11s\n",
                    account.getId(), account.getName(), account.getType(), averageBalance, interest, account.getCurrency());

            result += line;
        }

        result += head1 + nl;

        return result;
    }
}
