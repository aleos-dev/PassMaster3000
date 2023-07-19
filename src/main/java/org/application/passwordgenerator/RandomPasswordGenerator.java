package org.application.passwordgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPasswordGenerator implements PasswordGenerator {
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+{}[]~-";

    @Override
    public String generate(PasswordCriteria passwordCriteria) {
        List<Character> availableCharacters = new ArrayList<>();

        if (passwordCriteria.includeUppercase()) {
            addCharactersToSet('A', 'Z', availableCharacters);
        }
        if (passwordCriteria.includeLowercase()) {
            addCharactersToSet('a', 'z', availableCharacters);
        }
        if (passwordCriteria.includeDigits()) {
            addCharactersToSet('0', '9', availableCharacters);
        }
        if (passwordCriteria.includeSpecialChars()) {
            SPECIAL_CHARACTERS.chars().mapToObj(c -> (char) c).forEach(availableCharacters::add);
        }

        return constructRandomPassword(availableCharacters, passwordCriteria.length());
    }

    private void addCharactersToSet(char start, char end, List<Character> set) {
        IntStream.rangeClosed(start, end)
                .mapToObj(i -> (char) i)
                .forEach(set::add);
    }

    private String constructRandomPassword(List<Character> charSet, int length) {
        Random random = new Random();

        return IntStream.range(0, length)
                .mapToObj(i -> charSet.get(random.nextInt(charSet.size())))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
