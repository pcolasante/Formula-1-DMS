package Core;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class Core.FileManager: This class will validate the data input in the program, ensuring the program does not crash due to invalid data.

 */
public class DataValidation {
    public boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public boolean isValidPositiveInt(int value) {
        return value >= 0;
    }

    public Integer parseInt(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
