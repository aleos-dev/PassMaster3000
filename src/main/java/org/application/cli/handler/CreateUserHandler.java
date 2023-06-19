package org.application.cli.handler;

import org.application.UserManager;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.encryption.InvalidEncryptionKeyException;
import org.application.exception.validator.IncorrectLoginNameException;
import org.application.exception.validator.IncorrectPasswordException;

import java.io.IOException;
import java.util.Scanner;

public class CreateUserHandler {
    Scanner console = new Scanner(System.in);
    private final UserManager userManager;
    private final SignInHandler signInHandler;

    public CreateUserHandler(UserManager userManager) {
        this.userManager = userManager;
        signInHandler = new SignInHandler(userManager);
    }

    public void handle() {
        System.out.println("Type your Login");
        String nameLogin = console.nextLine();
        System.out.println("Type your Password");
        String passwordInProgram = console.nextLine();


        try {
            userManager.createNewUser(nameLogin, passwordInProgram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IncorrectPasswordException e) {
            throw new RuntimeException(e);
        } catch (IncorrectLoginNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidEncryptionKeyException e) {
            throw new RuntimeException(e);
        } catch (EncryptionException e) {
            throw new RuntimeException(e);
        }

        // This is the handler for creating a user. There is no need to do anything else here.
        // However, if you still want to perform it, you can check the authorization using `userManager.getUser()`.
        // If it returns a non-null value, it indicates that the user is authorized.
        System.out.println("Do you want to continue working with the program? [Type y or n and press Enter]");
        String choice = console.nextLine();
        if (choice.equals("y")) {
            userManager.authorize(nameLogin, passwordInProgram);

            // Specifically, entering credentials should be done using the corresponding option in the menu.
//            signInHandler.addNewPassword();
        } else if (choice.equals("n")) {
            System.out.println("Thank you for using our program");
        }
    }
}
