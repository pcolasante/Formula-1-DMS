import java.util.Map;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class: Race: All information from the race is held here.
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

    public Race(int raceId,Driver driver, String raceName, String location, String country, String date, int totalLaps, int position, int result) {
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
    public int getRaceId() {

        return raceId;
    }
    public Driver getDriver() {
        return driver;
    }
    public String getRaceName() {
        return raceName;
    }
    public String getLocation() {
        return location;
    }
    public String getCountry() {
        return country;
    }
    public String getDate() {
        return date;
    }
    public int getPosition() {
        return position;
    }
    public int getTotalLaps() {
        return totalLaps;
    }
    public int getResult() {
        return result;
    }

    public String toFileString() {
        return raceId + "-" + raceName + "," + location + "," + country + "-" + date + "- Laps:" + totalLaps + "-- Driver:" + driver + "- Position:" + position + "- Result:" + result;
    }
}
