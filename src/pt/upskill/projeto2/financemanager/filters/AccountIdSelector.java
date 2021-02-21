package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.Account;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class AccountIdSelector implements Selector<Account>{
	// Attributes
	private final long id;

	// Constructor
	public AccountIdSelector(long id) {
		this.id = id;
	}

	// Methods
	@Override
	public boolean isSelected(Account item) {
		return item.getId() == id;
	}
}
