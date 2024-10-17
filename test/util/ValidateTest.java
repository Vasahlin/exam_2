package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateTest {

    @Test
    void validName() {
        assertTrue(Validate.validName("Carl"));
        assertTrue(Validate.validName("Carl Carlsson"));
        assertTrue(Validate.validName("john"));
        assertTrue(Validate.validName("john smith"));
        assertTrue(Validate.validName("JOHN SMITH"));
        assertFalse(Validate.validName("1234"));
        assertFalse(Validate.validName(""));
        assertFalse(Validate.validName("b1"));
    }

    @Test
    void validateSocialSecurity() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity("12345-1234"));
        e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity("123456-123"));
        assertEquals("Social security was not properly formatted", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity("123456-12345"));
        e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity("123456-1234a"));
        e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity("a12345-1234"));
        e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity("123456-12345-"));
        e = assertThrows(IllegalArgumentException.class, () ->
                Validate.validateSocialSecurity(""));
        assertDoesNotThrow(() -> Validate.validateSocialSecurity("820402-1234"));
    }

    @Test
    void validSocialSecurity() {
        assertTrue(Validate.validSocial("7703021234"));
        assertFalse(Validate.validSocial("770302"));
    }

    @Test
    void notTenDigits() {
        assertTrue(Validate.notTenDigits("0123456"));
        assertFalse(Validate.notTenDigits("0123456789"));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Validate.notTenDigits("asd"));
        assertEquals(e.getMessage(), "Social Security must only contain digits");
    }

    @Test
    void notValidFormat() {
        assertTrue(Validate.notValidFormat("a23466789"));
        assertFalse(Validate.notValidFormat("7703021234"));
    }

    @Test
    void validateName() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Validate.validateName("1a B"));
        assertDoesNotThrow(() -> Validate.validateName("1a Ba"));
    }


    @Test
    void ableToParseSocial() {
       assertTrue(Validate.ableToParseSocial("891023-1234"));
       assertFalse(Validate.ableToParseSocial("891023-12345"));
       assertFalse(Validate.ableToParseSocial("8910232-1234"));
       assertFalse(Validate.ableToParseSocial("891023-a12"));
    }
}