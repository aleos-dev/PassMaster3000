package org.application.objects.website;

import java.util.HashSet;
import java.util.Set;

public class Website {

    private final Set<Credentials> credentials;

    public Website() {
        this.credentials = new HashSet<>();
    }

    public void addOrUpdateCredentials(String login, String password) {
        Credentials credentials = new Credentials(login, password);
        this.credentials.add(credentials);
    }
}
