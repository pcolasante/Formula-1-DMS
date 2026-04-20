import Core.*;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class MenuSystem: This class is the main menu system for the console-based application. It will display the main menu and submenus for Drivers, Races, and Championship Standings. 
 It will also handle user input and call the appropriate methods from the InformationManager, FileManager, DataValidation, and ChampionshipCalculator classes.   

 */

public class MenuSystem {

    private final InformationManager informationManager;
    private final FileManager fileManager;
    private final DataValidation validator;
    private final ChampionshipCalculator championshipCalculator;
    private String autoSaveFileName;

    // Constructor for MenuSystem, initializes all components with the provided instances, ensuring separation of concerns and modularity
    public MenuSystem(InformationManager informationManager, FileManager fileManager,
                      DataValidation validator, ChampionshipCalculator championshipCalculator) {

        this.informationManager = informationManager;
        this.fileManager = fileManager;
        this.validator = validator;
        this.championshipCalculator = championshipCalculator;
        this.autoSaveFileName = "f1-data.txt";

    }

    // Method to start the menu system, returns true if the user chooses to exit
    public boolean start() {

        Scanner scanner = new Scanner(System.in);
        int selectChoice;

        do {
            // Sets the main menu
            System.out.println("\n--- FORMULA 1 - DATA MANAGEMENT SYSTEM ---\n");
            System.out.println("1. Drivers");
            System.out.println("2. Races");
            System.out.println("3. Championship Standings");
            System.out.println("4. Save Data to File");
            System.out.println("5. Exit");
            selectChoice = readMenuChoice(scanner, "Select an option: ");

            switch (selectChoice) {

                /*--------------- Menu Options for Drivers ------------------*/
                case 1: 
                    handleDriversMenu(scanner);
                    break;

                /*--------------- Menu Options for Races -------------------*/
                case 2: 
                    handleRacesMenu(scanner);
                    break;

                /*--------------- Menu Options for Championship Standings -------------------*/
                case 3: 
                    runProtectedAction("Standings Calculation", this::showStandings);
                    break;
                
                /*--------------- Menu Options for Saving Data to File -------------------*/
                case 4: 
                    runProtectedAction("save data", () -> saveToFile(scanner) ? 1 : -1);
                    break;

                /*--------------- Menu Options for Exiting the Program -------------------*/
                case 5: 
                    runProtectedAction("auto-save before exit", this::autoSaveCurrentData);
                    System.out.println("EXITING FORMULA 1 DATA MANAGEMENT SYSTEM");
                    break;

                /*--------------- Invalid Selection -------------------*/
                default:
                    System.out.println("INVALID SELECTION");
            }

        }
        while (selectChoice != 5);

        return true;


        }

        //Provides a Drivers Menu, allowing the user to view, add, update, delete, and load drivers from a file. Returns the user's choice to the main menu.
        private int handleDriversMenu(Scanner scanner) {
            int choice;

            do {
                System.out.println("\n--- Drivers ---\n");
                System.out.println("1. View Drivers");
                System.out.println("2. Add Driver");
                System.out.println("3. Update Driver");
                System.out.println("4. Delete Driver");
                System.out.println("5. Load Driver from file");
                System.out.println("6. Return to Main Menu");
                choice = readMenuChoice(scanner, "Select an option: ");

                switch (choice) {
                    case 1:
                        //Displays Drivers
                        runProtectedAction("View Drivers", () -> informationManager.getAllDrivers().size());
                        break;
                    case 2:
                        //Adds a Driver
                        runProtectedAction("Add Driver", () -> addDriver(scanner) ? 1 : -1);
                        break;
                    case 3:
                        //Update a Driver
                        runProtectedAction("Update Driver", () -> updateDriver(scanner) ? 1 : -1);
                        break;
                    case 4:
                        //Delete a Driver
                        runProtectedAction("Delete Driver", () -> deleteDriver(scanner) ? 1 : -1);
                        break;
                    case 5:
                        //Load Driver from File
                        runProtectedAction("Load Drivers", () -> loadFromFile(scanner) ? 1 : -1);
                        break;
                    case 6:
                        //Returning to Main Menu
                        System.out.println("\n--- Return to Main Menu ---\n");
                        break;
                    default:
                        //Input validation
                        System.out.println("\n--- Invalid option ---\n");
                }
            } while (choice != 6);            
            return choice; 
        }

