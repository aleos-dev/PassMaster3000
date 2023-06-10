package org.application.passwordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MakePassword implements Generator{
    @Override
    public String passwordGenerator(PasswordOptions passwordOptions) {
        StringBuilder password = new StringBuilder();
        List<Character> charSet = new ArrayList<>();


        if (passwordOptions.isIncludeUppercase()) {
            for (char c = 'A'; c <= 'Z'; c++) {
                charSet.add(c);
            }
        }

        if (passwordOptions.isIncludeLowercase()) {
            for (char c = 'a'; c <= 'z'; c++) {
                charSet.add(c);
            }
        }

        if (passwordOptions.isIncludeDigits()) {
            for (char c = '0'; c <= '9'; c++) {
                charSet.add(c);
            }
        }

        if (passwordOptions.isIncludeSpecialChars()) {
            String specialCharacters = "!@#$%^&*()_+{}[]~-";
            for (char c : specialCharacters.toCharArray()) {
                charSet.add(c);
            }
        }

        Random random = new Random();

        for (int i = 0; i < passwordOptions.getLength(); i++) {
            int randomIndex = random.nextInt(charSet.size());
            char element = charSet.get(randomIndex);
            password.append(element);
        }

        return password.toString();
    }
}
