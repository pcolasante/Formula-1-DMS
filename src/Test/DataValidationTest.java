package Test;

import Core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataValidationTest {

    private DataValidation validator;

    @BeforeEach
    void setUp() {

        validator = new DataValidation();
    }

    @Test
    void testIsValidString() {
        assertTrue(validator.isValidString("McLaren"));
        assertFalse(validator.isValidString(""));

    }
    @Test
    void isValidPositiveInt() {
        assertTrue(validator.isValidPositiveInt(15));
    }

    @Test
    void parseInt() {

        assertEquals(15, validator.parseInt("15"));
    }
}