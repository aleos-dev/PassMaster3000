package org.application;

import org.application.encryption.EncryptionModule;
import org.application.exception.encryption.DecryptionException;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.passwordmanager.LoginDoesNotExistException;
import org.application.exception.passwordmanager.WebsiteDoesNotExistException;
import org.application.exception.user.LoginForWebsiteAlreadyExistException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectWebsiteNameException;
import org.application.objects.user.User;
import org.application.objects.website.Credentials;
import org.application.objects.website.Website;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PasswordManager {
    private final UserManager userManager;
    private final User user;
    private final EncryptionModule encryptionModule;

    PasswordManager(UserManager userManager) {
        this.userManager = userManager;
        this.user = userManager.getUser();
        this.encryptionModule = userManager.getEncryptionModule();

    }

    public Set<Credentials> getCredentialsForWebsite(String website) throws DecryptionException {
        Website webs = getWebsite(website);

        Set<Credentials> credentials = new HashSet<>();
        for (var crd : webs.getCredentials()) {
            String login = encryptionModule.decrypt(crd.login());
            String password = encryptionModule.decrypt(crd.password());
            credentials.add(new Credentials(login, password));
        }

        return credentials;
    }

    public void addWebsiteCredentials(String websiteName, String login, String password) throws IncorrectWebsiteNameException, LoginForWebsiteAlreadyExistException, EncryptionException, IOException, IncorrectLoginNameException {
        Validator validator = new Validator();
        if (!validator.validateWebsiteName(websiteName)) {
            throw new IncorrectWebsiteNameException("Website name should be a valid URL format.");
        }

        if (!validator.validateLogin(login)) {
            throw new IncorrectLoginNameException("Login should be between 3 and 20 alphanumeric characters.");
        }

        var website = new Website(websiteName);
        String encryptedLogin = encryptionModule.encrypt(login);
        String encryptedPassword = encryptionModule.encrypt(password);
        website.addOrUpdateCredentials(encryptedLogin, encryptedPassword);

        if (getWebsite(websiteName) != null && getWebsite(websiteName).doesLoginExist(login)) {
            throw new LoginForWebsiteAlreadyExistException("The current login already exists. Please try a different one.");
        }

        user.addWebsite(website.getName(), website);

        userManager.saveUsersDatabase(user);
    }

    public void updateCredentialsForWebsite(String websiteName, String login, String password) throws EncryptionException, LoginDoesNotExistException, WebsiteDoesNotExistException, IOException {

        var website = getWebsite(websiteName);

        if (website == null) {
            throw new WebsiteDoesNotExistException("The provided website name does not exist.");
        }

        String encryptedLogin = encryptionModule.encrypt(login);
        if (!website.doesLoginExist(encryptedLogin)) {
            throw new LoginDoesNotExistException("The provided login does not exist.");
        }

        String encryptedPassword = encryptionModule.encrypt(password);
        website.addOrUpdateCredentials(encryptedLogin, encryptedPassword);

        userManager.saveUsersDatabase(user);
    }

    public void changeUserCredentials(String userName, String password) {
        // TODO: Implement this.
    }

    private Website getWebsite(String website) {
        Map<String, Website> websites = this.user.getWebsites();
        return websites.get(website);
    }
}