        //Handles the Race Menu, allowing the user to view, add, update, delete, and load races from a file. Returns the user's choice to the main menu.
        private int handleRacesMenu(Scanner scanner) {
            int choice;

            do{
                System.out.println("\n--- Races ---\n");
                System.out.println("1. View Races");
                System.out.println("2. Add Race");
                System.out.println("3. Update Race");
                System.out.println("4. Delete Race");
                System.out.println("5. Load Race from file");
                System.out.println("6. Return to Main Menu");
                System.out.print("Select an option: ");
                choice = readMenuChoice(scanner, "Select an option: ");

                switch (choice) {
                    case 1:
                        //Displays Races
                        runProtectedAction("View Races", () -> informationManager.getAllRaces().size());
                        break;
                    case 2:
                        //Adds a Race
                        runProtectedAction("Add Race", () -> addRace(scanner) ? 1 : -1);
                        break;
                    case 3:
                        //Update a Race
                        runProtectedAction("Update Race", () -> updateRace(scanner) ? 1 : -1);
                        break;
                    case 4:
                        //Delete a Race
                        runProtectedAction("Delete Race", () -> deleteRace(scanner) ? 1 : -1);
                        break;
                    case 5:
                        //Load a Race from File
                        runProtectedAction("Load Races", () -> loadFromFile(scanner) ? 1 : -1);
                        break;
                    case 6:
                        //Returning to Main Menu
                        System.out.println("\n--- Return to Main Menu ---\n");
                        break;
                    default:
                        //Input validation
                        System.out.println("\n--- Invalid option ---\n");
                }

            } while (choice != 6);
            return choice;

        }

        //Method to run protected actions, with error handling to ensure the program continues running smoothly even if an action fails. 
        private int runProtectedAction(String actionName, Action action) {
            try {
                return action.execute();
        } catch (Exception e) {
            System.out.println("Could not complete " + actionName + ". Please try again.");
            return -1;
            }
        }

        //Method to show the championship standings, returns the number of drivers in the standings for confirmation.
        private int showStandings() {
            System.out.println("\n--- Championship Standings ---\n");
            return championshipCalculator.calculateStandings(informationManager.getAllRaces()).size();
        }

        /* ------------------------------------------------------------------------------------ DRIVERS ------------------------------------------------------------------------------------- */


        //Method to add a Driver to file, returns true if successful, otherwise false.
        private boolean addDriver(Scanner scanner) {
            Driver driver = promptDriver(scanner, "Add Driver");
            boolean added = informationManager.addDriver(driver);
            if (added) {
                System.out.println("Driver added successfully.");
                autoSaveAfterChange("Driver Add");
                informationManager.getAllDrivers();
                return true;
            }
            System.out.println("Failed to add driver. Duplicate ID found. Please try again.");
            return false;
        }

        //Method to update a Driver in file, returns true if successful, otherwise false.
        private boolean updateDriver(Scanner scanner) {
            informationManager.getAllDrivers();
            int driverId = readMenuChoice(scanner, "Enter the ID of the driver to update: ");
            Driver updatedDriver = promptDriver(scanner, "Update Driver");
            boolean updated = informationManager.updateDriverById(driverId, updatedDriver);
            if (updated) {
                System.out.println("Driver updated successfully.");
                autoSaveAfterChange("Driver Update");
                informationManager.getAllDrivers();
                return true;
            }
            System.out.println("Failed to update driver. Driver with ID " + driverId + " not found. Please try again.");
            return false;
        }

        //Method to delete a Driver from file, returns true if successful, otherwise false.
        private boolean deleteDriver(Scanner scanner) {
            int driverId = readMenuChoice(scanner, "Enter the ID to delete the Driver: ");
            boolean removed = informationManager.removeDriver(driverId);
            if (removed) {
                System.out.println("Driver deleted successfully.");
                autoSaveAfterChange("Driver Delete");
                informationManager.getAllDrivers();
                return true;
        }

        System.out.println("Failed to delete driver. Driver with ID " + driverId + " not found. Please try again.");
        return false;
        }

