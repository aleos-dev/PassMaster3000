package org.application;

public class Validator {

    public boolean validateLogin(String login) {
        // Method implementation
        return false;
    }

    public boolean validatePassword(String password) {
        if (password.matches("^[A-Za-z0-9]{4,}$")) {
            return true;
        }
        return false;
    }

    public boolean validateWebsiteName(String website) {
        // Method implementation
        return false;
    }
}
