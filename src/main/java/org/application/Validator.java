package org.application;

public class Validator {

    public boolean validateLogin(String login) {
        if (login.matches("^\\w{3,20}$")){
            return true;
        }
        return false;
    }

    public boolean validatePassword(String password) {
        // Method implementation
        return false;
    }

    public boolean validateWebsiteName(String website) {
        // Method implementation
        return false;
    }
}
