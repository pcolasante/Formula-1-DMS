package Core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//
class DriverTest {

    Driver driver;

    //Supplying test data for Driver Object
    @BeforeEach
    void setUp() {
        driver = new Driver();
        //Driver(int driverId, String driverName, int carNumber, String nationality, String team, int raceEntered, int podiums, int raceWins, int totalPoints, boolean activeStatus)
    }


    @Test
    void toFileString() {
    }

    @Test
    void testToString() {
    }
}