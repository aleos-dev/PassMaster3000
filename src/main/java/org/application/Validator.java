package org.application;

public class Validator {

    public boolean validateLogin(String login) {
        return !login.matches("^\\w{3,20}$");
    }

    public boolean validatePassword(String password) {
        return password.matches("^[A-Za-z0-9]{4,}$");
    }

    public boolean validateWebsiteName(String website) {
        return website.matches("^(https?://)?([\\w.-]+)\\.([a-zA-Z]{2,})(/\\S*)?$");
    }
}
