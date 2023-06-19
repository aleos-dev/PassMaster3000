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
import org.application.gui.fx.Utility;
import org.application.gui.fx.services.ClipboardService;
import org.application.gui.fx.services.CredentialService;
import org.application.gui.fx.services.UserService;
import org.application.objects.website.Credentials;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable, SceneController {
    @FXML
    private Label currentUserLabel;
    @FXML
    private Button copyLoginButton;
    @FXML
    private Button copyPasswordButton;
    @FXML
    private ComboBox<String> loginComboBox;
    @FXML
    private Text passwordDisplay;
    @FXML
    private TextField recordSearchBar;
    @FXML
    private ListView<String> recordNameListView;

    private final CredentialService credentialService;
    private final ClipboardService clipboardService;
    private final UserService userService;
    private Stage stage;
    private String selectedRecordName;
    private ObservableList<String> records;

    // Constructor initialising the services used in this controller
    public MainController() {
        this.clipboardService = new ClipboardService();
        this.userService = UserService.getInstance();
        this.credentialService = new CredentialService();
    }

    // Called to initialize a controller after its root element has been completely processed
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUserLabel.setText(userService.getUserName());
        recordNameListView.getItems().addAll(userService.getWebsitesListOfName());
        records = recordNameListView.getItems();
        setupRecordSearchBarListener();
        setupRecordSearchBarEnterKeyPressed();
        setupRecordListClickEvent();
    }

    // Sets up a listener for changes in the record search bar
    private void setupRecordSearchBarListener() {
        recordSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !Objects.equals(oldValue, newValue)) {
                handleRecordSearchBarListener();
            }
        });
    }

    // Sets up a handler for pressing enter in search bar
    private void setupRecordSearchBarEnterKeyPressed() {
        recordSearchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && records.size() != 0) {
                selectFirstRecordInList();
            }
        });
    }

    // Sets up a handler for click events in the record list
    private void setupRecordListClickEvent() {
        recordNameListView.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton() == MouseButton.PRIMARY) {
                handleRecordSelection();
            }
        });
    }

    // Opens the record management scene
    @FXML
    void openRecordManagementScene() {
        Utility.setNewScene(new FXMLLoader(getClass().getResource("record.fxml")), stage);
    }

    // Copies the login from the combo box to the clipboard
    @FXML
    void copyLoginFromBoxToClipboard() {
        String login = loginComboBox.getPromptText();
        if (clipboardService.copyToClipboard(login)) {
            showCopiedToClipboardPrompt(copyLoginButton);
        }
    }

    // Copies the password from the display field to the clipboard
    @FXML
    void copyPasswordFromDisplayFieldToClipboard() {
        String password = passwordDisplay.getText();
        if (clipboardService.copyToClipboard(password)) {
            showCopiedToClipboardPrompt(copyPasswordButton);
        }
    }

    // Signs out and loads the login scene
    @FXML
    void signOutAndLoadLoginScene() {
        Utility.setNewScene(new FXMLLoader(getClass().getResource("login.fxml")), stage);
    }

    // Deletes the user account
    @FXML
    void deleteAccount() {
        String headerText = "Need confirmation";
        String contentText = "Proceed with deleting account: " + userService.getUserName() + "?";
        if (Utility.displayAlertAndCheckResponse(Alert.AlertType.CONFIRMATION, headerText, contentText)) {
            userService.deleteAccount();
            signOutAndLoadLoginScene();
        }
    }

    // Closes the program
    @FXML
    void closeProgram() {
        stage.close();
    }

    // Sets the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Handles the change of the record search bar
    private void handleRecordSearchBarListener() {
        recordNameListView.getItems().clear();
        for (String record : userService.getWebsitesListOfName()) {
            if (record.contains(recordSearchBar.getText())) {
                records.add(record);
            }
        }
    }

    // Handles the event of selecting a record
    private void handleRecordSelection() {
        String selectedRecordName = recordNameListView.getSelectionModel().getSelectedItem();
        if (!Objects.equals(selectedRecordName, this.selectedRecordName)) {
            setupCredentialsForSelectedRecord(selectedRecordName);
        } else {
            fetchAndDisplayNextCredential();
        }
    }

    // Displays a "Copied" prompt on the button
    private void showCopiedToClipboardPrompt(Button button) {
        String label = button.getText();
        button.setText("Copied");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> button.setText(label));
        pause.play();
    }

    // Fetches and displays the credentials for a selected record
    private void setupCredentialsForSelectedRecord(String selectedRecordName) {
        try {
            credentialService.setCredentials(selectedRecordName);
            this.selectedRecordName = selectedRecordName;
            setupCredentialsInLoginBox();
        } catch (Exception e) {
            Utility.handleException(e, "", null);
        }
    }

    // Sets up the login combo box with the credentials for a record
    private void setupCredentialsInLoginBox() {
        List<Credentials> credentials = credentialService.getCredentialsForSelectedWebsite();
        ObservableList<String> loginsInComboBox = loginComboBox.getItems();
        loginsInComboBox.clear();
        for (var cr : credentials) {
            loginsInComboBox.add(cr.login());
        }
        fetchAndDisplayNextCredential();
    }

    // Fetches and displays the next credential in the list
    private void fetchAndDisplayNextCredential() {
        var cr = credentialService.getNextCredential();
        setLoginPromptInLoginBox(cr);
        setPasswordInPasswordDisplay(cr);
    }

    // Sets the password in the password display
    private void setPasswordInPasswordDisplay(Credentials credential) {
        passwordDisplay.setText(credential.password());
    }

    // Sets the login prompt in the login box
    private void setLoginPromptInLoginBox(Credentials credential) {
        loginComboBox.promptTextProperty().set(credential.login());
    }

    // Selects the first record in the list if the list is not empty
    private void selectFirstRecordInList() {
        String newSelectedRecord = records.get(0);
        if (!Objects.equals(selectedRecordName, newSelectedRecord)) {
            handleSelectionOfNewRecord(newSelectedRecord);
        } else {
            fetchAndDisplayNextCredential();
        }
    }

    // Handles the selection of a new record in the list
    private void handleSelectionOfNewRecord(String newSelectedRecord) {
        try {
            credentialService.setCredentials(newSelectedRecord);
        } catch (Exception e) {
            Utility.handleException(e, "", null);
        }
        recordSearchBar.setText(newSelectedRecord);
        selectedRecordName = newSelectedRecord;
        setupCredentialsInLoginBox();
    }
}
