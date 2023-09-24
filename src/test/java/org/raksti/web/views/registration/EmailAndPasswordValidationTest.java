package org.raksti.web.views.registration;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailAndPasswordValidationTest {

    @Test
    public void testValidatePassword() {
        EmailAndPasswordValidator emailAndPasswordValidation = new EmailAndPasswordValidator();

        assertFalse(emailAndPasswordValidation.validatePassword("12345678"));
        assertFalse(emailAndPasswordValidation.validatePassword("ABCDEFGH"));
        assertFalse(emailAndPasswordValidation.validatePassword("abcdefgh"));

        assertTrue(emailAndPasswordValidation.validatePassword("Abc6!fgh"));
        assertTrue(emailAndPasswordValidation.validatePassword("Parole88"));
        assertTrue(emailAndPasswordValidation.validatePassword("666Parole"));

        assertFalse(emailAndPasswordValidation.validatePassword("ParoleAB"));

    }
}