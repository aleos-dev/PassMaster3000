package org.application.passwordGenerator;

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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isIncludeUppercase() {
        return includeUppercase;
    }

    public void setIncludeUppercase(boolean includeUppercase) {
        this.includeUppercase = includeUppercase;
    }

    public boolean isIncludeLowercase() {
        return includeLowercase;
    }

    public void setIncludeLowercase(boolean includeLowercase) {
        this.includeLowercase = includeLowercase;
    }

    public boolean isIncludeDigits() {
        return includeDigits;
    }

    public void setIncludeDigits(boolean includeDigits) {
        this.includeDigits = includeDigits;
    }

    public boolean isIncludeSpecialChars() {
        return includeSpecialChars;
    }

    public void setIncludeSpecialChars(boolean includeSpecialChars) {
        this.includeSpecialChars = includeSpecialChars;
    }
}
