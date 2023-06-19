package org.application;

import org.application.exception.encryption.DecryptionException;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.encryption.InvalidEncryptionKeyException;
import org.application.exception.passwordmanager.LoginDoesNotExistException;
import org.application.exception.passwordmanager.WebsiteDoesNotExistException;
import org.application.exception.user.LoginForWebsiteAlreadyExistException;
import org.application.exception.user.UserAlreadyExistException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectPasswordException;
import org.application.exception.validator.ValidatorException;
import org.application.objects.user.User;
import org.application.objects.website.Website;
import org.application.passwordGenerator.Generator;
import org.application.passwordGenerator.MakePassword;
import org.application.passwordGenerator.PasswordOptions;
import org.application.services.JSONService;

import java.io.IOException;
import java.util.Map;

public class Main {


    public static void main(String[] args) throws IOException, InvalidEncryptionKeyException, EncryptionException {
        UserManager userManager = new UserManager();

        System.out.println("----- Create user test-----");
        String userName = "Al";
        String password = "asd";
        // creating new user
        while (true) {
            try {
                userManager.createNewUser(userName, password);
                break;

            } catch (UserAlreadyExistException e) {
                System.out.println("UserName: " + userName + " ||| " + e.getMessage());
                break;

            } catch (IncorrectLoginNameException e) {
                System.out.println("UserName: " + userName + " ||| " + e.getMessage());
                userName = "Aleos";

            } catch (IncorrectPasswordException e) {
                System.out.println("password: " + password + " ||| " + e.getMessage());
                password = "asdf";
            }
        }

        System.out.println();

// Need to implement exception handler for incorrect user or password
        System.out.println("----- Authorize user test-----\n");

        System.out.println("----- Bad password -----");
        if (userManager.authorize(userName, "asd")) {
            System.out.println("Authorization was successful");
        } else {
            System.out.println("Username or password were incorrect");
        }

        System.out.println("----- Correct password -----");
        if (userManager.authorize(userName, "asdf")) {
            System.out.println("Authorization was successful");
        } else {
            System.out.println("Username or password were incorrect");
        }


        System.out.println("\n\n----- Password manager -----");
        var passwordManager = new PasswordManager(userManager);

        Generator generator = new MakePassword();
        String passForWebsite = generator.passwordGenerator(new PasswordOptions(16, true, true, true, true));
        System.out.println("Generated password is: " + passForWebsite);

        System.out.println("\n----- Get + Add website credentials test-----");
        try {
            passwordManager.addWebsiteCredentials("google.com", "aleos", passForWebsite);
            System.out.println("Website credential was saved");

            System.out.println("Get website record from file:\n " + userManager.getUser().getWebsites());
            System.out.println("Get website record: " + passwordManager.getCredentialsForWebsite("google.com"));
        } catch (ValidatorException | LoginForWebsiteAlreadyExistException | DecryptionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n----- Update credentials -----");
        try {
            System.out.println("Old website record: " + passwordManager.getCredentialsForWebsite("google.com"));
            passwordManager.updateCredentialsForWebsite("google.com", "aleos", "TESTPASS");
            System.out.println("New website record: " + passwordManager.getCredentialsForWebsite("google.com"));
        } catch (LoginDoesNotExistException | WebsiteDoesNotExistException | DecryptionException e) {
            System.out.println(e.getMessage());
        }

    }

}