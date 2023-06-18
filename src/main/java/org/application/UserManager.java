package org.application;

import org.application.encryption.EncryptionModule;
import org.application.exception.encryption.DecryptionException;
import org.application.exception.encryption.InvalidEncryptionKeyException;
import org.application.objects.user.User;
import org.application.services.JSONService;

import java.util.Map;

public class UserManager {
    private final JSONService jService = JSONService.service;
    private Map<String, User> usersDatabase;
    private EncryptionModule encryptionModule;
    private User authorizedUser;

    UserManager() {
        getUsersDatabase();
    }

    public boolean createNewUser(String userName, String password) {
        // Method implementation
        return false;
    }

    public void saveUsersDatabase(User user) {
        usersDatabase.put(user.getLogin(), user);
    }

    private void getUsersDatabase() {
        this.usersDatabase = jService.getDatabase();
    }

    public boolean authorize(String userName, String password) {
        if (!usersDatabase.containsKey(userName)) {
            return false;
        }

        try {

            String encryptedPassword = usersDatabase.get(userName).getUserPassword();
            var encModule = new EncryptionModule(password);

            if (password.equals(encModule.decrypt(encryptedPassword))) {
                authorizedUser = usersDatabase.get(userName);
                encryptionModule = encModule;
                return true;
            }
        } catch (InvalidEncryptionKeyException | DecryptionException ignored) {
            /* ignored */
        }

        return false;

    }

    public User getUser() {
        return authorizedUser;
    }

    public EncryptionModule getEncryptionModule() {
        return encryptionModule;
    }

}
