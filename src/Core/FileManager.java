package Core;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.io.File;
import java.util.Scanner;

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

            try (Scanner scanner = new Scanner(file)) {
                int lineNumber = 0;
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    String line = scanner.nextLine();
                    line = line.trim();

                    if (line.isEmpty()) {
                        continue;
                    }

                    try {
                        String[] parts = line.split("\\|");
                        if (parts.length < 2) {
                            throw new IllegalArgumentException("Incorrect format.");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("Incorrect format at line " + lineNumber + ": " + e.getMessage());

                    }

                }
            }
            
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
                        + driver.getNationality() + "|"
                        + driver.getTeam() + "|"
                        + driver.getCarNumber() + "|"
                        + driver.getTotalPoints() + "|"
                        + driver.getRaceWins() + "|"
                        + driver.getRaceEntered() + "|"
                        + driver.getPodiums() + "|"
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
}
