package pt.upskill.projeto2.financemanager.gui;

import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.utils.Menu;


/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class PersonalFinanceManagerUserInterface {

    public PersonalFinanceManagerUserInterface(
            PersonalFinanceManager personalFinanceManager) {
        this.personalFinanceManager = personalFinanceManager;
    }

    private static final String OPT_GLOBAL_POSITION = "Posição Global";
    private static final String OPT_ACCOUNT_STATEMENT = "Movimentos Conta";
    private static final String OPT_LIST_CATEGORIES = "Listar categorias";
    private static final String OPT_ANALISE = "Análise";
    private static final String OPT_EXIT = "Sair";

    private static final String OPT_MONTHLY_SUMMARY = "Evolução global por mês";
    private static final String OPT_PREDICTION_PER_CATEGORY = "Previsão gastos totais do mês por categoria";
    private static final String OPT_ANUAL_INTEREST = "Previsão juros anuais";

    private static final String[] OPTIONS_ANALYSIS = {OPT_MONTHLY_SUMMARY, OPT_PREDICTION_PER_CATEGORY, OPT_ANUAL_INTEREST};
    private static final String[] OPTIONS = {OPT_GLOBAL_POSITION,
            OPT_ACCOUNT_STATEMENT, OPT_LIST_CATEGORIES, OPT_ANALISE, OPT_EXIT};

    private final PersonalFinanceManager personalFinanceManager;

    public void execute() {
        try {
            while (true) {
                String option = Menu.requestSelection("Menu", OPTIONS);

                switch (option) {
                    case OPT_GLOBAL_POSITION:
                        if (personalFinanceManager.hasAccounts()) {
                            personalFinanceManager.globalPosition();
                        } else {
                            System.out.println("Não existem contas.");
                        }
                        break;
                    case OPT_ACCOUNT_STATEMENT:
                        if (personalFinanceManager.hasAccounts()) {
                            personalFinanceManager.accountStatement();
                        } else {
                            System.out.println("Não existem contas.");
                        }
                        break;
                    case OPT_LIST_CATEGORIES:
                        personalFinanceManager.listCategories();
                        break;
                    case OPT_ANALISE:
                        try {
                            analise();
                        } catch (UnknownAccountException e) {
                            System.out.println("Não existem contas.");
                        }
                        break;
                    default:
                        if(Menu.yesOrNoInput("Deseja guardar as Contas?")){
                            personalFinanceManager.saveAccounts();
                            if(Menu.yesOrNoInput("Deseja apagar os ficheiros de Statements?")){
                                PersonalFinanceManager.deleteStatementFiles();
                            }
                        }
                        System.exit(0);
                }
            }
        } catch (NullPointerException e) {
            // Case of cancel would return NullPointerException
            // End of program
        }

    }


    // First Menu Methods
    private void analise() throws UnknownAccountException {
        String option = Menu.requestSelection("", OPTIONS_ANALYSIS);
        if (!personalFinanceManager.hasAccounts()) {
            throw new UnknownAccountException();
        } else {
            switch (option) {
                case OPT_MONTHLY_SUMMARY:
                    personalFinanceManager.monthlySummary();
                    break;
                case OPT_PREDICTION_PER_CATEGORY:
                    personalFinanceManager.predictionPerCategory();
                    break;
                case OPT_ANUAL_INTEREST:
                    personalFinanceManager.anualInterest();
                    break;
            }
        }
    }

}
