package org.application.gui.fx.services;

import org.application.PasswordManager;
import org.application.UserManager;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.encryption.InvalidEncryptionKeyException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectPasswordException;

import java.io.IOException;
import java.util.Set;

public class UserService {

    private final static UserManager USER_MANAGER = new UserManager();
    private PasswordManager passwordManager;
    private static UserService instance = null;

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
    }

    public boolean authorize(String userName, String userPassword) {
        if (USER_MANAGER.authorize(userName, userPassword)) {
            setPasswordManager();
            return true;
        }
        return false;
    }

    public void createNewUser(String userName, String userPassword) throws IncorrectPasswordException, InvalidEncryptionKeyException, EncryptionException, IncorrectLoginNameException, IOException {
        USER_MANAGER.createNewUser(userName, userPassword);
    }


    public String getUserName() {
        return USER_MANAGER.getUser().getLogin();
    }

    public Set<String> getWebsitesListOfName() {
        return USER_MANAGER.getUser().getWebsites().keySet();
    }

    public void deleteAccount() {
        String currentAccount = getUserName();
        USER_MANAGER.deleteAccount(currentAccount);
    }

    public PasswordManager getPasswordManager() {
        return passwordManager;
    }

    public void setPasswordManager() {
        passwordManager = new PasswordManager(UserService.USER_MANAGER);
    }
}
