package Test;

import Core.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class InformationManagerTest {

    private Driver driver;
    private Race race;

    private InformationManager manager;
    private Driver testDriver1;
    private Driver testDriver2;
    private Race testRace1;
    private Race testRace2;

    @BeforeEach
    void setUp() {

        manager = new InformationManager();
        testDriver1 = new Driver(1, "Lando Norris", 1, "UK", "McLaren", 24, 18, 8, 423, true);
        testDriver2 = new Driver(3, "Max Verstappen", 3, "NL", "Red Bull", 24, 16,8, 421, true);
        testRace1 = new Race(1, testDriver1, "LOUIS VUITTON AUSTRALIAN GRAND PRIX 2025", "Albert Park Grand Prix Circuit, Melbourne", "Australia", "16 MAR 2025", 58, 1, 25);
        testRace2 = new Race(3,testDriver2,"LENOVO JAPANESE GRAND PRIX 2025","Suzuka Circuit, Suzuka", "Japan", "06 Apr 2025", 25, 1, 32);

    }

    /*---------------------------DRIVER--------------------------*/

    //Method to test if driver is duplicate when adding a new Driver, should return true if driver is not duplicate.
    @Test
    void testDuplicateDriver() {

        manager.addDriver(testDriver1);
        assertTrue(manager.addDriver(testDriver2));
    }

    //Method to test if a driver can be successfully added, should be equal
    @Test
    void testAddDriver() {

        assertTrue(manager.addDriver(testDriver1));

    }

    //Method to test if a Driver can be successfully updated
    @Test
    void testUpdateDriverById() {

        manager.addDriver(testDriver1);

        Driver updated = new Driver(1, "Lando Norris", 1, "NL", "McLaren", 24, 15, 7, 415, true);
        assertTrue(manager.updateDriverById(1,updated));
        assertEquals(415, manager.getDriverById(1).getTotalPoints());

    }

    //Method to test if a Driver can be successfully removed, should remove Max Verstappen using Id number 3.
    @Test
    void testRemoveDriver() {
        manager.addDriver(testDriver1);
        manager.addDriver(testDriver2);

        assertTrue(manager.removeDriver(3));

    }

    /*------------------------RACES--------------------------*/

    //Method to test if a Race can be added
    @Test
    void testAddRace() {
        manager.addDriver(testDriver1);
        assertTrue(manager.addRace(testRace1));
    }

    @Test
    void testAddRaceWithNullDriver() {

        Race race = new Race(77, null, "HEINEKEN CHINESE GRAND PRIX 2025", "Monte Carlo, Monaco", "Monaco", "24 MAY 2025", 78, 1, 25);
        assertTrue(manager.addRace(race));
        assertEquals(1,manager.getRacesData().size());
        assertNull(manager.getRacesData().get(0).getDriver());
    }

    //Method to test if a Race can be updated by Id
    @Test
    void testUpdateRaceById() {
        manager.addRace(testRace1);

        Race updated = new Race(1, testDriver1,"LENOVO JAPANESE GRAND PRIX 2025","Suzuka Circuit, Suzuka", "Japan", "06 Apr 2025", 25, 1, 32);
        assertTrue(manager.updateRaceById(1,updated));

        Race loaded = manager.getRacesData().get(0);
        assertEquals(25,loaded.getTotalLaps());
        assertEquals(32, loaded.getResult());
    }

    //Method to test if a Race can be deleted
    @Test
    void testDeleteRace() {
        manager.addRace(testRace1);
        manager.addRace(testRace2);

        assertTrue(manager.deleteRace(1));

    }
}