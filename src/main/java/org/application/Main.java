package org.application;

import org.application.services.JSONService;
import org.application.objects.password.PasswordObject;
import org.application.objects.user.User;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        User user = new User("Vasya", "vasya@gmail.com");
        user.setPasswords(new ArrayList<>() {{
            add(new PasswordObject());
        }});
        JSONService service = JSONService.getInstance();
        service.writeToFile(user);
    }
}