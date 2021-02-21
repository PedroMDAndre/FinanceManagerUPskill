package pt.upskill.projeto2.financemanager;

import java.io.File;

public class FileDirPath {
    public static File dirAccountInfo = new File("account_info/");  // Directório com os ficheiros de contas
    public static File dirStatements = new File("statements/");     // Directório com os ficheiros de statements
    public static File categoriesFile = new File("account_info/categories"); // Ficheiro com as categorias
    public static String dirSavePath = "account_info/";                 // Directório para guardar as contas
}
