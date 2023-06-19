package org.application.gui.fx.services;

import org.application.PasswordManager;
import org.application.exception.encryption.DecryptionException;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.passwordmanager.LoginDoesNotExistException;
import org.application.exception.passwordmanager.WebsiteDoesNotExistException;
import org.application.exception.user.LoginForWebsiteAlreadyExistException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectWebsiteNameException;
import org.application.objects.website.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CredentialService {
    PasswordManager passwordManager;
    List<Credentials> credentials;
    private int credentialIndex = 0;

    public CredentialService() {
        this.passwordManager = UserService.getInstance().getPasswordManager();
    }

    public void setCredentials(String websiteName) throws DecryptionException {
        credentialIndex = 0;
        credentials = new ArrayList<>(passwordManager.getCredentialsForWebsite(websiteName));
    }

    public void addCredentialToRecord(String websiteName, String loginName, String password) throws LoginForWebsiteAlreadyExistException, EncryptionException, IncorrectLoginNameException, IOException, IncorrectWebsiteNameException {
        passwordManager.addWebsiteCredentials(websiteName, loginName, password);
    }

    public void deleteRecord(String recordName) throws IOException {
        passwordManager.deleteWebsite(recordName);
    }

    public List<Credentials> getCredentialsForSelectedWebsite() {
        return credentials;
    }

    public Credentials getNextCredential() {
        int size = credentials.size();
        if (size == 0) {
            return new Credentials("", "");
        }

        return credentials.get(credentialIndex++ % size);
    }

    public void deleteCredential(String record, String login) throws IOException, WebsiteDoesNotExistException {
       passwordManager.deleteCredential(record, login);
       credentials.remove(new Credentials(login, ""));
    }

    public void updateCredentialsForRecord(String recordName, String login, String password) throws EncryptionException, IOException, LoginDoesNotExistException, WebsiteDoesNotExistException {
        passwordManager.updateCredentialsForWebsite(recordName, login, password);
    }
}
