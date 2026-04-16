package Core;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class ChampionshipCalculator: This class will process the calculation of the championship standings based on the driver's gained points. It will take a list of race results and calculate the total points for each driver, returning a map of driver names to their total points. It will also display the championship standings in descending order of points. 

 */


public class ChampionshipCalculator {

    // Method to calculate championship standings
    public Map<String, Integer> calculateStandings(List<Race> results) {

        Map<String, Integer> standings = new HashMap<>();

        if (results == null || results.isEmpty()) {
            System.out.println("No results on record.");
            return standings;
        }

        for (Race race : results) {
            Driver driver = race.getDriver();
            String driverName = (driver != null) ? driver.getDriverName() : "Unknown";
            standings.put(driverName, standings.getOrDefault(driverName, 0) + race.getResult());
        }

        System.out.println("\nChampionship Standings:");

        standings.entrySet()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue() + " pts"));
        return standings;
    }
}
