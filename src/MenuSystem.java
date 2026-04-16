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
    private final ChampionshipCalculator calculator;

    // Constructor for MenuSystem, initializes all components with the provided instances, ensuring separation of concerns and modularity
    public MenuSystem(InformationManager informationManager, FileManager fileManager,
                      DataValidation validator, ChampionshipCalculator calculator) {

        this.informationManager = informationManager;
        this.fileManager = fileManager;
        this.validator = validator;
        this.calculator = calculator;

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
                        runProtectedAction("Add Driver", () -> informationManager.addDriver() ? 1 : -1);
                        break;
                    case 3:
                        //Update a Driver
                        runProtectedAction("Update Driver", () -> informationManager.updateDriver() ? 1 : -1);
                        break;
                    case 4:
                        //Delete a Driver
                        runProtectedAction("Delete Driver", () -> informationManager.removeDriver() ? 1 : -1);
                        break;
                    case 5:
                        //Load Driver from File
                        runProtectedAction("Load Drivers", () -> informationManager.loadFromFile() ? 1 : -1);
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
                        runProtectedAction("View Races", () -> informationManager.getAllRaces().size());
                        break;
                    case 2:
                        runProtectedAction("Add Race", () -> informationManager.addRace() ? 1 : -1);
                        break;
                    case 3:
                        runProtectedAction("Update Race", () -> informationManager.updateRace() ? 1 : -1);
                        break;
                    case 4:
                        runProtectedAction("Delete Race", () -> informationManager.deleteRace() ? 1 : -1);
                        break;
                    case 5:
                        runProtectedAction("Load Races", () -> informationManager.loadFromFile() ? 1 : -1);
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

        private int runProtectedAction(String actionName, Action action) {
            try {
                return action.execute();
        } catch (Exception e) {
            System.out.println("Could not complete " + actionName + ". Please try again.");
            return -1;
            }
        }

        private int showStandings() {
            System.out.println("\n--- Championship Standings ---\n");
            return calculator.calculateStandings(manager.getAllRaces()).size();
        }

        private boolean addDriver() {
            Driver driver = promptDriver(scanner, "Add Driver");
            boolean added = informationManager.addDriver(driver);
            if (added) {
                System.out.println("Driver added successfully.");
                return true;
            }
            System.out.println("Failed to add driver. Duplicate ID found. Please try again.");
            return false;
        }

        private boolean updateDriver(Scanner scanner) {
            int driverId = readMenuChoice(scanner, "Enter the ID of the driver to update: ");
            Driver updatedDriver = promptDriver(scanner, "Update Driver");
            boolean updated = informationManager.updateDriverById(driverId, updatedDriver);
            if (updated) {
                System.out.println("Driver updated successfully.");
                return true;
            }
            System.out.println("Failed to update driver. Driver with ID " + driverId + " not found. Please try again.");
            return false;
        }

        private boolean deleteDriver(Scanner scanner) {
            int driverId = readMenuChoice(scanner, "Enter the ID to delete the Driver: ");
            boolean removed = informationManager.removeDriver(driverId);
            if (removed) {
                System.out.println("Driver deleted successfully.");
                return true;
        }

        System.out.println("Failed to delete driver. Driver with ID " + driverId + " not found. Please try again.");
        return false;
        }

        /*-----------------------------RACES----------------------------- */

        private boolean addRace(Scanner scanner) {
            Race race = promptRace(scanner, "Add Race");
            boolean added = informationManager.addRace(race);
            if (added) {
                System.out.println("Race added successfully.");
                return true;
            }
            
            System.out.println("Failed to add race. Duplicate ID found. Please try again.");
            return false;

        }

        private boolean updateRace(Scanner scanner) {
            int raceId = readMenuChoice(scanner, "Enter the ID of the race to update: ");
            Race updatedRace = promptRace(scanner, "Update  Race");
            boolean updated = informationManager.updateRaceById(raceId, updatedRace);
            if (updated) {
                System.out.println("Race updated successfully.");
                return true;
            }
            System.out.println("Failed to update race. Race with ID " + raceId + " not found. Please try again.");
            return false;
        }   

        private boolean deleteRace(Scanner scanner) {
            int raceId = readMenuChoice(scanner, "Enter the ID to delete the race: ");
            boolean removed = informationManager.removeRace(raceId);
            if (removed) {
                System.out.println("Race deleted successfully.");
                return true;
            }
            System.out.println("Failed to delete race. Race with ID " + raceId + " not found. Please try again.");
            return false;
        }

        private boolean loadFromFile(Scanner scanner) {
            String fileName = readNonEmptyLine(scanner, "Enter the file name: ");
            boolean loaded = informationManager.loadFromFile(fileName, informationManager);
            System.out.println(loaded ? "Finished processing file." : "File loading failed. Please try again.");
            return loaded;
        }

        private boolean saveToFile(Scanner scanner) {
            String fileName = readNonEmptyLine(scanner, "Enter the file name to save: ");
            boolean saved = informationManager.saveToFile(fileName, informationManager);
            System.out.println(saved ? "Data saved successfully to file." : "Failed to save data. Please try again.");
            return saved;
        }

        //continue here

    }