        /* ------------------------------------------------------------------------------------RACES ------------------------------------------------------------------------------------- */

        //Method to add a Race to file, returns true if successful, otherwise false.
        private boolean addRace(Scanner scanner) {
            Race race = promptRace(scanner, "Add Race");
            boolean added = informationManager.addRace(race);
            if (added) {
                System.out.println("Race added successfully.");
                autoSaveAfterChange("Race Add \n");
                informationManager.getAllRaces();
                return true;
            }
            
            System.out.println("Failed to add race. Please try again.");
            return false;

        }

        //Method to update a Race in file, returns true if successful, otherwise false.
        private boolean updateRace(Scanner scanner) {
            informationManager.getAllRaces();
            int raceId = readMenuChoice(scanner, "Enter the ID of the race to update: ");
            Race updatedRace = promptRace(scanner, "Update  Race");
            boolean updated = informationManager.updateRaceById(raceId, updatedRace);
            if (updated) {
                System.out.println("Race updated successfully.");
                autoSaveAfterChange("Race Update");
                informationManager.getAllRaces();
                return true;
            }
            System.out.println("Failed to update race. Race with ID " + raceId + " not found. Please try again.");
            return false;
        }   

        //Method to delete a Race from file, returns true if successful, otherwise false.
        private boolean deleteRace(Scanner scanner) {
            int raceId = readMenuChoice(scanner, "Enter the ID to delete the race: ");
            boolean removed = informationManager.deleteRace(raceId);
            if (removed) {
                System.out.println("Race deleted successfully.");
                autoSaveAfterChange("Race Delete");
                informationManager.getAllRaces();
                return true;
            }
            System.out.println("Failed to delete race. Race with ID " + raceId + " not found. Please try again.");
            return false;
        }

        //Method to load data from a file, returns true if successful, otherwise false.
        private boolean loadFromFile(Scanner scanner) {
            String fileName = readNonEmptyLine(scanner, "Enter the file name: ", true);
            boolean loaded = fileManager.loadFromFile(fileName, informationManager);
            System.out.println(loaded ? "Finished processing file." : "File loading failed. Please try again.");
            return loaded;
        }

        //Method to save and auto-save data to a file, returns true if successful, otherwise false.
        private boolean saveToFile(Scanner scanner) {
            String fileName = readNonEmptyLine(scanner, "Enter the file name to save: ", true);
            boolean saved = fileManager.saveToFile(fileName, informationManager);
            if (saved) {
                autoSaveFileName = fileName;
            }
            System.out.println(saved ? "Data saved successfully to file." : "Failed to save data. Please try again.");
            return saved;
        }

        //Method to auto-save current data to a file, returns 1 if successful, otherwise -1.
        private int autoSaveCurrentData() {
            boolean saved = fileManager.saveToFile(autoSaveFileName, informationManager);
            if (saved) {
                return 1;
            }

            System.out.println("Auto-save failed. Please ensure you have saved data to a file at least once during this session.");
            return -1;
        }

        //Method to auto-save data after changes, providing feedback to the user about the auto-save status.
        private void autoSaveAfterChange(String changeType) {
            int result = autoSaveCurrentData();
            if (result > 0) {
                System.out.println("Auto-saved after " + changeType + " to file: " + autoSaveFileName + ".");
            }
        }

