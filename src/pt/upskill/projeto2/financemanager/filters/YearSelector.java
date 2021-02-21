package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

public class YearSelector implements Selector<StatementLine> {
    // Attributes
    private final int year;

    // Constructor
    public YearSelector(int year) {
        this.year = year;
    }

    // Method
    @Override
    public boolean isSelected(StatementLine line) {
        return line.getDate().getYear() == year;
    }
}
