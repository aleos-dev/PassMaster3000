package org.application.cli.handler;

import org.application.UserManager;

import java.util.Scanner;

public class SignInHandler {

    Scanner console = new Scanner(System.in);


    private final UserManager userManager;

    public SignInHandler(UserManager userManager) {
        this.userManager = userManager;
    }

    public void handle(){
        System.out.println("Type your Login");
        String login = console.nextLine();
        System.out.println("Type your Password");
        String password = console.nextLine();
        userManager.authorize(login,password);
    }
}
