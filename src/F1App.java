/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026


 Program Objective: Build a Formula 1 Management System to maintain and keep record of drivers and race results. It will also calculate championship standings based on the driver's gained points.
 This includes adding, removing, and displaying a list of current drivers and races. It should also be able to exit the program.
 The user will be asked to input driver or race details to either add/remove.
 The program will return the driver list, and a confirmation.

 Class: F1App (Main class): Will launch the main console-based application.

 */
public class F1App {

    public static void main(String[] args) {

        DataValidation validator = new DataValidation();

        FileManager fileManager =
                new FileManager();

        InformationManager manager =
                fileManager.loadFromFile();

        ChampionshipCalculator calculator =
                new ChampionshipCalculator();

        MenuSystem menu =
                new MenuSystem(manager, fileManager, validator, calculator);

        boolean saved = menu.start();

        if (!saved) {
            System.out.println("Error saving data.");
        }
    }

}