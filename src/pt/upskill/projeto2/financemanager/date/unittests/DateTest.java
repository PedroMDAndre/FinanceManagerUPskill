/**
 *
 */
package pt.upskill.projeto2.financemanager.date.unittests;

import org.junit.*;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.date.Month;

import java.util.Calendar;

import static org.junit.Assert.*;


public class DateTest {

    private static Date d0;
    private static Date d1;
    private static Date d2;
    private static Date d3;
    @SuppressWarnings("unused")
    private Date d4;
    @SuppressWarnings("unused")
    private Date d5;
    @SuppressWarnings("unused")
    private Date d6;
    @SuppressWarnings("unused")
    private Date d7;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        d0 = new Date(1, Month.DECEMBER, 2013);
        d1 = new Date(1, Month.JANUARY, 2014);
        d2 = new Date(2, Date.intToMonth(2), 2014);
        d3 = new Date(1, Month.FEBRUARY, 2015);
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        d4 = new Date(1, Month.DECEMBER, 2013);
        d5 = new Date(1, Month.JANUARY, 2014);
        d6 = new Date(2, Month.JANUARY, 2014);
        d7 = new Date(1, Date.intToMonth(2), 2015);
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#Date()}.
     */
    @Test
    public void testDate() {
        java.util.Date date = new java.util.Date();
        Date d = new Date();
        assertEquals(date, d.toDate());
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#Date(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testDateDate() {
        java.util.Date date = new java.util.Date();
        Date d = new Date(date);
        assertEquals(date, d.toDate());
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#Date(java.util.Date)}.
     */
    @Test
    public void testDateDate1() {
        java.util.Date date = new java.util.Date();
        Date d = new Date(date);
        Calendar c = Calendar.getInstance();
        assertEquals(c.get(Calendar.DAY_OF_MONTH), d.getDay());
        assertEquals(Date.intToMonth(c.get(Calendar.MONTH) + 1), d.getMonth());
        assertEquals(c.get(Calendar.YEAR), d.getYear());
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#Date(int, int, int)}.
     */
    @Test
    public void testDateIntIntInt() {
        Date d = new Date(1, Date.intToMonth(2), 2014);
        assertEquals(1, d.getDay());
        assertEquals(Date.intToMonth(2), d.getMonth());
        assertEquals(2014, d.getYear());
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#lastDayOf(Month, int)}.
     */
    @Test
    public void testLastDayOf() {
        assertEquals(31, Date.lastDayOf(Date.intToMonth(1), 2014));
        assertEquals(28, Date.lastDayOf(Date.intToMonth(2), 2014));
        assertEquals(29, Date.lastDayOf(Date.intToMonth(2), 2012));
        assertEquals(31, Date.lastDayOf(Date.intToMonth(3), 2014));
        assertEquals(30, Date.lastDayOf(Date.intToMonth(4), 2014));
        assertEquals(31, Date.lastDayOf(Date.intToMonth(5), 2014));
        assertEquals(30, Date.lastDayOf(Date.intToMonth(6), 2014));
        assertEquals(31, Date.lastDayOf(Date.intToMonth(7), 2014));
        assertEquals(31, Date.lastDayOf(Date.intToMonth(8), 2014));
        assertEquals(30, Date.lastDayOf(Date.intToMonth(9), 2014));
        assertEquals(31, Date.lastDayOf(Date.intToMonth(10), 2014));
        assertEquals(30, Date.lastDayOf(Date.intToMonth(11), 2014));
        assertEquals(31, Date.lastDayOf(Date.intToMonth(12), 2014));
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#before(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testBefore() {
        Date da = new Date();
        Date db = new Date(1, Date.intToMonth(2), 2014);
        assertTrue(db.before(da));

        assertTrue(d0.before(d1));
        assertTrue(d1.before(d2));
        assertTrue(d2.before(d3));

        assertFalse(d1.before(d1));

    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#after(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testAfter() {
        Date da = new Date();
        Date db = new Date(1, Date.intToMonth(2), 2014);
        assertTrue(da.after(db));

        assertTrue(d1.after(d0));
        assertTrue(d2.after(d1));
        assertTrue(d3.after(d2));

        assertFalse(d1.after(d1));

    }


    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#compareTo(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testCompareTo() {
        assertTrue(d0.compareTo(d1) < 0);
        assertTrue(d1.compareTo(d2) < 0);
        assertTrue(d2.compareTo(d3) < 0);

        assertEquals(d1.compareTo(d1), 0);

        assertTrue(d1.compareTo(d0) > 0);
        assertTrue(d2.compareTo(d1) > 0);
        assertTrue(d3.compareTo(d2) > 0);
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#firstOfMonth(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testFirstOfMonth() {
        Date d = new Date(2, 2, 2014);
        assertEquals(new Date(1, 2, 2014), Date.firstOfMonth(d));
        d = new Date(1, 1, 2014);
        assertEquals(new Date(1, 1, 2014), Date.firstOfMonth(d));
        d = new Date(31, 12, 2013);
        assertEquals(new Date(1, 12, 2013), Date.firstOfMonth(d));
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#endOfMonth(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testEndOfMonth() {
        Date d = new Date(2, 2, 2014);
        assertEquals(new Date(28, 2, 2014), Date.endOfMonth(d));
        d = new Date(1, 1, 2014);
        assertEquals(new Date(31, 1, 2014), Date.endOfMonth(d));
        d = new Date(31, 12, 2013);
        assertEquals(new Date(31, 12, 2013), Date.endOfMonth(d));
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#endOfNextMonth(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testEndOfNextMonth() {
        Date d = new Date(2, 2, 2014);
        assertEquals(new Date(31, 3, 2014), Date.endOfNextMonth(d));
        d = new Date(1, 1, 2014);
        assertEquals(new Date(28, 2, 2014), Date.endOfNextMonth(d));
        d = new Date(31, 12, 2013);
        assertEquals(new Date(31, 1, 2014), Date.endOfNextMonth(d));
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#firstOfNextMonth(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testFirstOfNextMonth() {
        Date d = new Date(2, 2, 2014);
        assertEquals(new Date(1, 3, 2014), Date.firstOfNextMonth(d));
        d = new Date(1, 1, 2014);
        assertEquals(new Date(1, 2, 2014), Date.firstOfNextMonth(d));
        d = new Date(31, 12, 2013);
        assertEquals(new Date(1, 1, 2014), Date.firstOfNextMonth(d));
    }

    /**
     * Test method for {@link pt.upskill.projeto2.financemanager.date.Date#lastDayOfPreviousMonth(pt.upskill.projeto2.financemanager.date.Date)}.
     */
    @Test
    public void testLastDayOfPreviousMonth() {
        Date d = new Date(2, 2, 2014);
        assertEquals(new Date(31, 1, 2014), Date.lastDayOfPreviousMonth(d));
        d = new Date(1, 1, 2014);
        assertEquals(new Date(31, 12, 2013), Date.lastDayOfPreviousMonth(d));
        d = new Date(31, 12, 2013);
        assertEquals(new Date(30, 11, 2013), Date.lastDayOfPreviousMonth(d));
    }

    @Test
    public void testDifferenceInDays() {
        Date d1 = new Date(2, 2, 2014);
        Date d2 = new Date(4, 2, 2014);
        assertEquals(2, d1.diffInDays(d2));
        d2 = new Date(2, 3, 2014);
        assertEquals(28, d1.diffInDays(d2));
        Date d3 = new Date(2, 4, 2014);
        assertEquals(31, d2.diffInDays(d3));
        d2 = new Date(2, 4, 2014);
        assertEquals(28 + 31, d1.diffInDays(d2));
        d2 = new Date(2, 2, 2015);
        assertEquals(365, d1.diffInDays(d2));
        d2 = new Date(2, 4, 2015);
        assertEquals(28 + 31 + 365, d1.diffInDays(d2));

        d2 = new Date(2, 4, 2014);
        assertEquals(28 + 31, d2.diffInDays(d1));

    }

    @Test
    public void testDifferenceInDays2() {
        Date d1 = new Date(2, 3, 2014);
        Date d2 = new Date(2, 3, 2014);
        for (int i = 0; i != 365; ++i) {
            assertEquals(i, d1.diffInDays(d2));
            d2 = new Date(new java.util.Date(d2.toDate().getTime() + 3600 * 1000 * 24));
        }
    }

}
