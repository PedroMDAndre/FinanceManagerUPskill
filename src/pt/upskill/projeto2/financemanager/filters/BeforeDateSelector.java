package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class BeforeDateSelector implements Selector<StatementLine>{
    // Attributes
    private final Date date;

    // Constructor
    public BeforeDateSelector(Date date){
        this.date = date;
    }

    // Method
    @Override
    public boolean isSelected(StatementLine line) {
        Date lineDate = line.getDate();
        return lineDate.before(date);
    }
}
