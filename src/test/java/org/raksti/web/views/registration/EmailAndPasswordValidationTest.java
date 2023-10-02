package org.raksti.web.views.registration;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailAndPasswordValidationTest {

    @Test
    public void testValidatePassword() {
        EmailAndPasswordValidator emailAndPasswordValidation = new EmailAndPasswordValidator();

        assertFalse(emailAndPasswordValidation.isValidPassword("12345678"));
        assertFalse(emailAndPasswordValidation.isValidPassword("ABCDEFGH"));
        assertFalse(emailAndPasswordValidation.isValidPassword("abcdefgh"));

        assertTrue(emailAndPasswordValidation.isValidPassword("Abc6!fgh"));
        assertTrue(emailAndPasswordValidation.isValidPassword("Parole88"));
        assertTrue(emailAndPasswordValidation.isValidPassword("666Parole"));

        assertFalse(emailAndPasswordValidation.isValidPassword("ParoleAB"));

    }
}