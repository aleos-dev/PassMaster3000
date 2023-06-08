package org.application.objects.password;

import java.util.Objects;

public class PasswordObject {

    private String password;
    private String secretKey;
    private String website;

    public PasswordObject() {
    }

    public PasswordObject(String password, String secretKey, String website) {
        this.password = password;
        this.secretKey = secretKey;
        this.website = website;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordObject password1)) return false;
        return Objects.equals(getPassword(), password1.getPassword()) && Objects.equals(getWebsite(), password1.getWebsite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getWebsite());
    }
}
