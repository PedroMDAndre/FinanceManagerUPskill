package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class CurrentYearDateSelector implements Selector<StatementLine>{
    // Attributes

    // Constructor
    public CurrentYearDateSelector(){

    }

    // Method
    @Override
    public boolean isSelected(StatementLine line) {
        Date lineDate = line.getDate();
        Date todayDate = new Date();
        return lineDate.getYear()==todayDate.getYear();
    }
}
