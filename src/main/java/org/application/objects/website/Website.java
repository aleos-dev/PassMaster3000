package org.application.objects.website;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class Website {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("credentials")
    private final Set<Credentials> credentials;

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
                "credentials=" + credentials +
                '}';
    }
}
