package org.application.gui.fx;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.application.exception.encryption.DecryptionException;
import org.application.exception.passwordmanager.LoginDoesNotExistException;
import org.application.exception.passwordmanager.WebsiteDoesNotExistException;
import org.application.exception.user.LoginForWebsiteAlreadyExistException;
import org.application.exception.user.UserAlreadyExistException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectWebsiteNameException;
import org.application.gui.fx.controller.SceneController;
import org.application.passwordgenerator.RandomPasswordGenerator;
import org.application.passwordgenerator.PasswordCriteria;

import java.io.IOException;

public class Utility {
    private static final String RED_TEXT_STYLE = "-fx-text-fill: red;";
    private static final String DEFAULT_TEXT_STYLE = "-fx-text-fill: black;";
    private static final String DECRYPTION_ERROR = "Something went wrong with decryption";
    private static final String UNKNOWN_ERROR = "Error was happened!";
    private static final String IO_EXCEPTION = "IOException error!";
    private static final String INCORRECT_WEBSITE_NAME = "Incorrect website name";
    private static final String LOGIN_DOES_NOT_EXIST = "Can't find this login";
    private static final String WEBSITE_DOES_NOT_EXIST = "Can't find this record";
    private static final String USER_ALREADY_EXIST = "Incorrect user name";

    private static final RandomPasswordGenerator passwordGenerator = new RandomPasswordGenerator();

    // Method to set a new scene with a given loader and stage
    public static void setNewScene(FXMLLoader loader, Stage stage) {
        try {
            Parent root = loader.load();
            SceneController controller = loader.getController();
            if (controller == null) {
                System.out.println("Controller is null");
                return;
            }
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            controller.setStage(stage);
            stage.show();
        } catch (Exception e) {
            handleException(e, e.getMessage(), null);
        }
    }

    // Method to handle exceptions and display appropriate error messages
    public static void handleException(Exception e, String errorDetails, TextField textField) {
        if (e instanceof IncorrectWebsiteNameException) {
            showPrompt(INCORRECT_WEBSITE_NAME, textField);
        } else if (e instanceof LoginForWebsiteAlreadyExistException) {
            showPrompt(errorDetails, textField);
        } else if (e instanceof IncorrectLoginNameException) {
            showPrompt(errorDetails, textField);
        } else if (e instanceof DecryptionException) {
            displayAlertAndCheckResponse(Alert.AlertType.WARNING, DECRYPTION_ERROR, e.getMessage());
        } else if (e instanceof WebsiteDoesNotExistException) {
            displayAlertAndCheckResponse(Alert.AlertType.WARNING, WEBSITE_DOES_NOT_EXIST, e.getMessage());
        } else if (e instanceof LoginDoesNotExistException) {
            displayAlertAndCheckResponse(Alert.AlertType.WARNING, LOGIN_DOES_NOT_EXIST, e.getMessage());
        } else if (e instanceof IOException) {
            displayAlertAndCheckResponse(Alert.AlertType.WARNING, IO_EXCEPTION, e.getMessage());
        } else if (e instanceof UserAlreadyExistException) {
            displayAlertAndCheckResponse(Alert.AlertType.WARNING, USER_ALREADY_EXIST, e.getMessage());
        } else {
            displayAlertAndCheckResponse(Alert.AlertType.ERROR, UNKNOWN_ERROR, e.getMessage());
        }
    }

    // Method to show a prompt with error details in a text field
    public static void showPrompt(String errorDetails, TextField textField) {
        String originalText = textField.getText();
        textField.setStyle(RED_TEXT_STYLE);
        textField.setText(errorDetails);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            textField.clear();
            textField.setStyle(DEFAULT_TEXT_STYLE);
            textField.setText(originalText);
        });
        pause.play();
    }

    public static String generatePassword(int length, boolean upperCase, boolean digits, boolean specialChars) {
        var passwordOption = new PasswordCriteria(length, upperCase, true, digits, specialChars);
        return passwordGenerator.generate(passwordOption);
    }

    // Method to display an alert with the given error type and details, and check the user's response
    public static boolean displayAlertAndCheckResponse(Alert.AlertType alertType, String errorType, String errorDetails) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(errorType);
        alert.setContentText(errorDetails);
        alert.showAndWait();
        return alert.resultProperty().get().getButtonData().isDefaultButton();
    }
}
