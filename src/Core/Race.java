package Core;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class: Core.Race: All information from the race is held here.
 It will obtain and return the race name, location, country, date, attendance, total laps, and result.

 */
public class Race {

    private int raceId;
    private Driver driver;
    String raceName;
    String location;
    String country;
    String date;
    int position;
    int totalLaps;
    int result;

    //Map<String, Integer> results;

    // Constructor for Race, initializes all fields with the provided values, ensuring that each driver has a unique ID and complete information
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

    // Method to get the race ID, returns the unique identifier for the race
    public int getRaceId() {

        return raceId;
    }

    // Method to get the driver associated with the race, returns the driver object
    public Driver getDriver() {
        return driver;
    }

    // Method to get the race name, returns the name of the race
    public String getRaceName() {
        return raceName;
    }

    // Method to get the location of the race, returns the location
    public String getLocation() {
        return location;
    }

    // Method to get the country where the race took place, returns the country
    public String getCountry() {
        return country;
    }

    // Method to get the date of the race, returns the date
    public String getDate() {
        return date;
    }

    // Method to get the position of the driver in the race, returns the position
    public int getPosition() {
        return position;
    }

    // Method to get the total laps in the race, returns the total laps
    public int getTotalLaps() {
        return totalLaps;
    }

    // Method to get the result of the race, returns the result (e.g., points earned based on position)
    public int getResult() {
        return result;
    }

    // Method to format race information for file storage, ensuring consistent and readable output
    public String toFileString() {
        return raceId + " | " + raceName + " | " + location + " | " + country + " | Date: " + date + " | Total Laps: " + totalLaps + " | Position: " + position + " | Result: " + result + " | Driver: " + ((driver != null) ? driver.getDriverName() + " (#" + driver.getDriverId() + ")" : "Unknown Driver");
    }

    // Override toString method for better readability when printing race information
       @Override
    public String toString() {
        String driverLabel = (driver != null) ? driver.getDriverName() + " (#" + driver.getDriverId() + ")" : "Unknown Driver";
        return raceId +
                " |  " + raceName + '\'' +
                " |  " + location + '\'' +
                " |  " + country + '\'' +
                " | Date: " + date + '\'' +
                " | Total Laps: " + totalLaps +
                " | Position: " + position +
                " | Result: " + result +
                " | Driver: " + driverLabel;
    }

}
