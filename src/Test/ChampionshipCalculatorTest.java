package Test;

import Core.*;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ChampionshipCalculatorTest {

    private ChampionshipCalculator championshipCalculator;
    private Driver testDriver1;
    private Driver testDriver2;
    private Race testRace1;
    private Race testRace2;
    private Race testRace3;

    //Setting up for the tests
    @BeforeEach
    void setUp() {

        testDriver1 = new Driver(1, "Lando Norris", 1, "UK", "McLaren", 24, 18, 8, 423, true);
        testDriver2 = new Driver(3, "Max Verstappen", 3, "NL", "Red Bull", 24, 16,8, 421, true);
        testRace1 = new Race(1, testDriver1, "LOUIS VUITTON AUSTRALIAN GRAND PRIX 2025", "Albert Park Grand Prix Circuit, Melbourne", "Australia", "16 MAR 2025", 58, 1, 25);
        testRace2 = new Race(3,testDriver2,"LENOVO JAPANESE GRAND PRIX 2025","Suzuka Circuit, Suzuka", "Japan", "06 Apr 2025", 25, 1, 32);
        testRace3 = new Race(5, null, "HEINEKEN CHINESE GRAND PRIX 2025", "Shanghai International Circuit, Shanghai", "China", "23 MAR 2025", 56,1, 25);

    }

    //Test to calculate standings, should pass if method is able to calculate the total of totalPoints
    @Test
    void testCalculateStandings() {

        ChampionshipCalculator calculator = new ChampionshipCalculator();

        Map<String, Integer> standings = calculator.calculateStandings(Arrays.asList(testRace1,testRace2,testRace3));

        assertEquals(2,standings.size());
        assertEquals(32, standings.get("Max Verstappen"));
        assertEquals(25, standings.get("Lando Norris"));
        assertFalse(standings.containsKey("No linked driver"));

    }
}