import Core.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuSystem {

    private final InformationManager informationManager;
    private final FileManager fileManager;
    private final DataValidation validator;
    private final ChampionshipCalculator calculator;

    public MenuSystem(InformationManager informationManager, FileManager fileManager,
                      DataValidation validator, ChampionshipCalculator calculator) {

        this.informationManager = informationManager;
        this.fileManager = fileManager;
        this.validator = validator;
        this.calculator = calculator;

    }

    public boolean start() {

        Scanner scanner = new Scanner(System.in);

        int selectChoice;

        do {
            //Sets the main menu
            System.out.println("\n--- FORMULA 1 - DATA MANAGEMENT SYSTEM ---\n");
            System.out.println("1. Drivers");
            System.out.println("2. Races");
            System.out.println("3. Championship Standings");
            System.out.println("4. Save Data to File");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            selectChoice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (selectChoice) {
                case 1: // Drivers
                    int choice1;
                    do {
                        /*--------------- Menu Options for Drivers ------------------*/
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
                    break;
//---------------------------------------------------------------------------------------------------//
                case 2: //Races
                    int choice2;
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
                    break;

//--------------------------------------------------------------------------------------------------//
                case 3: //Championships
                    //Menu Options for Championship Standings
                    System.out.println("\n--- Championship Standings ---\n");
                    break;

//--------------------------------------------------------------------------------------------------//
                case 4: //Save Data to File
                    System.out.println("\n--- Save Data to File ---\n");


                    //-------------------------------------------------------------------------------//

                case 5: //Exit
                    System.out.println("\nEnter file path to save (or press Enter to skip):\n");
                    String savePath = scanner.nextLine().trim();

                    if (!savePath.isEmpty()) {
                        boolean saveResult = fileManager.saveToFile(savePath, informationManager);
                        if (saveResult.isPresent()) {
                            System.out.println("Save successful");

                        } else {
                            System.out.println("Save failed");
                        }
                    }
                    System.out.println("EXITING FORMULA 1 DATA MANAGEMENT SYSTEM");
                    break;

                    //Invalid input
                default:
                    System.out.println("INVALID SELECTION");
            }

        }
        while (selectChoice != 4);

        return true;


        }
    }

}

