package org.application.gui.fx.controller;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.application.exception.user.LoginForWebsiteAlreadyExistException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectWebsiteNameException;
import org.application.gui.fx.Utility;
import org.application.gui.fx.services.CredentialService;
import org.application.gui.fx.services.UserService;
import org.application.objects.website.Credentials;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RecordController implements Initializable, SceneController {
    @FXML
    private TextField recordName;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField recordSearchBar;
    @FXML
    private ListView<String> recordNameListView;
    @FXML
    private CheckBox digits;
    @FXML
    private CheckBox specialChars;
    @FXML
    private CheckBox upperCase;
    @FXML
    private Slider length;
    @FXML
    private Text prompt;

    private final CredentialService credentialService;
    private final UserService userService;
    private String selectedRecordName;
    private ObservableList<String> records;
    private Stage stage;

    public RecordController() {
        this.userService = UserService.getInstance();
        this.credentialService = new CredentialService();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        recordNameListView.getItems().addAll(userService.getWebsitesListOfName());
        records = recordNameListView.getItems();
        setupRecordSearchBarListener();
        setupRecordSearchBarEnterKeyPressed();
        setupRecordListClickEvent();
    }

    // Set up a listener for changes in the record search bar.
    private void setupRecordSearchBarListener() {
        recordSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !Objects.equals(oldValue, newValue)) {
                handleRecordSearchBarListener();
            }
        });
    }

    // This method assigns an event listener to the search bar that will trigger when the Enter key is pressed.
    private void setupRecordSearchBarEnterKeyPressed() {
        recordSearchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && records.size() != 0) {
                handleCredentialDisplay();
            }
        });
    }

    // This method assigns a click event listener to the record list.
    private void setupRecordListClickEvent() {
        recordNameListView.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton() == MouseButton.PRIMARY) {
                handleRecordSelection();
            }
        });
    }

    @FXML
    void showLengthPrompt() {
        String label = String.valueOf((int) length.getValue());
        prompt.setVisible(true);
        prompt.setText(label);
    }

    // Hide length of the password prompt after 1 second
    @FXML
    void hideLengthPrompt() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> prompt.setVisible(false));
        pause.play();
    }

    // Confirm the action based on conditions and handle exceptions if there are any.
    @FXML
    void confirm() {
        String recordName = this.recordName.getText();
        String login = this.login.getText();
        String password = this.password.getText();
        try {
            credentialService.addCredentialToRecord(recordName, login, password);
            openMainScene();
        } catch (LoginForWebsiteAlreadyExistException e) {
            updateCredentialsConfirmation(recordName, login, password);
            openMainScene();
        } catch (IncorrectWebsiteNameException e) {
            Utility.handleException(e, "", this.recordName);
        } catch (IncorrectLoginNameException e) {
            Utility.handleException(e, "", this.login);
        } catch (Exception e) {
            Utility.handleException(e, e.getMessage(), null);
        }
    }

    @FXML
    void generatePassword() {
        String newPassword = Utility.generatePassword((int) length.getValue(), upperCase.isSelected(), digits.isSelected(), specialChars.isSelected());
        password.setText(newPassword);
    }

    @FXML
    private void openMainScene() {
        Utility.setNewScene(new FXMLLoader(getClass().getResource("main.fxml")), stage);
    }

    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void closeProgram() {
        stage.close();
    }

    @FXML
    private void deleteRecord() {
        String headerText = "Need confirmation";
        String record = recordNameListView.getSelectionModel().getSelectedItem();
        String contentText = "Proceed with deleting record: " + record + "?";
        if (Utility.displayAlertAndCheckResponse(Alert.AlertType.CONFIRMATION, headerText, contentText)) {
            try {
                credentialService.deleteRecord(record);
                recordSearchBar.clear();
                recordName.clear();
                login.clear();
                password.clear();
                handleRecordSearchBarListener();
            } catch (IOException e) {
                Utility.displayAlertAndCheckResponse(Alert.AlertType.ERROR, "Unexpected error!", e.getMessage());
            }
        }
    }

    @FXML
    private void deleteCredential() {
        String record = recordNameListView.getSelectionModel().getSelectedItem();
        String login = this.login.getText();
        if (!recordNameListView.getItems().contains(record) || login.isBlank()) {
            return;
        }
        String headerText = "Need confirmation";
        String contentText = "Proceed with deleting login: " + login + "?";

        try {
            if (Utility.displayAlertAndCheckResponse(Alert.AlertType.CONFIRMATION, headerText, contentText)) {
                credentialService.deleteCredential(record, login);
                displayCredential(new Credentials("", ""));
            }
        } catch (Exception e) {
            Utility.handleException(e, "delete credential error", null);
        }
    }

    private void updateCredentialsConfirmation(String recordName, String login, String password) {
        String headerText = "Need confirmation";
        String contentText = "Do you really want to rewrite credential to record: " + recordName + " and login: " + login;
        try {
            if (Utility.displayAlertAndCheckResponse(Alert.AlertType.CONFIRMATION, headerText, contentText)) {
                credentialService.updateCredentialsForRecord(recordName, login, password);
            }
        } catch (Exception e) {
            Utility.handleException(e, "update credentials error", null);
        }
    }

    // Handle search bar text changes.
    private void handleRecordSearchBarListener() {
        recordNameListView.getItems().clear();
        for (String record : userService.getWebsitesListOfName()) {
            if (record.contains(recordSearchBar.getText())) {
                recordNameListView.getItems().add(record);
            }
        }
    }

    private void handleCredentialDisplay() {
        String newSelectedRecord = records.get(0);
        if (!Objects.equals(selectedRecordName, newSelectedRecord)) {
            try {
                credentialService.setCredentials(newSelectedRecord);
            } catch (Exception e) {
                Utility.handleException(e, "decryption exception", null);
            }
            recordSearchBar.setText(newSelectedRecord);
            selectedRecordName = newSelectedRecord;
            recordName.setText(selectedRecordName);
            displayCredential(new Credentials("", ""));
        } else {
            displayCredential(credentialService.getNextCredential());
        }
    }

    // Handle the record selection event.
    private void handleRecordSelection() {
        String selectedRecordName = recordNameListView.getSelectionModel().getSelectedItem();
        if (Objects.equals(selectedRecordName, this.selectedRecordName)) {
            displayCredential(credentialService.getNextCredential());
        } else {
            try {
                this.selectedRecordName = selectedRecordName;
                credentialService.setCredentials(selectedRecordName);
                recordName.setText(selectedRecordName);
                displayCredential(new Credentials("", ""));
            } catch (Exception e) {
                Utility.handleException(e, "decryption exception", null);
            }
        }
    }

    private void displayCredential(Credentials cr) {
        login.setText(cr.login());
        password.setText(cr.password());
    }
}
