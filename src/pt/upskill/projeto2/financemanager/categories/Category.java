package pt.upskill.projeto2.financemanager.categories;

import pt.upskill.projeto2.financemanager.FileDirPath;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Category implements Serializable {
    private static final File FILE_CATEGORIES = FileDirPath.categoriesFile;
    // Attributes
    private final String categoryName;
    private Set<String> tagSet;

    // Constructor
    public Category(String categoryName) {
        this.categoryName = categoryName.toUpperCase();
        this.tagSet = new HashSet<>();
    }

    // Class Methods
    public static List<Category> readCategories() {
        return readCategories(FILE_CATEGORIES);
    }

    /**
     * Função que lê o ficheiro categories e gera uma lista de {@link Category} (método fábrica)
     * Deve ser utilizada a desserialização de objetos para ler o ficheiro binário categories.
     *
     * @param file - Ficheiro onde estão apontadas as categorias possíveis iniciais, numa lista serializada (por defeito: /account_info/categories)
     * @return uma lista de categorias, geradas ao ler o ficheiro
     */
    public static List<Category> readCategories(File file) {
        // Foi alterado o caminho do teste.
        List<Category> categories = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            categories = (ArrayList<Category>) objIn.readObject();
            objIn.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("Erro a ler o ficheiro com o as Categorias!");
            System.out.println("Criada nova lista vazia!");
        } catch (ClassNotFoundException e) {
            System.out.println("Não foi possível converter a lista salva no ficheiro!");
            System.out.println("Criada nova lista vazia!");
        }
        return categories;
    }

    public static void writeCategories(List<Category> categories) {
        writeCategories(FILE_CATEGORIES, categories);
    }

    /**
     * Função que grava no ficheiro categories (por defeito: /account_info/categories) a lista de {@link Category} passada como segundo argumento
     * Deve ser utilizada a serialização dos objetos para gravar o ficheiro binário categories.
     *
     * @param file
     * @param categories
     */
    public static void writeCategories(File file, List<Category> categories) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(categories);
            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Erro a salvar as Categorias no ficheiro!");
        }
    }

    // Methods
    public boolean hasTag(String tag) {
        return tagSet.contains(tag.toUpperCase());
    }

    public void addTag(String tag) {
        tagSet.add(tag.toUpperCase());
    }

    public String getName() {
        return categoryName;
    }

    public boolean isCategoryInList(List<Category> categories) {
        for (Category category : categories) {
            if (category.categoryName.equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> getTagSet() {
        return tagSet;
    }

    @Override
    public boolean equals(Object category) {
        if (this == category) {
            return true;
        }
        if (category == null) {
            return false;
        }
        if (this.getClass() != category.getClass()) {
            return false;
        }
        return this.categoryName.equals(((Category) category).categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", tagSet=" + tagSet +
                '}';
    }

}
