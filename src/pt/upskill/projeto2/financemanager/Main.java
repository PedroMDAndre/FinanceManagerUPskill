package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Main {
    public static void main(String[] args) {
        PersonalFinanceManager personalFinanceManager = new PersonalFinanceManager();
        PersonalFinanceManagerUserInterface gui = new PersonalFinanceManagerUserInterface(
                personalFinanceManager);
        gui.execute();
    }
}
