package org.application.objects.website;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Website {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("credentials")
    private final Set<Credentials> credentials;

    public Website() {
        this.name = "";
        this.credentials = new HashSet<>();
    }
    
    public Website(String name) {
        this.name = name;
        this.credentials = new HashSet<>();
    }

    public Set<Credentials> getCredentials() {
        return credentials;
    }

    public void addOrUpdateCredentials(String login, String password) {
        Credentials credentials = new Credentials(login, password);
        this.credentials.removeIf(credentials::equals);
        this.credentials.add(credentials);
    }


    public boolean doesLoginExist(String login) {
        return credentials.stream()
                .anyMatch(credentials -> credentials.login().equals(login));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Website{" +
                "name='" + name + '\'' +
                ", credentials=" + credentials +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Website website)) return false;
        return Objects.equals(getName(), website.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
