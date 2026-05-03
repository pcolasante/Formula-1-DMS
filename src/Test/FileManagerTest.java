package Test;

import Core.Driver;
import Core.FileManager;
import Core.InformationManager;
import Core.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Paulina Flores Colasante
 * Course: Software Development 1
 * Date: 4/8/2026
 * Class FileManagerTest: This class will test the load/save feature of the Driver and Race information from legacy text files or the SQLite database file provided by the user.
 */

class FileManagerTest {
    private FileManager fileManager;

    /**
     * Sets up the File Manager for the tests
     */
    @BeforeEach
    void setUp() {
        fileManager = new FileManager();
    }

    /**
     * testLoadFromFile: Will test the LoadFromFile method, should pass if program is able to open and load a file.
     *
     * @throws Exception
     */
    @Test
    void testLoadFromFile() throws Exception {

        InformationManager source = new InformationManager();

        source.addDriver(new Driver(1, "Lando Norris", 1, "UK", "McLaren", 24, 18, 8, 423, true));

        Path tempFile = Files.createTempFile("test", ".txt");

        try {
            assertTrue(fileManager.saveToFile(tempFile.toString(), source));

            InformationManager target = new InformationManager();

            assertTrue(fileManager.loadFromFile(tempFile.toString(), target));

            Driver loaded = target.getDriverById(1);
            assertNotNull(loaded);
            assertEquals("Lando Norris", loaded.getDriverName());
            assertEquals("McLaren", loaded.getTeam());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * testSaveToFile: Test to save a file to a text file, should pass if information is saved to file.
     *
     * @throws Exception
     */
    @Test
    void testSaveToFile() throws Exception {

        InformationManager source = new InformationManager();
        Driver driver = new Driver(1, "Lando Norris", 1, "UK", "McLaren", 24, 18, 8, 423, true);
        source.addDriver(driver);
        source.addRace(new Race(1, driver, "LOUIS VUITTON AUSTRALIAN GRAND PRIX 2025", "Albert Park Grand Prix Circuit, Melbourne", "Australia", "16 MAR 2025", 58, 1, 25));

        Path tempFile = Files.createTempFile("test", ".txt");

        try {
            assertTrue(fileManager.saveToFile(tempFile.toString(), source));

            List<String> lines = Files.readAllLines(tempFile);
            assertEquals(2, lines.size());
            assertTrue(lines.get(0).startsWith("DRIVER|"));
            assertTrue(lines.get(1).startsWith("RACE|"));
            assertTrue(lines.get(1).contains("|1|"));
        } finally {
            Files.deleteIfExists(tempFile);
        }


    }
}