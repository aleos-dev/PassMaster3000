package org.application;

import org.application.objects.user.User;
import org.application.services.JSONService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        User user = new User("Vasya", "vasya@gmail.com");
        JSONService jsonService = JSONService.service;
        jsonService.writeToFile(user);

    }
}