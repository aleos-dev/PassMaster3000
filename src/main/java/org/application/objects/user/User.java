package org.application.objects.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.application.objects.website.Website;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String userPassword;
    @JsonProperty("websites")
    private final Map<String, Website> websites;

    public User() {
        this.login = "login";
        this.userPassword = "password";
        this.websites = new HashMap<>();
    }

    public User(String login, String userPassword) {
        this.login = login;
        this.userPassword = userPassword;
        this.websites = new HashMap<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public Map<String, Website> getWebsites() {
        return websites;
    }

    public void addWebsite(String link, Website website) {

        websites.put(link, website);
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", websites=" + websites +
                '}';
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
