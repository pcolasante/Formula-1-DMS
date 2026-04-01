package Core;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;



public class ChampionshipCalculator {

    public Map<String, Integer> calculateStandings(List<Race> results) {

        Map<String, Integer> standings = new HashMap<>();

        if (results == null || results.isEmpty()) {
            System.out.println("No results on record.");
            return standings;
        }

        for (Race r : results) {
            Driver driver = r.getDriver();
            String driverName = (driver != null) ? driver.getDriverName() : "Unknown";
            standings.put(driverName, standings.getOrDefault(driverName, 0) + r.getResult());
        }

        System.out.println("\nChampionship Standings:");

        standings.entrySet()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue() + " pts"));
        return standings;
    }
}
