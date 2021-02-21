package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

public class NoCategorySelector implements Selector<StatementLine> {
    // Attributes

    // Constructor
    public NoCategorySelector() {

    }

    //Methods
    @Override
    public boolean isSelected(StatementLine line) {
        return line.getCategory() == null;
    }
}
