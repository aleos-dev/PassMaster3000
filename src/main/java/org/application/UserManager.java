package org.application;

import org.application.encryption.EncryptionModule;
import org.application.exception.encryption.DecryptionException;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.encryption.InvalidEncryptionKeyException;
import org.application.exception.user.UserAlreadyExistException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectPasswordException;
import org.application.objects.user.User;
import org.application.services.JSONService;

import java.io.IOException;
import java.util.Map;

public class UserManager {
    private final JSONService jService = JSONService.service;
    private Map<String, User> usersDatabase;
    private EncryptionModule encryptionModule;
    private User authorizedUser;

    public UserManager() {
        getUsersDatabase();
    }

    public void createNewUser(String userName, String password) throws IOException, IncorrectPasswordException, IncorrectLoginNameException, InvalidEncryptionKeyException, EncryptionException {
        if (usersDatabase.containsKey(userName))
            throw new UserAlreadyExistException("This user already exists, please try another username");
        else {
            var validator = new Validator();
            if (validator.validateLogin(userName)) {
                throw new IncorrectLoginNameException("Login should be between 3 and 20 alphanumeric characters.");
            }

            if (!validator.validatePassword(password)) {
                throw new IncorrectPasswordException("Password should contain a minimum of 4 alphanumeric characters.");
            }

            encryptionModule = new EncryptionModule(password);
            String encryptedPassword = encryptionModule.encrypt(password);


            User user = new User(userName, encryptedPassword);
            usersDatabase.put(userName,user);
            saveUsersDatabase(user);
        }
    }

    public void saveUsersDatabase(User user) throws IOException {
        usersDatabase.put(user.getLogin(), user);
        jService.writeToFile(user);
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
            System.out.println("Something went wrong.");
        }

        return false;

    }

    public void deleteAccount(String userName) {
        usersDatabase.remove(userName);
        try {
            jService.removeUserFromDatabase(userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser() {
        return authorizedUser;
    }

    public EncryptionModule getEncryptionModule() {
        return encryptionModule;
    }

}
