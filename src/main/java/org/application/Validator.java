package org.application;

public class Validator {

    public boolean validateLogin(String login) {
        if (login.matches("^\\w{3,20}$")) {
            return true;
        }
        return false;
    }

    public boolean validatePassword(String password) {
        if (password.matches("^[A-Za-z0-9]{4,}$")) {
            return true;
        }
        return false;
    }

    public boolean validateWebsiteName(String website) {
        if (website.matches("^(https?://)?([\\w.-]+)\\.([a-zA-Z]{2,})(/\\S*)?$")) {
            return true;
        }
        return false;
    }
}
