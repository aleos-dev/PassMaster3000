package org.application.gui.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectPasswordException;
import org.application.gui.fx.Utility;
import org.application.gui.fx.services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, SceneController {
    private static final String INCORRECT_CREDENTIALS = "Incorrect credentials";
    private static final String TOO_SHORT_PASSWORD = "Too short password";
    private static final String TOO_SHORT_LOGIN = "Too short login";

    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private AnchorPane sideBar;
    @FXML
    private Button signInButton;
    @FXML
    private Button newUserButton;
    @FXML
    private Button backButton;
    @FXML
    private Button createButton;

    private Stage stage;

    // This method is called after the fxml file has been loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        Utility.enableWindowDrag(sideBar, stage);
    }

    // Trigger login when ENTER is pressed in the password field
    @FXML
    void enterPassword(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            if (signInButton.isVisible()) {
                login();
            } else {
                createNewUser();
            }
        }
    }

    // Log in the user if credentials are correct
    @FXML
    void login() {
        if (UserService.getInstance().authorize(loginField.getText(), passwordField.getText())) {
            openMainScene();
        } else {
            Utility.showPrompt(INCORRECT_CREDENTIALS, loginField);
            Utility.showPrompt(INCORRECT_CREDENTIALS, passwordField);
        }
    }

    // Start the process of creating a new user account
    @FXML
    private void initCreatingNewUser() {
        signInButton.setVisible(false);
        newUserButton.setVisible(false);
        backButton.setVisible(true);
        createButton.setVisible(true);
        loginField.setPromptText("Enter new login");
        passwordField.setPromptText("Enter new password");
    }

    // Create a new user account
    @FXML
    private void createNewUser() {
        try {
            UserService.getInstance().createNewUser(loginField.getText(), passwordField.getText());
        } catch (IncorrectPasswordException e) {
            Utility.handleException(e, TOO_SHORT_PASSWORD, passwordField);
            return;
        } catch (IncorrectLoginNameException e) {
            Utility.handleException(e, TOO_SHORT_LOGIN, loginField);
            return;
        } catch (Exception e) {
            Utility.handleException(e, "", null);
        }
        back();
    }

    // Return to log in screen from new user creation
    @FXML
    private void back() {
        signInButton.setVisible(true);
        newUserButton.setVisible(true);
        backButton.setVisible(false);
        createButton.setVisible(false);
        loginField.setPromptText("Enter Login");
        passwordField.setPromptText("Enter Password");
        loginField.clear();
        passwordField.clear();
    }

    // Open the main application scene
    private void openMainScene() {
        Utility.setNewScene(new FXMLLoader(getClass().getResource("main.fxml")), stage);
    }

    // Close the application
    @FXML
    void closeProgram() {
        stage.close();
    }

}
