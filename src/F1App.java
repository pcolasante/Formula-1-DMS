/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026


 Program Objective: Build a Formula 1 Management System to maintain and keep record of drivers and race results. It will also calculate championship standings based on the driver's gained points.
 This includes adding, removing, and displaying a list of current drivers and races. It should also be able to exit the program.
 The user will be asked to input driver or race details to either add/remove.
 The program will return the driver list, and a confirmation.

 Class: F1App (Main class): Will launch the main console-based application.

 */

import java.util.Scanner;

public class F1App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        InformationManager manager = new InformationManager();

        int selectChoice;

        do {
            //Sets the main menu
            System.out.println("\n--- FORMULA 1 - DATA MANAGEMENT SYSTEM ---\n");
            System.out.println("1. Drivers");
            System.out.println("2. Races");
            System.out.println("3. Championship Standings");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            selectChoice = scanner.nextInt();
            scanner.nextLine(); // clear buffer
            switch (selectChoice) {
                case 1:
                    int choice1;
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
                                manager.getAllDrivers();
                                break;
                            case 2:
                                //Adds a Driver
                                System.out.println("\n--- Add a Driver ---\n");
                                manager.addDriver();
                                break;
                            case 3:
                                //Update a Driver
                                System.out.println("\n--- Update a Driver ---\n");
                                manager.updateDriver();
                                break;
                            case 4:
                                //Delete a Driver
                                System.out.println("\n--- Delete a Driver ---\n");
                                manager.removeDriver();
                                break;
                            case 5:
                                System.out.println("\n--- Load Driver from file ---\n");
                                manager.loadDriver();
                                break;
                            case 6:
                                System.out.println("\n--- Return to Main Menu ---\n");
                                break;
                            default:
                                System.out.println("\n--- Invalid option ---\n");
                        }
                    } while (choice1 != 6);
                    break;
//---------------------------------------------------------------------------------------------------//
                case 2:
                    int choice2;
                    do {
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
                                manager.getAllRaces();
                                break;
                            case 2:
                                System.out.println("\n--- Add a Race ---\n");
                                manager.addRace();
                                break;
                            case 3:
                                System.out.println("\n--- Update a Race ---\n");
                                manager.updateRace();
                                break;
                            case 4:
                                System.out.println("\n--- Delete a Race ---\n");
                                manager.deleteRace();
                                break;
                            case 5:
                                System.out.println("\n--- Load Race from file ---\n");
                                manager.loadRace();
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
                case 3:
                    System.out.println("\n--- Championship Standings ---\n");
                    //what the fuck am i doing here
                    break;
                case 4:
                    System.out.println("EXITING FORMULA 1 DATA MANAGEMENT SYSTEM");
                    break;
                default:
                    System.out.println("INVALID SELECTION");
            }

        }
        while (selectChoice != 4);

        scanner.close();

    }
}