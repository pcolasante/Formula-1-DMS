package Core;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
import java.io.File;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class FileManager: This class will process the storage of the Driver and Race Results information.

 */
public class FileManager {


    // Method to load data from a file, returns true if successful, otherwise false
    public boolean loadFromFile(String fileName, InformationManager informationManager) {

        try {
            File file = new File(fileName);

            System.out.println("Looking for file at: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("File doesn't exist");
                return false;
            }

            List<String[]> raceLines = new ArrayList<>();

            int loadedDrivers = 0;
            int loadedRaces = 0;

            try (Scanner scanner = new Scanner(file)) {
                int lineNumber = 0;
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    String line = scanner.nextLine();
                    line = line.trim();

                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] parts = line.split("\\|");
                    if (parts.length < 2) {
                        System.out.println("Incorrect format for line " + lineNumber + ".");
                        continue;
                    }

                    String recordType = parts[0].trim().toUpperCase();

                    if (recordType.equals("DRIVER")) {
                        Driver driver = parseDriver(parts, lineNumber);

                        if (driver != null && informationManager.addDriver(driver)) {
                            loadedDrivers++;
                        }

                    } else if (recordType.equals("RACE")) {
                        raceLines.add(parts);
                    } else {
                        throw new IllegalArgumentException("Unknown record type.");
                    }
                }
            }

            for (String[] raceParts : raceLines) {
                Race race = parseRace(raceParts, informationManager);

                if (race != null && informationManager.addRace(race)) {
                    loadedRaces++;
                }
            }

            System.out.println("Loaded " + loadedDrivers + " driver(s) and " + loadedRaces + " race(s) from file.");
            return true;
        } catch (Exception e) {
            System.out.println("Error loading file");
            return false;
        }
    }

    // Method to save data to a file, returns true if successful, otherwise false
    public boolean saveToFile(String fileName, InformationManager informationManager) {
        try (PrintWriter writer = new PrintWriter(fileName)) {

            ArrayList<Driver> drivers = informationManager.getDriversData();
            for (Driver driver : drivers) {
                writer.println("DRIVER|"
                        + driver.getDriverId() + "|"
                        + driver.getDriverName() + "|"
                        + driver.getCarNumber() + "|"
                        + driver.getNationality() + "|"
                        + driver.getTeam() + "|"
                        + driver.getRaceEntered() + "|"
                        + driver.getPodiums() + "|"
                        + driver.getRaceWins() + "|"
                        + driver.getTotalPoints() + "|"
                        + driver.isActiveStatus());
            }

            ArrayList<Race> races = informationManager.getRacesData();
            for (Race race : races) {
                Driver driver = race.getDriver();
                int driverId = (driver != null) ? driver.getDriverId() : -1;
                writer.println("RACE|"
                        + race.getRaceId() + "|"
                        + driverId + "|"
                        + race.getRaceName() + "|"
                        + race.getLocation() + "|"
                        + race.getCountry() + "|"
                        + race.getDate() + "|"
                        + race.getTotalLaps() + "|"
                        + race.getPosition() + "|"
                        + race.getResult());
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
            return false;
        }
    }

    //Method to parse the Driver information gathered from the file
    private Driver parseDriver(String[] parts, int lineNumber) {
        if (parts.length != 11) {
            System.out.println("Incorrect driver format.");
            return null;
        }

        Integer driverId = Integer.parseInt(parts[1]);
        Integer carNumber = Integer.parseInt(parts[3]);
        Integer raceEntered = Integer.parseInt(parts[6]);
        Integer podiums = Integer.parseInt(parts[7]);
        Integer raceWins = Integer.parseInt(parts[8]);
        Integer totalPoints = Integer.parseInt(parts[9]);
        Boolean activeStatus = Boolean.parseBoolean(parts[10]);

        if (driverId == null || carNumber == null || totalPoints == null || raceWins == null || raceEntered == null || podiums == null || activeStatus == null) {
            System.out.println("Incorrect format.");
            return null;
        }

        return new Driver(driverId, parts[2], carNumber, parts[4], parts[5], raceEntered, podiums, raceWins, totalPoints, activeStatus);

    }

    //Method to parse the Race information gathered from the file
    private Race parseRace(String[] parts, InformationManager manager) {
        if (parts.length != 10) {
            System.out.println("Incorrect race format.");
            return null;
        }

        Integer raceId = Integer.parseInt(parts[1]);
        Integer driverId = Integer.parseInt(parts[2]);
        Integer totalLaps = Integer.parseInt(parts[7]);
        Integer position = Integer.parseInt(parts[8]);
        Integer result = Integer.parseInt(parts[9]);

        if (raceId == null || driverId == null || totalLaps == null || position == null || result == null) {
            System.out.println("Incorrect race format.");
            return null;
        }

        Driver driver = (driverId >= 0) ? manager.getDriverById(driverId) : null;

        return new Race(raceId, driver, parts[3], parts[4], parts[5], parts[6], totalLaps, position, result);
    }


}
