package Core;

/**
 * Author: Paulina Flores Colasante
 * Course: Software Development 1
 * Date: 3/8/2026
 * Class: Race: All information from the race is held here.
 * It will obtain and return the race name, location, country, date, attendance, total laps, and result.
 */
public class Race {

    private final int raceId;
    private final Driver driver;
    String raceName;
    String location;
    String country;
    String date;
    int position;
    int totalLaps;
    int result;


    /**
     * Constructor for Race, initializes all fields with the provided values, ensuring that each driver has a unique ID and complete information
     *
     * @param raceId
     * @param driver
     * @param raceName
     * @param location
     * @param country
     * @param date
     * @param totalLaps
     * @param position
     * @param result
     */
    public Race(int raceId, Driver driver, String raceName, String location, String country, String date, int totalLaps, int position, int result) {
        this.raceId = raceId;
        this.driver = driver;
        this.raceName = raceName;
        this.location = location;
        this.country = country;
        this.date = date;
        this.totalLaps = totalLaps;
        this.position = position;
        this.result = result;
    }

    /**
     * getRaceId: Method to get the race ID, returns the unique identifier for the race
     *
     * @return int
     */
    public int getRaceId() {

        return raceId;
    }

    /**
     * getDriver: Method to get the driver associated with the race, returns the driver object
     *
     * @return driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * getRaceName: Method to get the race name, returns the name of the race
     *
     * @return string
     */
    public String getRaceName() {
        return raceName;
    }

    /**
     * getLocation: Method to get the location of the race, returns the location
     *
     * @return string
     */
    public String getLocation() {
        return location;
    }

    /**
     * getCountry: Method to get the country where the race took place, returns the country
     *
     * @return String
     */
    public String getCountry() {
        return country;
    }

    /**
     * getData: Method to get the date of the race, returns the date
     *
     * @return String
     */
    public String getDate() {
        return date;
    }

    /**
     * getPosition: Method to get the position of the driver in the race, returns the position
     *
     * @return int
     */
    public int getPosition() {
        return position;
    }

    /**
     * getTotalLapsMethod to get the total laps in the race, returns the total laps
     *
     * @return int
     */
    public int getTotalLaps() {
        return totalLaps;
    }

    /**
     * getResult: Method to get the result of the race, returns the result (e.g., points earned based on position)
     *
     * @return int
     */
    public int getResult() {
        return result;
    }

    /**
     * toFileString: Method to format race information for file storage, ensuring consistent and readable output
     *
     * @return String
     */
    public String toFileString() {
        return raceId + " | " + raceName + " | " + location + " | " + country + " | Date: " + date + " | Total Laps: " + totalLaps + " | Position: " + position + " | Result: " + result + " | Driver: " + ((driver != null) ? driver.getDriverName() + " (#" + driver.getDriverId() + ")" : "Unknown Driver");
    }

    /**
     * Override toString method for better readability when printing race information
     *
     * @return raceId
     */
    @Override
    public String toString() {
        String driverLabel = (driver != null) ? driver.getDriverName() + " (#" + driver.getDriverId() + ")" : "Unknown Driver";
        return raceId +
                " |  " + raceName +
                " |  " + location +
                " |  " + country +
                " | Date: " + date +
                " | Total Laps: " + totalLaps +
                " | Position: " + position +
                " | Result: " + result +
                " | Driver: " + driverLabel;
    }

}
