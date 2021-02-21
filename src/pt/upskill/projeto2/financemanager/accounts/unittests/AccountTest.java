package pt.upskill.projeto2.financemanager.accounts.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import pt.upskill.projeto2.financemanager.accounts.*;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.BadFormatException;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;

public class AccountTest {

    private static List<Category> categories;
    private static Account a1;
    @SuppressWarnings("unused")
    private static Account a2;
    @SuppressWarnings("unused")
    private Account a3;
    @SuppressWarnings("unused")
    private Account a4;
    private Account a5;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        a1 = new DraftAccount(12345, "DRAFT_ACC");
        a2 = new DraftAccount(67890, "DRAFT_ACC");

        //Simulating a categories file containing (in this order):
        //SUMMARY with description SUMMARY
        //SAVINGS with description TRANSF
        //HOME with descriptions PURCHASE, INSTALMENT
        //CAR with description LOW VALUE
        categories = new ArrayList<Category>();
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
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        a3 = a1;
        a4 = new DraftAccount(67890, "DRAFT_ACC");
        a5 = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890989.csv"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAccount1() {
        Account acc = new DraftAccount(1, "NAME");
        assertEquals(1, acc.getId());
        assertEquals("NAME", acc.getName());
        assertEquals(DraftAccount.class, acc.getClass());
        assertEquals("", acc.additionalInfo());
        assertEquals(0.0, acc.currentBalance(), 0.001);
        assertEquals(0.0, acc.estimatedAverageBalance(), 0.001);
        assertNull(acc.getStartDate());
        assertNull(acc.getEndDate());
        assertEquals(BanksConstants.normalInterestRate(), acc.getInterestRate(), 0.001);
    }

    @Test
    public void testAccount2() throws FileNotFoundException, EOFException, ParseException, BadFormatException, UnknownAccountException {
        Account acc = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890989.csv"));
        assertEquals(1234567890989L, acc.getId());
        assertEquals("POUPANCA", acc.getName());
        assertEquals(SavingsAccount.class, acc.getClass());
        assertEquals("", acc.additionalInfo());
        assertEquals(3900.0, acc.currentBalance(), 0.001);
        assertEquals(3900.0, acc.estimatedAverageBalance(), 0.001);
        assertEquals(new Date(31, 10, 2013), acc.getStartDate());
        assertEquals(new Date(3, 1, 2014), acc.getEndDate());
        assertEquals(BanksConstants.savingsInterestRate(), acc.getInterestRate(), 0.001);
    }

    @Test
    public void testAddStatementLine() {
        a1.addStatementLine(new StatementLine(new Date(1, 1, 2014), new Date(1, 1, 2014), "description", 0.0, 22, 1520, 1542, null));
        a1.addStatementLine(new StatementLine(new Date(2, 1, 2014), new Date(2, 1, 2014), "description", 0.0, 0.0, 1542, 1542, null));
        assertEquals(1542.0, a1.currentBalance(), 0.001);
        assertEquals(1542.0, a1.estimatedAverageBalance(), 0.001);
        assertEquals(new Date(1, 1, 2014), a1.getStartDate());
        assertEquals(new Date(2, 1, 2014), a1.getEndDate());
    }

    @Test
    public void testRemoveStatementLinesBefore() {
        a5.removeStatementLinesBefore(new Date(1, 12, 2013));
        assertEquals(0.0, a5.totalDraftsForCategorySince(categories.get(0), new Date(1, 11, 2013)), 0.001);
    }

    @Test
    public void testTotalForMonth() {
        assertEquals(0.0, a5.totalForMonth(12, 2013), 0.001);
    }

    @Test
    public void testTotalForMonth2() {
        a1.addStatementLine(new StatementLine(new Date(1, 12, 2013), new Date(1, 12, 2013), "description", -22.0, 0.0, 1542, 1542, null));
        a1.addStatementLine(new StatementLine(new Date(2, 12, 2013), new Date(2, 12, 2013), "description", -2.0, 0.0, 1540, 1540, null));
        assertEquals(-24.0, a1.totalForMonth(12, 2013), 0.001);
    }

    @Test
    public void testAutoCategorizeStatements1() throws FileNotFoundException, EOFException, ParseException, BadFormatException, UnknownAccountException {
        Account a6 = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890987.csv"));
        assertEquals(0.0, a6.totalDraftsForCategorySince(categories.get(0), new Date(1, 11, 2013)), 0.001);
        assertEquals(0.0, a6.totalDraftsForCategorySince(categories.get(1), new Date(1, 11, 2013)), 0.001);
        assertEquals(0.0, a6.totalDraftsForCategorySince(categories.get(2), new Date(1, 11, 2013)), 0.001);
        assertEquals(0.0, a6.totalDraftsForCategorySince(categories.get(3), new Date(1, 11, 2013)), 0.001);
        a6.autoCategorizeStatements(categories);
        assertEquals(-5576.63, a6.totalDraftsForCategorySince(categories.get(0), new Date(1, 11, 2013)), 0.01);
        assertEquals(0.0, a6.totalDraftsForCategorySince(categories.get(1), new Date(1, 11, 2013)), 0.001);
        assertEquals(-450, a6.totalDraftsForCategorySince(categories.get(2), new Date(1, 11, 2013)), 0.001);
        assertEquals(-150.65, a6.totalDraftsForCategorySince(categories.get(3), new Date(1, 11, 2013)), 0.001);
    }

    @Test
    public void testAutoCategorizeStatements2() throws FileNotFoundException, EOFException, ParseException, BadFormatException {
        // Savings accounts only have SAVINGS statement-lines ... and they are all credits
        assertEquals(0.0, a5.totalDraftsForCategorySince(categories.get(0), new Date(1, 11, 2013)), 0.001);
        assertEquals(0.0, a5.totalDraftsForCategorySince(categories.get(1), new Date(1, 11, 2013)), 0.001);
        a5.autoCategorizeStatements(categories);
        assertEquals(0.0, a5.totalDraftsForCategorySince(categories.get(0), new Date(1, 11, 2013)), 0.001);
        assertEquals(0.0, a5.totalDraftsForCategorySince(categories.get(1), new Date(1, 11, 2013)), 0.001);
    }

    @Test
    public void testSetName() {
        a5.setName("OTHER");
        assertEquals("OTHER", a5.getName());
    }

}
