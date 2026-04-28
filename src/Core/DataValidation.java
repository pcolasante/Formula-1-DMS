package Core;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class Core.FileManager: This class will validate the data input in the program, ensuring the program does not crash due to invalid data.

 */
public class DataValidation {

    //Method to state if string is valid. Should accept a String that is not empty.
    public boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    //Method to state if an Integer is valid. Should accept an Int is positive.
    public boolean isValidPositiveInt(int value) {
        return value >= 0;
    }

    //Method to return the Integers from Strings
    public Integer parseInt(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
