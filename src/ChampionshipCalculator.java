

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;



public class ChampionshipCalculator {

    public void calculateStandings() {

        Map<String, Integer> standings = new HashMap<>();

        for (Race r : result) {
            standings.put(
                    String.valueOf(r.getDriver()),
                    standings.getOrDefault(r.getDriver(), 0) + r.getResult()
            );
        }

        System.out.println("\nChampionship Standings:");

        standings.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(entry ->
                        System.out.println(entry.getKey() + " - " + entry.getValue() + " pts")
                );
    }
}
