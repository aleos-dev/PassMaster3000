package org.application;

import org.application.objects.website.Website;
import org.application.services.JSONService;
import org.application.objects.user.User;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        User user = new User("Vasya", "vasya@gmail.com");
        JSONService jsonService = JSONService.service;
        jsonService.writeToFile(user);

    }
}