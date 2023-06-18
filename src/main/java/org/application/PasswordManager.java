package org.application;

import org.application.encryption.EncryptionModule;
import org.application.objects.user.User;
import org.application.objects.website.Credentials;
import org.application.objects.website.Website;

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

    public Set<Credentials> getCredentialsForWebsite(String website) {
        Website websiteDetails = getWebsite(website);
        return websiteDetails.getCredentials();
    }

    public void addWebsiteCredentials(String website, String login, String password) {
        Website websiteDetails = getWebsite(website);
        websiteDetails.addOrUpdateCredentials(login, password);
    }

    public void updateCredentialsForWebsite(String website, String login, String password) {
        addWebsiteCredentials(website, login, password);
    }

    public void changeUserCredentials(String userName, String password) {
        this.user.setLogin(userName);
        this.user.setUserPassword(password);
    }

    private Website getWebsite(String website) {
        Map<String, Website> websites = this.user.getWebsites();
        return websites.get(website);
    }
}
