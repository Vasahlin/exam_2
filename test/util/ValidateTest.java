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
    void validSocialSecurity() {
        assertTrue(Validate.validSocialSecurity("7703021234"));
        assertFalse(Validate.validSocialSecurity("770302"));
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
}