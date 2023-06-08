package org.application.objects.user;

import org.application.objects.password.PasswordObject;

import java.util.List;
import java.util.Objects;

public class User {

    private String name;
    private String email;
    private String userPassword;
    private List<PasswordObject> passwords;

    public User() {
    }

    public User(String name, String email, String userPassword, List<PasswordObject> passwords) {
        this.name = name;
        this.email = email;
        this.userPassword = userPassword;
        this.passwords = passwords;
    }

    public User(String name, String email, String userPassword) {
        this.name = name;
        this.email = email;
        this.userPassword = userPassword;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<PasswordObject> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<PasswordObject> passwords) {
        this.passwords = passwords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
