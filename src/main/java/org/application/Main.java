package org.application;

import org.application.objects.user.User;
import org.application.objects.website.Website;
import org.application.services.JSONService;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONService jsonService = JSONService.service;
        User user = new User("Vasya", "vasya@gmail.com");
        Website website = new Website();
        Website website1 = new Website();
        Website website2 = new Website();
        website.addOrUpdateCredentials("login", "password");
        website1.addOrUpdateCredentials("login", "password");
        website2.addOrUpdateCredentials("login", "password");
        user.addWebsite("google", website);
        user.addWebsite("youtube", website1);
        user.addWebsite("github", website2);
        jsonService.writeToFile(user);

        User user2 = new User("Petya", "petya@gmail.com");
        Website website3 = new Website();
        Website website4 = new Website();
        Website website5 = new Website();
        website3.addOrUpdateCredentials("login", "password");
        website4.addOrUpdateCredentials("login", "password");
        website5.addOrUpdateCredentials("login", "password");
        user2.addWebsite("google", website3);
        user2.addWebsite("youtube", website4);
        user2.addWebsite("github", website5);
        jsonService.writeToFile(user2);


    }
}