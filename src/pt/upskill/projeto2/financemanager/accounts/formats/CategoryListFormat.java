package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.categories.Category;

import java.util.List;

public class CategoryListFormat implements Format<List<Category>> {
    // Attributes

    // Constructor
    public CategoryListFormat() {

    }

    // Methods
    @Override
    public String format(List<Category> categories) {
        String nl = System.getProperty("line.separator");
        StringBuilder result = new StringBuilder();

        for (Category category : categories) {
            result.append(category.getName()).append(nl);
            for (String tag : category.getTagSet()) {
                result.append("\t\t\t").append(tag).append(nl);
            }
            result.append("-----------------------").append(nl);
        }

        return result.toString();
    }

    public String fields() {
        return "Category\tDescription\n" +
                "=======================";
    }


}
