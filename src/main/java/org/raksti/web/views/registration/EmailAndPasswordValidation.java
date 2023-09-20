package org.raksti.web.views.registration;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailAndPasswordValidation {

    private final static String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$";

    private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private final static Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return !matcher.matches();
    }

    public boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
}
