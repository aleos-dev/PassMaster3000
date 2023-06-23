package org.application.passwordGenerator;

import lombok.Data;

@Data
public class PasswordOptions {
    private int length;
    private boolean includeUppercase;
    private boolean includeLowercase;
    private boolean includeDigits;
    private boolean includeSpecialChars;

    public PasswordOptions() {
    }

    public PasswordOptions(int length, boolean includeUppercase, boolean includeLowercase,
                           boolean includeDigits, boolean includeSpecialChars) {
        this.length = length;
        this.includeUppercase = includeUppercase;
        this.includeLowercase = includeLowercase;
        this.includeDigits = includeDigits;
        this.includeSpecialChars = includeSpecialChars;
    }
}
