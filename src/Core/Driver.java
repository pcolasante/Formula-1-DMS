package Core;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;


/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class: Driver: All information from the driver is held here.
 It will obtain and return the driver's name, nationality, team, car number, total points won so far, race wins, races entered, podiums, and if active or not.

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


    // Constructor for Driver, initializes all fields with the provided values, ensuring that each driver has a unique ID and complete information
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

    // Method to get the driver's ID, returns the unique identifier for the driver
    public int getDriverId() {
        return driverId;
    }

    // Method to get the driver's name, returns the name of the driver
    public String getDriverName() {
        return driverName;
    }

    // Method to get the driver's nationality, returns the nationality
    public String getNationality() {
        return nationality;
    }

    // Method to get the team of the driver, returns the team name
    public String getTeam() {
        return team;
    }

    // Method to get the car number of the driver, returns the car number
    public int getCarNumber() {
        return carNumber;
    }

    // Method to get the total points the driver has accumulated, returns the total points
    public int getTotalPoints() {
        return totalPoints;
    }

    // Method to get the number of races the driver has won, returns the count of race wins
    public int getRaceWins() {
        return raceWins;
    }

    // Method to get the number of races the driver has entered, returns the count of races entered
    public int getRaceEntered() {
        return raceEntered;
    }

    // Method to get the number of podiums the driver has achieved, returns the count of podium finishes
    public int getPodiums() {
        return podiums;
    }

    // Method to check if the driver is currently active, returns true if active, otherwise false
    public boolean isActiveStatus() {
        return activeStatus;
    }

    // Method to format driver information for file storage, ensuring consistent and readable output
    public String toFileString() {
        // add String formatting ""
      return driverId + " | " + driverName + " | " + carNumber + " | " + nationality + " | " + team + " | Races Entered: " + raceEntered + " | Podiums: " + podiums + " | Race Wins: " + raceWins + " | Total Points: " + totalPoints + " | Active Status: " + activeStatus;
    }

    // Override toString method for better readability when printing driver information
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
