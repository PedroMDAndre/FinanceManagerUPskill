package pt.upskill.projeto2.financemanager.zextra;

import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.DraftAccount;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.accounts.formats.FileAccountFormat;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.utils.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pt.upskill.projeto2.financemanager.accounts.Account.newAccount;

public class ATest {
    public static void main(String[] args) {


        //testDates();
        //testMonth();
        //testAccountAverage();
        //testCreateTestCategories();
        //testAccount1();
        //testAccount2();
        //testAccount3();
        //testMenu();
        //testLoadStatements();

       // File file = new File("statements/c.csv");
        //file.delete();
       // PersonalFinanceManager.deleteStatementFiles();

    }

    private static void testLoadStatements(){
        File file = new File("statements/1234567890987_1.csv");
        try {
            Account a = newAccount(file);
            System.out.println(a);
        } catch (UnknownAccountException e) {
            System.out.println("Não é conta");
        }

        readAllAccount();
    }

    private static void testDates() {
        Date today = new Date();
        Date beginMonth = Date.firstOfMonth(today);
        Date endMonth = Date.endOfMonth(today);

        System.out.println("Today: " + today);
        System.out.println("Begin: " + beginMonth);
        System.out.println("End  : " + endMonth);


        int nrDaysToEnd = endMonth.diffInDays(today);
        int nrDaysToToday = beginMonth.diffInDays(today);
        System.out.println("Days to end    : " + nrDaysToEnd);
        System.out.println("Days from begin: " + nrDaysToToday);
        Date todayDate = new Date(31, 12, 2020);
        Date beginYear = new Date(1, 1, todayDate.getYear());
        int totalNrDays = todayDate.diffInDays(beginYear);
        System.out.println(totalNrDays);
    }

    private static void testAccountAverage() {
        Account ac1 = new DraftAccount(12333, "Draft Account");
        Date inicio1 = new Date(20, 12, 2019);
        Date inicio2 = new Date(1, 10, 2020);
        Date inicio3 = new Date(6, 10, 2020);
        StatementLine sl1 = new StatementLine(inicio1, inicio1, "Compra", -100, 0, 100, 100, null);
        StatementLine sl2 = new StatementLine(inicio2, inicio2, "Compra", 0, 100, 200, 200, null);
        StatementLine sl3 = new StatementLine(inicio3, inicio3, "Compra", 0, 100, 300, 300, null);
        ac1.addStatementLine(sl1);
        ac1.addStatementLine(sl2);
        ac1.addStatementLine(sl3);
        System.out.println(ac1.estimatedAverageBalance());
    }


    public static void readAllAccount() {
        File dirAccount = new File("statements/"); // Directório com os ficheiros de contas
        System.out.println(dirAccount.getAbsolutePath());
        List<Account> accountsList = new ArrayList<>();
        try {
            for (File file : dirAccount.listFiles()) {
                if (file.isFile()) {
                    Account account = Account.newAccount(file);
                    accountsList.add(account);
                    System.out.println(account);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Não existem ficheiros de conta!");
        } catch (UnknownAccountException e) {
            System.out.println("Não foi possível determinar o tipo de conta. Ficheiro: ");
        }

    }

    public static void testAccount1() {
        File file = new File("account_info/1234567890987.csv");
        try {
            Account conta1 = newAccount(file);
            FileAccountFormat f1 = new FileAccountFormat();
            System.out.println(f1.format(conta1));
        } catch (UnknownAccountException e) {

        }
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < 30; i++) {
            set.add(i);
        }
        System.out.println(set);

    }

    public static void testAccount2() {
        FileAccountFormat faformat = new FileAccountFormat();
        try {
            Account a5 = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890989.csv"));
            System.out.println(faformat.format(a5));
            a5.removeStatementLinesBefore(new Date(1, 12, 2013));
            System.out.println();
            System.out.println(faformat.format(a5));
        } catch (UnknownAccountException e) {
        }
    }

    public static void testAccount3() {
        List<Category> categories1 = Category.readCategories();
        List<Category> categories = new ArrayList<>();
        Category cat = new Category("SUMMARY");
        cat.addTag("SUMMARY");
        Category cat2 = new Category("SAVINGS");
        cat2.addTag("TRANSF");
        Category cat3 = new Category("HOME");
        cat3.addTag("PURCHASE");
        cat3.addTag("INSTALMENT");
        Category cat4 = new Category("CAR");
        cat4.addTag("LOW VALUE");
        categories.add(cat);
        categories.add(cat2);
        categories.add(cat3);
        categories.add(cat4);

        Category.writeCategories(categories);
        try {
            Account a6 = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890987.csv"));
            a6.autoCategorizeStatements(categories);
            // assertEquals(-150.65, a6.totalDraftsForCategorySince(categories.get(3), new Date(1, 11, 2013)), 0.001);
            System.out.println(categories.get(3));
            double a = a6.totalDraftsForCategorySince(categories.get(3), new Date(1, 11, 2013));
            System.out.println(a);
            System.out.println(a6);
        } catch (UnknownAccountException e) {

        }
    }

    public static void testCreateTestCategories() {
        List<Category> categories = new ArrayList<>();
        Category c1 = new Category("HOME");
        Category c2 = new Category("CAR");
        Category c3 = new Category("EXTRA");
        c1.addTag("PURCHASE");
        c2.addTag("GASOLINA");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        File file = new File("src/pt/upskill/projeto2/financemanager/account_info_test/categories");
        Category.writeCategories(file, categories);
        categories = Category.readCategories(file);
        System.out.println(categories);
    }

    public static void testCategory2() {
        List<Category> categories = new ArrayList<>();
        Category c1 = new Category("HOME");
        Category c2 = new Category("CAR");
        Category c3 = new Category("EXTRA");
        c1.addTag("PURCHASE");
        c2.addTag("GASOLINA");
        //c1.addTag("bichos");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        File file = new File("src/pt/upskill/projeto2/financemanager/account_info_test/categories");
        Category.writeCategories(file, categories);
        categories = Category.readCategories();
        System.out.println(categories);
        System.out.println(c3.isCategoryInList(categories));
    }

    public static void testMenu() {
        Menu.requestInput("Input?");
        String[] strings = {"a", "b"};
        Menu.requestSelection("Escolha?", strings);
        Menu.yesOrNoInput("Yes?");
    }

    public static void testMonth() {
        PersonalFinanceManager a = new PersonalFinanceManager();
        List<Account> accountsList = a.getAccountsList();
        Set<String> yearsString = new HashSet<>();
        for (Account account : accountsList) {
            for (StatementLine line : account.getStatementLines()) {
                yearsString.add(Integer.toString(line.getDate().getYear()));
            }
        }
        String[] yearStringTokens = new String[yearsString.size()];
        yearsString.toArray(yearStringTokens);


        String option = Menu.requestSelection("Selecione o ano:", yearStringTokens);
        int year = Integer.parseInt(option);
    }

}
