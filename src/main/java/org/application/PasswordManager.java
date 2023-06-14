package org.application;

import org.application.encryption.EncryptionModule;
import org.application.objects.user.User;
import org.application.objects.website.Credentials;

public class PasswordManager {
    private final UserManager userManager;
    private final User user;
    private final EncryptionModule encryptionModule;

    PasswordManager(UserManager userManager) {
        this.userManager = userManager;
        this.user = userManager.getUser();
        this.encryptionModule = userManager.getEncryptionModule();

    }

    public Credentials getCredentialsForWebsite(String website) {
        // Method implementation
        return null;
    }

    public boolean addWebsiteCredentials(String website, String login, String password) {
        // Method implementation
        return false;
    }

    public boolean updateCredentialsForWebsite(String website, String login, String password) {
        // Method implementation
        return false;
    }

    public boolean changeUser(String userName, String password) {
        // Method implementation
        return false;
    }


}
