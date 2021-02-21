package pt.upskill.projeto2.financemanager.zextra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/MainScene.fxml"));
        primaryStage.setTitle("Finance Manager");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("gui/FinanceManager.png"))));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();

    }

}

// Icon from https://www.hiclipart.com/free-transparent-background-png-clipart-ipjxg/download