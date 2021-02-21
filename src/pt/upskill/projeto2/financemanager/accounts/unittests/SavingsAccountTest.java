package pt.upskill.projeto2.financemanager.accounts.unittests;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.BanksConstants;
import pt.upskill.projeto2.financemanager.accounts.SavingsAccount;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

import java.io.File;

public class SavingsAccountTest {

    private static Account a1;
    @SuppressWarnings("unused")
    private static Account a2;
    @SuppressWarnings("unused")
    private Account a3;
    private Account a4;
    private Account a5;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        a1 = new SavingsAccount(12345, "My Savings Acc");
        a2 = new SavingsAccount(67890, "My Savings Acc");

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        a3 = a1;
        a4 = new SavingsAccount(67890, "My Savings Acc");
        a5 = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890989.csv"));
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testSavingsAccount() {
        Account a = new SavingsAccount(1, "SAV");
        assertEquals(BanksConstants.savingsInterestRate(), a.getInterestRate(), 0.001);
    }

    @Test
    public void testAddStatementLine() {
        a4.addStatementLine(new StatementLine(new Date(1, 1, 2014), new Date(1, 1, 2014), "description", -22, 0.0, 1542, 1542, null));
        assertEquals(1542.0, a4.currentBalance(), 0.0001);
        assertEquals(-22, a4.totalDraftsForCategorySince(SavingsAccount.savingsCategory, new Date(31, 12, 2013)), 0.0001);
    }

    @Test
    public void testEstimatedAverageBalance() {
        assertEquals(a5.estimatedAverageBalance(), a5.currentBalance(), 0.001);
    }


}
