package org.application.objects.user;

import org.application.objects.website.Website;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {

    private final String login;
    private final String userPassword;
    private final Map<String, Website> websites;

    public User(String login, String userPassword) {
        this.login = login;
        this.userPassword = userPassword;
        this.websites = new HashMap<>();
    }

    public String getLogin() {
        return login;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Map<String, Website> getWebsites() {
        return websites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getLogin(), user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }
}
