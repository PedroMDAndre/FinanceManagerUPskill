package pt.upskill.projeto2.financemanager.accounts.formats.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.accounts.formats.LongStatementFormat;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;


public class LongStatementFormatTest {

    private StatementLine s1;
    private StatementLine s2;
    private LongStatementFormat f;

    @Before
    public void setUp() throws Exception {
        s1 = new StatementLine(new Date(1, 1, 2014), new Date(1, 1, 2014),
                "description", 0.0, 22, 1520, 1542, null);
        s2 = new StatementLine(new Date(2, 1, 2014), new Date(3, 1, 2014),
                "description ...", -10.0, 220, 1500, 1730, new Category(
                "CATEGORY"));
        f = new LongStatementFormat();
    }


    @Test
    public void testFormat() {
        assertEquals("01-01-2014 \t01-01-2014 \tdescription \t0.0 \t22.0 \t1520.0 \t1542.0", f.format(s1));
        assertEquals("02-01-2014 \t03-01-2014 \tdescription ... \t-10.0 \t220.0 \t1500.0 \t1730.0", f.format(s2));
    }

    @Test
    public void testFields() {
        assertEquals(
                "Date \tValue Date \tDescription \tDraft \tCredit \tAccounting balance \tAvailable balance ", f.fields());
    }

}
