package Test;

import Core.DataValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Paulina Flores Colasante
 * CEN 3024 - Software Development 1
 * Date: 4/8/2026
 * DataValidationTest.java: This class will test and validate the data input in the program, ensuring the program does not crash due to invalid data.
 */

class DataValidationTest {

    private DataValidation validator;

    @BeforeEach
    void setUp() {

        validator = new DataValidation();
    }

    /**
     * Method to test if a string is valid, returns an assertation
     */
    @Test
    void testIsValidString() {
        assertTrue(validator.isValidString("McLaren"));
        assertFalse(validator.isValidString(""));

    }

    /**
     * Method to test if Integer is valid and positive, returns assertation
     */
    @Test
    void isValidPositiveInt() {
        assertTrue(validator.isValidPositiveInt(15));
    }

    /**
     * Method to test if integer can be parsed from a string, returns assertation
     */
    @Test
    void parseInt() {

        assertEquals(15, validator.parseInt("15"));
    }
}