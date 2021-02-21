package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.date.Date;

import java.util.ArrayList;

public class DraftAccount extends Account {
    // Attributes

    //Constructor
    public DraftAccount(long id, String accountName) {
        super(id, accountName, "DraftAccount");
    }
}
