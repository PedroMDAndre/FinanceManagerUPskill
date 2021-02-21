package pt.upskill.projeto2.financemanager.accounts;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class BanksConstants {

    private static double normalInterestRate = 0.01;
    private static double savingsInterestRate = 0.02;

    public static double savingsInterestRate() {
        return savingsInterestRate;
    }

    public static double normalInterestRate() {
        return normalInterestRate;
    }

    public static void setNormalInterestRate(double r) {
        normalInterestRate = r;
    }

    public static void setSavingsInterestRate(double r) {
        savingsInterestRate = r;
    }
}
