package pt.upskill.projeto2.financemanager.accounts.formats.analyze;


import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.formats.Format;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

import java.util.List;

/**
 * Format Object
 * <p>Prediction per Category for current Month
 */
public class PredictionPerCategoryFormat implements Format<Account> {
    // Attributes
    private final List<Category> categoryList;

    // Constructor

    /**
     * Format Object
     * <p>Prediction per Category for current Month
     */
    public PredictionPerCategoryFormat(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    // Methods
    @Override
    public String format(Account account) {
        String nl = System.getProperty("line.separator");
        String result = "";
        result += "\nPrevisão gastos totais do mês por categoria" + nl;
        result += "-----------------------------------------------------------------\n" + nl;

        String head1 = "Account Info - " + account.getInfoDate() + nl;
        String head2 = "Account     " + account.getId() + "  " + account.getCurrency() + "  " + account.getName() + "  " + account.getType() + nl + nl;
        String head3 = "Category         Current Draft    Predict Draft    Expected Total" + nl;
        String head4 = "-----------------------------------------------------------------" + nl;


        result += head1 + head2 + head3 + head4;

        double totalCurrentDraft = 0;
        double totalPredictDraft = 0;
        double total = 0;
        Date today = new Date();

        Date beginMonth = Date.firstOfMonth(today);
        Date endMonth = Date.endOfMonth(today);
        int nrDaysToEnd = today.diffInDays(endMonth) + 1;
        int nrDaysToToday = beginMonth.diffInDays(today);

        for (Category category : categoryList) {
            String catName = category.getName();
            double currentDraft = account.totalDraftsForCategorySince(category, beginMonth);
            double predictDraft = currentDraft * nrDaysToEnd / nrDaysToToday;
            double expectedTotal = currentDraft + predictDraft;

            result += String.format("%-16s %13.2f %16.2f %17.2f", catName, currentDraft, predictDraft, expectedTotal) + nl;

            totalCurrentDraft += currentDraft;
            totalPredictDraft += predictDraft;
        }

        total = totalCurrentDraft + totalPredictDraft;

        result += head4;

        result += String.format("TOTAL %24.2f %16.2f %17.2f", totalCurrentDraft, totalPredictDraft, total) + nl +nl;

        return result;
    }
}
