package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

public class MonthYearSelector implements Selector<StatementLine> {
    // Attributes
    private final int month;
    private final int year;

    // Constructor
    public MonthYearSelector(int month, int year) {
        this.month = month;
        this.year = year;
    }

    // Method
    @Override
    public boolean isSelected(StatementLine line) {
        return line.getDate().getYear() == year && line.getDate().getMonth().ordinal() == month;
    }
}
