package Core;

/**
 * Author: Paulina Flores Colasante
 * Course: Software Development 1
 * Date: 3/8/2026
 * Driver.java: All information from the driver is held here.
 * It will obtain and return the driver's name, nationality, team, car number, total points won so far, race wins, races entered, podiums, and if active or not.
 */
public class Driver {

    int driverId;
    String driverName;
    String nationality;
    String team;
    int carNumber;
    int totalPoints;
    int raceWins;
    int raceEntered;
    int podiums;
    boolean activeStatus;


    /**
     * Driver: initializes all fields with the provided values, ensuring that each driver has a unique ID and complete information
     *
     * @param driverId
     * @param driverName
     * @param carNumber
     * @param nationality
     * @param team
     * @param raceEntered
     * @param podiums
     * @param raceWins
     * @param totalPoints
     * @param activeStatus
     */
    public Driver(int driverId, String driverName, int carNumber, String nationality, String team, int raceEntered, int podiums, int raceWins, int totalPoints, boolean activeStatus) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.nationality = nationality;
        this.team = team;
        this.carNumber = carNumber;
        this.totalPoints = totalPoints;
        this.raceWins = raceWins;
        this.raceEntered = raceEntered;
        this.podiums = podiums;
        this.activeStatus = activeStatus;
    }

    /**
     * getDriverId: Method to get the driver's ID, returns the unique identifier for the driver
     *
     * @return int driverId
     */
    public int getDriverId() {
        return driverId;
    }

    /**
     * getDriverName: Method to get the driver's name, returns the name of the driver
     *
     * @return String driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * getNationality: Method to get the driver's nationality, returns the nationality
     *
     * @return String
     *
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Method to get the team of the driver, returns the team name
     * getTeam:
     *
     * @return
     */
    public String getTeam() {
        return team;
    }


    /**
     * getCarNumber: Method to get the car number of the driver, returns the car number
     *
     * @return Int
     */
    public int getCarNumber() {
        return carNumber;
    }

    /**
     * getTotalPoints: Method to get the total points the driver has accumulated, returns the total points
     *
     * @return Int
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * getRaceWins: Method to get the number of races the driver has won, returns the count of race wins
     *
     * @return Int
     */
    public int getRaceWins() {
        return raceWins;
    }

    /**
     * getRaceEntered: Method to get the number of races the driver has entered, returns the count of races entered
     *
     * @return Int
     */
    public int getRaceEntered() {
        return raceEntered;
    }

    /**
     * getPodiums: Method to get the number of podiums the driver has achieved, returns the count of podium finishes
     *
     * @return int
     */
    public int getPodiums() {
        return podiums;
    }

    /**
     * isActiveStatus: Method to check if the driver is currently active, returns true if active, otherwise false
     *
     * @return boolean
     */
    public boolean isActiveStatus() {
        return activeStatus;
    }

    /**
     * toFileString: Method to format driver information for file storage, ensuring consistent and readable output
     *
     * @return String
     */
    public String toFileString() {
        // add String formatting ""
        return driverId + " | " + driverName + " | " + carNumber + " | " + nationality + " | " + team + " | Races Entered: " + raceEntered + " | Podiums: " + podiums + " | Race Wins: " + raceWins + " | Total Points: " + totalPoints + " | Active Status: " + activeStatus;
    }

    /**
     * toString: Override toString method for better readability when printing driver information, returns driverId and all its objects
     *
     * @return String
     */
    @Override
    public String toString() {
        return driverId +
                " | " + driverName +
                " | Car Number: " + carNumber +
                " | Nationality: " + nationality +
                " | Team: " + team +
                " | Races Entered: " + raceEntered +
                " | Podiums: " + podiums +
                " | Race Wins: " + raceWins +
                " | Total Points: " + totalPoints +
                " | Active Status: " + activeStatus;
    }

}
