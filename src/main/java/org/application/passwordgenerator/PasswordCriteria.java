package org.application.passwordgenerator;

public record PasswordCriteria(int length, boolean includeUppercase, boolean includeLowercase, boolean includeDigits,
                               boolean includeSpecialChars) {
    public final static PasswordCriteria DEFAULT = new PasswordCriteria(8, true, true, true, false);
}
