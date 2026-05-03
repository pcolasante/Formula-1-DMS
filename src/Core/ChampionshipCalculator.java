package Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Paulina Flores Colasante
 * CEN 3024 - Software Development 1
 * Date: 3/8/2026
 * ChampionshipCalculator.java: This class will process the calculation of the championship standings based on the driver's gained points. It will take a list of race results and calculate the total points for each driver, returning a map of driver names to their total points. It will also display the championship standings in descending order of points.
 */


public class ChampionshipCalculator {

    /**
     * Method: CalculateStandings
     * Returns standings
     * Purpose: Method to calculate championship standings
     */
    public Map<String, Integer> calculateStandings(List<Race> results) {

        Map<String, Integer> standings = new HashMap<>();

        if (results == null || results.isEmpty()) {
            System.out.println("No results on record.");
            return standings;
        }

        int skippedUnlinkedRaces = 0;


        for (Race race : results) {
            Driver driver = race.getDriver();

            if (driver == null) {
                skippedUnlinkedRaces++;
                continue;
            }

            String driverName = driver.getDriverName();
            standings.put(driverName, standings.getOrDefault(driverName, 0) + race.getResult());
        }

        if (standings.isEmpty()) {
            System.out.println(" \n No Results on record. \n");
            return standings;
        }

        System.out.println("\n--- Championship Standings ---\n");

        standings.entrySet()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue() + " pts"));

        if (skippedUnlinkedRaces > 0) {
            System.out.println("Skipped " + skippedUnlinkedRaces + " unlinked races.");
        }
        return standings;
    }
}