/*int choice1;
                    do {
                        
                        System.out.println("\n--- Drivers ---\n");
                        System.out.println("1. View Drivers");
                        System.out.println("2. Add Driver");
                        System.out.println("3. Update Driver");
                        System.out.println("4. Delete Driver");
                        System.out.println("5. Load Driver from file");
                        System.out.println("6. Return to Main Menu");
                        System.out.print("Select an option: ");

                        choice1 = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice1) {
                            case 1:
                                //Displays Drivers
                                System.out.println("\n--- Drivers ---\n");
                                ArrayList<Driver> allDrivers = informationManager.getAllDrivers();
                                if (allDrivers.isEmpty()) {
                                    System.out.println("No Drivers in the System");
                                } else {
                                    System.out.println("Drivers in the System");
                                    for (Driver driver : allDrivers) {
                                        System.out.println(driver);
                                    }
                                }
                                break;
                            case 2:
                                //Adds a Driver
                                System.out.println("\n--- Add a Driver ---\n");
                                informationManager.addDriver();
                                break;
                            case 3:
                                //Update a Driver
                                System.out.println("\n--- Update a Driver ---\n");
                                informationManager.updateDriver();
                                break;
                            case 4:
                                //Delete a Driver
                                System.out.println("\n--- Delete a Driver ---\n");
                                informationManager.removeDriver();
                                break;
                            case 5:
                                //Load Driver from File
                                System.out.println("\n--- Load Driver from file ---\n");
                                informationManager.loadDrivers();
                                break;
                            case 6:
                                //Returning to Main Menu
                                System.out.println("\n--- Return to Main Menu ---\n");
                                break;
                            default:
                                //Input validation
                                System.out.println("\n--- Invalid option ---\n");
                        }
                    } while (choice1 != 6);
                    break; */

/*
                    do {
                        //Menu Options for Races
                        System.out.println("\n--- Races ---\n");
                        System.out.println("1. View Races");
                        System.out.println("2. Add Race");
                        System.out.println("3. Update Race");
                        System.out.println("4. Delete Race");
                        System.out.println("5. Load Race from file");
                        System.out.println("6. Return to Main Menu");
                        System.out.print("Select an option: ");

                        choice2 = scanner.nextInt();
                        scanner.nextLine();
                        switch (choice2) {
                            case 1:
                                System.out.println("\n--- Races ---\n");
                                informationManager.getAllRaces();
                                break;
                            case 2:
                                System.out.println("\n--- Add a Race ---\n");
                                informationManager.addRace();
                                break;
                            case 3:
                                System.out.println("\n--- Update a Race ---\n");
                                informationManager.updateRace();
                                break;
                            case 4:
                                System.out.println("\n--- Delete a Race ---\n");
                                informationManager.deleteRace();
                                break;
                            case 5:
                                System.out.println("\n--- Load Race from file ---\n");
                                informationManager.loadRaces();
                                break;
                            case 6:
                                System.out.println("\n--- Return to Main Menu ---\n");
                                break;
                            default:
                                System.out.println("\n--- Invalid option ---\n");
                        }
                    } while (choice2 != 6);
                    break; */