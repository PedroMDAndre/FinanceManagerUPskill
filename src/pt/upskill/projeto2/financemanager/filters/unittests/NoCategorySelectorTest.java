package pt.upskill.projeto2.financemanager.filters.unittests;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.filters.NoCategorySelector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class NoCategorySelectorTest {

	private static StatementLine stt1;
	private static StatementLine stt2;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stt1 = new StatementLine(new Date(1, 1, 2014), new Date(1, 1, 2014), "description", 0.0, 22, 1520, 1542, new Category("A"));
		stt2 = new StatementLine(new Date(2, 1, 2014), new Date(2, 1, 2014), "description", 0.0, 22, 1520, 1542, null);		
	}

	@Test
	public void testIsSelected() {
		NoCategorySelector selector = new NoCategorySelector();
		assertFalse(selector.isSelected(stt1));
		assertTrue(selector.isSelected(stt2));
		
	}

}
