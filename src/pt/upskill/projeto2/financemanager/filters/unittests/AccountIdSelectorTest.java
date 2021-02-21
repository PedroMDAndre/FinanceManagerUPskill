package pt.upskill.projeto2.financemanager.filters.unittests;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.DraftAccount;
import pt.upskill.projeto2.financemanager.filters.AccountIdSelector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AccountIdSelectorTest {

	private static Account a1;
	private static Account a2;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		a1 = new DraftAccount(12345, "DRAFT_ACC");
		a2 = new DraftAccount(67890, "DRAFT_ACC");		
	}


	@Test
	public void testAccountIdSelector() {
		AccountIdSelector idSelector = new AccountIdSelector(12345);
		assertTrue(idSelector.isSelected(a1));
		assertFalse(idSelector.isSelected(a2));
	}


}
