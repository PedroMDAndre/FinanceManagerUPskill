package pt.upskill.projeto2.financemanager.accounts.formats.unittests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.upskill.projeto2.financemanager.accounts.Account;

import pt.upskill.projeto2.financemanager.accounts.formats.FileAccountFormat;
import pt.upskill.projeto2.financemanager.date.Date;

public class FileAccountFormatTest {

    private Account a1;
    private FileAccountFormat f;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        a1 = Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890989.csv"));
        f = new FileAccountFormat();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFormat() {
        String nl = System.getProperty("line.separator");
        String s1 = f.format(a1);
        String s2 = "Account Info - " + new Date().toString() + nl
                + "Account  ;1234567890989 ; EUR  ;POUPANCA ;SavingsAccount ;" + nl
                + "Start Date ;31-10-2013" + nl
                + "End Date ;03-01-2014" + nl
                + "Date ;Value Date ;Description ;Draft ;Credit ;Accounting balance ;Available balance" + nl
                + "31-10-2013 ;31-10-2013 ;SUMMARY ;0.0 ;200.0 ;2600.0 ;2600.0" + nl
                + "30-11-2013 ;30-11-2013 ;SUMMARY ;0.0 ;200.0 ;2800.0 ;2800.0" + nl
                + "31-12-2013 ;31-12-2013 ;SUMMARY ;0.0 ;200.0 ;3000.0 ;3000.0" + nl
                + "02-01-2014 ;02-01-2014 ;TRANSF ;0.0 ;300.0 ;3300.0 ;3300.0" + nl
                + "02-01-2014 ;02-01-2014 ;TRANSF ;0.0 ;300.0 ;3600.0 ;3600.0" + nl
                + "03-01-2014 ;03-01-2014 ;TRANSF ;0.0 ;300.0 ;3900.0 ;3900.0" + nl;

        for (int i = 0; i != s1.length(); i++)
            if (s1.charAt(i) != s2.charAt(i)) {
                System.out.println(i);
                break;
            }

        assertEquals(s2, s1);


    }

}