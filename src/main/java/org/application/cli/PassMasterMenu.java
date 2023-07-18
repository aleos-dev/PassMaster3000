package org.application.cli;

import org.application.PasswordManager;
import org.application.UserManager;

import java.util.Optional;
import java.util.Scanner;

public class PassMasterMenu {
    private final Scanner scanner;

    public PassMasterMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        var userManager = new UserManager();
        var passwordManager = new PasswordManager(userManager);
        MenuHandler handler = new MenuHandler(userManager, passwordManager, scanner);

        Optional<MenuOption> menuOption;
        do {

            printMenu(userManager.getUser() == null);

            int menuIndex = Integer.parseInt(scanner.nextLine());
            menuOption = MenuOption.getOption(menuIndex);

            if (menuOption.isEmpty()) {
                System.out.println("Incorrect option. Please try again;");
                continue;
            }

            handler.handle(menuOption.get());

        } while (menuOption.isEmpty() || !menuOption.get().name().equals("EXIT"));
    }

    private void printMenu(boolean wasAuthorization) {

        System.out.println("=== Menu ===");

        MenuOption[] arr = MenuOption.values();
        int size = arr.length;

        if (wasAuthorization) {
            for (int i = 1; i <= arr.length; i++) {
                if (arr[i % size] == MenuOption.SIGN_IN || arr[i % size] == MenuOption.CREATE_NEW_USER || arr[i % size] == MenuOption.EXIT) {
                    System.out.println(i % size + ". " + arr[i % size].getPrompt());
                }
            }
        } else {
            for (int i = 1; i <= arr.length; i++) {
                if (arr[i % size] == MenuOption.GET_WEBSITE_CREDENTIALS || arr[i % size] == MenuOption.ADD_WEBSITE_CREDENTIALS || arr[i % size] == MenuOption.UPDATE_WEBSITE_CREDENTIALS || arr[i % size] == MenuOption.EXIT) {
                    System.out.println(i % size + ". " + arr[i % size].getPrompt());
                }

            }
        }
    }
}
