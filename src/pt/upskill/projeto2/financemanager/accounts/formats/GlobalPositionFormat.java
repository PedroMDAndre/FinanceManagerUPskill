package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.Account;

import java.util.List;

public class GlobalPositionFormat implements Format<List<Account>> {
    @Override
    public String format(List<Account> accountsList) {
        String nl = System.getProperty("line.separator");
        String result = "";

        double saldoTotal = 0.0;
        result += "Posição Global" + nl +
                "=========================" + nl;
        result += "Número de Conta     Saldo" + nl;
        for (Account account : accountsList) {
            saldoTotal += account.currentBalance();
            result += String.format("%-15d %9.2f\n", account.getId(), account.currentBalance());
        }

        result += String.format("\n%-15s %9.2f\n", "Saldo total:", saldoTotal);

        return result;
    }
}
