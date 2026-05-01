package UI;

import Core.ChampionshipCalculator;
import Core.DataValidation;
import Core.FileManager;
import Core.InformationManager;


import java.util.Objects;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026


 Program Objective: Build a Formula 1 Management System to maintain and keep record of drivers and race results. It will also calculate championship standings based on the driver's gained points.
 This includes adding, removing, and displaying a list of current drivers and races. It should also be able to exit the program.
 The user will be asked to input driver or race details to either add/remove.
 The program will return the driver list, and a confirmation.

 Class: UI.F1App (Main class): Will launch the main console-based application.

 */
public class F1App {

    // Declare all components as final to ensure immutability and thread safety
    private final DataValidation validator;
    private final FileManager fileManager;
    private final InformationManager informationManager;
    private final ChampionshipCalculator championshipCalculator;

    // Default constructor initializes all components with their default implementations
    public F1App() {
        validator = new DataValidation();
        fileManager = new FileManager();
        informationManager = new InformationManager();
        championshipCalculator = new ChampionshipCalculator();
    }

    // Constructor for dependency injection, allows easier testing and flexibility
    public F1App(DataValidation validator,
                 FileManager fileManager,
                 InformationManager manager,
                 ChampionshipCalculator calculator) {
        this.validator = Objects.requireNonNull(validator, "Validator is required");
        this.fileManager = Objects.requireNonNull(fileManager, "FileManager is required");
        this.informationManager = Objects.requireNonNull(manager, "Manager is required");
        this.championshipCalculator = Objects.requireNonNull(calculator, "Calculator is required");
    }

    //Create and start the menu system, returning true if the user chooses to exit
    public boolean run() {
        MenuSystem menu = new MenuSystem(informationManager, fileManager, validator, championshipCalculator);
        boolean started = menu.start();
        if (!started) {
            System.out.println("Failed to start the menu system. Exiting application.");
            return false;
        }

        return true;
    }

    //Builds menu system, easier testing.
    private MenuSystem buildMenuSystem() {
        return new MenuSystem(informationManager, fileManager, validator, championshipCalculator);
    }

    // Main method to launch the application
    public static void main(String[] args) {
        if (args != null && args.length > 0 && "--cli".equalsIgnoreCase(args[0])) {
            new F1App().run();
            return;
        }

        F1AppGUI.main(args);
    }
}