        //Method to prompt the Driver's information from the user, returns a Driver object with the provided information.
        private Driver promptDriver (Scanner scanner, String action) {
            System.out.println("\n--- " + action + " ---\n");
           // int id = readMenuChoice(scanner, "Enter Driver ID:");

            int id;
            while (true) {
                id = readMenuChoice(scanner, "Enter Driver ID: ");

                if (informationManager.driverExists(id)) {
                    System.out.println("This Driver ID already exists. Please enter a unique ID.");
                } else {
                    break;
                }
            }
            String name = readNonEmptyLine(scanner, "Name: ", false);
            int carNumber = readMenuChoice(scanner, "Car number: ");
            String nationality = readNonEmptyLine(scanner, "Nationality: ", false);
            String team = readNonEmptyLine(scanner, "Team: ", false);
            int racesEntered = readMenuChoice(scanner, "Races entered: ");
            int podiums = readMenuChoice(scanner, "Podiums: ");
            int raceWins = readMenuChoice(scanner, "Race wins: ");
            int totalPoints = readMenuChoice(scanner, "Total points: ");
            boolean activeStatus = readBoolean(scanner, "Active status (true/false): ");
            return new Driver(id, name, carNumber, nationality, team, racesEntered, podiums, raceWins, totalPoints, activeStatus);

        }

        //Method to prompt the Race information from the user, returns a Race object with the provided information.
        private Race promptRace(Scanner scanner, String action) {
        System.out.println("--- " + action + " ---");
        //int raceId = readMenuChoice(scanner, "Race ID: ");

            int raceId;
            while (true) {
                raceId = readMenuChoice(scanner, "Enter Race ID: ");

                if (informationManager.raceExists(raceId)) {
                    System.out.println("This Race ID already exists. Please enter a unique ID.");
                } else {
                    break;
                }
            }

        Driver driver = readExistingDriverOrNone(scanner);
        String raceName = readNonEmptyLine(scanner, "Race name: ", true);
        String location = readNonEmptyLine(scanner, "Location: ", false);
        String country = readNonEmptyLine(scanner, "Country: ", false);
        String date = readNonEmptyLine(scanner, "Date: ", true);
        int totalLaps = readMenuChoice(scanner, "Total laps: ");
        int position = readMenuChoice(scanner, "Position: ");
        int result = readMenuChoice(scanner, "Points earned: ");

        return new Race(raceId, driver, raceName, location, country, date, totalLaps, position, result);
    }

        //Method to read an existing Driver's ID from the user and return the corresponding Driver object, ensuring that the driver exists before proceeding.
        private Driver readExistingDriverOrNone(Scanner scanner) {
            if (informationManager.getDriversData().isEmpty()) {
                System.out.println("No drivers are available. Race will be saved without a linked driver.");
                return null;
            }

            while (true) {
                int driverId = readMenuChoice(scanner, "Associated driver ID (0 for none): ");
                if (driverId == 0) {
                    return null;
                }

                Driver driver = informationManager.getDriverById(driverId);
                if (driver != null) {
                    return driver;
                }
                System.out.println("No driver found with that ID. Please try again or enter 0 for none.");
            }
        }


        //Method to read a menu choice from the user, ensuring that the input is a valid positive integer, and returns the parsed integer value.
        private int readMenuChoice(Scanner scanner, String prompt) {
            while (true) {
                System.out.print(prompt);
                String input = scanner.nextLine();
                Integer parsed = validator.parseInt(input);
                if (parsed != null && validator.isValidPositiveInt(parsed)) {
                    return parsed;
                }
                System.out.println("Invalid input. Please enter a positive integer. Try again.");
            }
        }

        //Method to read a non-empty string from the user, ensuring that the input is valid and not empty, and returns the trimmed string value.
        private String readNonEmptyLine(Scanner scanner, String prompt, boolean allowNumbers) {
            while (true) {
                System.out.print(prompt);
                String input = scanner.nextLine();

                boolean isValid = validator.isValidString(input);

                if (!allowNumbers) {
                    isValid = isValid && !input.matches(".*\\d.*");
                }

                if (isValid) {
                    return input.trim();
                }
                System.out.println("Invalid input. This value cannot be empty nor contain numbers. Please try again.");
            }
        }

        //Method to read a boolean value from the user, ensuring that the input is either "true" or "false", and returns the corresponding boolean value.
        private boolean readBoolean(Scanner scanner, String prompt) {
            while (true) {
                System.out.print(prompt);
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("true")) {
                    return true;
                } else if (input.equals("false")) {
                    return false;
                }
                System.out.println("Invalid input. Please enter 'true' or 'false'.");
            }
        }

        private interface Action {
            int execute() throws Exception;
        }
    }