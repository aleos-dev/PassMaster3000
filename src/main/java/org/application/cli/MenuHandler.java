package org.application.cli;

import org.application.PasswordManager;
import org.application.UserManager;
import org.application.cli.handler.*;

import java.util.Scanner;

public class MenuHandler {
    // try to use only 1 scanner
    Scanner scanner;

    private final UserManager userManager;
    private final PasswordManager passwordManager;

    private final SignInHandler signInHandler;
    private final CreateUserHandler createUserHandler;
    private final GetWebsiteCredentialsHandler getWebsiteCredentialsHandler;
    private final AddWebsiteCredentialsHandler addWebsiteCredentialsHandler;
    private final UpdateWebsiteCredentialsHandler updateWebsiteCredentialsHandler;


    public MenuHandler(UserManager userManager, PasswordManager passwordManager, Scanner scanner) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.passwordManager = passwordManager;

        this.signInHandler = new SignInHandler(userManager);
        this.createUserHandler = new CreateUserHandler(userManager);
        this.getWebsiteCredentialsHandler = new GetWebsiteCredentialsHandler();
        this.addWebsiteCredentialsHandler = new AddWebsiteCredentialsHandler();
        this.updateWebsiteCredentialsHandler = new UpdateWebsiteCredentialsHandler();
    }

    public void start() {
        System.out.println("Sign in [Type 1 press Enter] Register [Type 2 press Enter]");
        int variable = scanner.nextInt();
        if (variable == 1) {
            signInHandler.handle();
        } else if (variable == 2) {
            createUserHandler.handle();
        }
    }


    public void handle(MenuOption option) {
        switch (option) {
            case SIGN_IN -> signInHandler.handle();
            case CREATE_NEW_USER -> createUserHandler.handle();
            case GET_WEBSITE_CREDENTIALS -> System.out.println("get");
            case ADD_WEBSITE_CREDENTIALS -> System.out.println("add");
            case UPDATE_WEBSITE_CREDENTIALS -> System.out.println("update");
            case EXIT -> System.out.println("exit");
        }
    }

}
