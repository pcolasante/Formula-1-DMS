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

    public Driver(int driverId, String driverName, String nationality, String team, int carNumber, int totalPoints, int RaceWins, int RaceEntered, int podiums, boolean activeStatus) {
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
    public int getDriverId() {
        return driverId;
    }
    public String getDriverName() {
        return driverName;
    }
    public String getNationality() {
        return nationality;
    }
    public String getTeam() {
        return team;
    }
    public int getCarNumber() {
        return carNumber;
    }
    public int getTotalPoints() {
        return totalPoints;
    }
    public int getRaceWins() {
        return raceWins;
    }
    public int getRaceEntered() {
        return raceEntered;
    }
    public int getPodiums() {
        return podiums;
    }
    public boolean isActiveStatus() {
        return activeStatus;
    }

    public String toFileString() {
        // add String formatting ""
      return driverId + driverName + nationality + carNumber + team + raceEntered + podiums + raceWins + totalPoints + activeStatus;
    }

}
