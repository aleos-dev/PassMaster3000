package org.application.objects.website;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class Website {
    @JsonProperty("credentials")
    private final Set<Credentials> credentials;

    public Website() {
        this.credentials = new HashSet<>();
    }

    public Set<Credentials> getCredentials() {
        return credentials;
    }

    @Override
    public String toString() {
        return "Website{" +
                "credentials=" + credentials +
                '}';
    }

    public void addOrUpdateCredentials(String login, String password) {
        Credentials credentials = new Credentials(login, password);
        this.credentials.removeIf(credentials::equals);
        this.credentials.add(credentials);
    }
}
