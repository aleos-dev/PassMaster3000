package org.application.cli;

import java.util.Optional;

public enum MenuOption {
    EXIT("Exit."),
    SIGN_IN("Sign in."),
    CREATE_NEW_USER("Create new user."),
    GET_WEBSITE_CREDENTIALS("Get website credentials"),
    ADD_WEBSITE_CREDENTIALS("Add website credentials"),
    UPDATE_WEBSITE_CREDENTIALS("Update website credentials");

    private final String prompt;

    MenuOption(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public static Optional<MenuOption> getOption(int ordinal) {

        MenuOption menuOption = null;
        try {

            menuOption = values()[ordinal];
        } catch (ArrayIndexOutOfBoundsException e) {
            // NOPE
        }

        return Optional.ofNullable(menuOption);
    }
}
