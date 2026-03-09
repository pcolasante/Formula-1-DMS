import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;



public class ChampionshipCalculator {

    public void calculateStandings() {

        Map<String, Integer> standings = new HashMap<>();

        for (RaceResult r : results) {
            standings.put(
                    r.getDriverName(),
                    standings.getOrDefault(r.getDriverName(), 0) + r.getPoints()
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
