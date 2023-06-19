package org.application.gui.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.application.gui.fx.controller.LoginController;

public class PM_Application extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/org/application/gui/fx/controller/login.fxml"));
        Parent root = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Pass Master 3000");
        stage.setScene(scene);
        loginController.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}