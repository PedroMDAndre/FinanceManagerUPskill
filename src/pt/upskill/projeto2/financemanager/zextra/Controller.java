package pt.upskill.projeto2.financemanager.zextra;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.utils.Menu;


import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Controller {
    private static Stage aboutMsb = new Stage();
    private static PersonalFinanceManager personalFinanceManager;
    private static boolean areFilesLoaded = false;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private VBox vBoxMain;


    private static void setLoadedFiles() {
        areFilesLoaded = true;
    }

    private static void showAlertNotLoaded() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please load the account files first!");

        alert.showAndWait();
    }


    // Menu File
    @FXML
    void handleFileLoad(ActionEvent event) {
        personalFinanceManager = new PersonalFinanceManager();
        setLoadedFiles();
    }

    @FXML
    void handleFileSave(ActionEvent event) {
        if (areFilesLoaded) {
            personalFinanceManager.saveAccounts();
            if (Menu.yesOrNoInput("Deseja apagar os ficheiros de Statements?")) {
                PersonalFinanceManager.deleteStatementFiles();
            }
        } else {
            showAlertNotLoaded();
        }
    }

    @FXML
    void handleFileQuit(ActionEvent event) {
        if (areFilesLoaded) {
            if (Menu.yesOrNoInput("Deseja guardar as contas?")) {
                personalFinanceManager.saveAccounts();
                if (Menu.yesOrNoInput("Deseja apagar os ficheiros de Statements?")) {
                    PersonalFinanceManager.deleteStatementFiles();
                }
            }
        }
        System.exit(0);
    }


    // Menu General
    @FXML
    void handleGeneralGlobalPosition(ActionEvent event) {
        if (areFilesLoaded) {
            fillText(personalFinanceManager.globalPosition());
        } else {
            showAlertNotLoaded();
        }
    }

    @FXML
    void handleGeneralAccountStatement(ActionEvent event) {
        if (areFilesLoaded) {
            fillText(personalFinanceManager.accountStatement());

        } else {
            showAlertNotLoaded();
        }
    }

    @FXML
    void handleGeneralListCategories(ActionEvent event) {
        TreeView<String> treeCategories = null;
        try {
            treeCategories = FXMLLoader.load(getClass().getResource("gui/CategoriesScene.fxml"));
            mainPane.getChildren().setAll(treeCategories);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Bind Size to mainPain
        treeCategories.prefWidthProperty().bind(mainPane.widthProperty());
        treeCategories.prefHeightProperty().bind(mainPane.heightProperty());

        // Add Root Main
        TreeItem<String> rootItem = new TreeItem<>("Categories");
        rootItem.setExpanded(true);
        treeCategories.setRoot(rootItem);

        // Add children
        List<Category> categories = Category.readCategories();
        for (Category category : categories) {
            TreeItem<String> treeItem = new TreeItem<>(category.getName());
            treeItem.setExpanded(true);
            rootItem.getChildren().add(treeItem);

            Set<String> tags = category.getTagSet();
            for (String tag : tags) {
                TreeItem<String> treeChild = new TreeItem<>(tag);
                treeChild.setExpanded(true);
                treeItem.getChildren().add(treeChild);
            }
        }
    }


    // Menu Help
    @FXML
    void loadAboutMsg(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("gui/AboutMsb.fxml"));
            Scene aboutWindow = new Scene(root, 300, 150);

            aboutMsb.setTitle("About");
            aboutMsb.getIcons().add(new Image(String.valueOf(getClass().getResource("gui/FinanceManager.png"))));
            aboutMsb.setScene(aboutWindow);
            aboutMsb.resizableProperty().setValue(Boolean.FALSE);

            aboutMsb.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSubmitbtClose(ActionEvent event) {
        aboutMsb.close();
    }


    // Menu Analyze
    public void handleAnalyzeMonthlySummary(ActionEvent actionEvent) {
        if (areFilesLoaded) {
            fillText(personalFinanceManager.monthlySummary());
        } else {
            showAlertNotLoaded();
        }
    }

    public void handleAnalyzeDraftPredictionCategory(ActionEvent actionEvent) {
        if (areFilesLoaded) {
            fillText(personalFinanceManager.predictionPerCategory());
        } else {
            showAlertNotLoaded();
        }
    }

    public void handleAnalyzeAnnualInterest(ActionEvent actionEvent) {
        if (areFilesLoaded) {
            fillText(personalFinanceManager.anualInterest());
        } else {
            showAlertNotLoaded();
        }
    }


    private void fillText(String input) {
        ScrollPane scrollPane = new ScrollPane();
        Text text = new Text(50,100,input);

        scrollPane.setContent(text);

        mainPane.getChildren().setAll(scrollPane);

        scrollPane.prefWidthProperty().bind(mainPane.widthProperty());
        scrollPane.prefHeightProperty().bind(mainPane.heightProperty());

        text.setFont(Font.font("Consolas", FontWeight.THIN, 14));   // Monospaced font

        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setLineSpacing(2);
    }
}
