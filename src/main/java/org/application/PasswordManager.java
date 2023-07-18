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

    public PasswordManager(UserManager userManager) {
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

        if (validator.validateLogin(login)) {
            throw new IncorrectLoginNameException("Login should be between 3 and 20 alphanumeric characters.");
        }
        Website website = user.getWebsites().get(websiteName);
        String encryptedLogin = encryptionModule.encrypt(login);
        if ( website == null) {
            website = new Website(websiteName);
        } else if (website.doesLoginExist(encryptedLogin)) {
            throw new LoginForWebsiteAlreadyExistException("The current login already exists. Please try a different one.");
        }

        String encryptedPassword = encryptionModule.encrypt(password);
        website.addOrUpdateCredentials(encryptedLogin, encryptedPassword);

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

    public void deleteWebsite(String websiteName) throws IOException {

        user.getWebsites().remove(websiteName);
        userManager.saveUsersDatabase(user);
    }

    public void deleteCredential(String websiteName, String login) throws WebsiteDoesNotExistException, IOException {

        var website = getWebsite(websiteName);
        if (website == null) {
            throw new WebsiteDoesNotExistException("The provided website name does not exist.");
        }

        try {
            String encryptedLogin = encryptionModule.encrypt(login);
            website.getCredentials().remove(new Credentials(encryptedLogin, ""));
        } catch (EncryptionException e) {
            throw new RuntimeException(e);
        }
        userManager.saveUsersDatabase(user);
    }

    private Website getWebsite(String website) {
        Map<String, Website> websites = this.user.getWebsites();
        return websites.get(website);
    }
}
