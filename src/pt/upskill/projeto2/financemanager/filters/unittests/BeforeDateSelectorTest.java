package pt.upskill.projeto2.financemanager.filters.unittests;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.filters.BeforeDateSelector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BeforeDateSelectorTest {

	private static StatementLine stt1;
	private static StatementLine stt2;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stt1 = new StatementLine(new Date(1, 1, 2014), new Date(1, 1, 2014), "description", 0.0, 22, 1520, 1542, new Category("A"));
		stt2 = new StatementLine(new Date(1, 2, 2014), new Date(1, 2, 2014), "description", 0.0, 22, 1520, 1542, null);		
	}

	@Test
	public void testIsSelected() {
		BeforeDateSelector selector = new BeforeDateSelector(new Date(15, 1, 2014));
		assertTrue(selector.isSelected(stt1));
		assertFalse(selector.isSelected(stt2));
		
	}
}
