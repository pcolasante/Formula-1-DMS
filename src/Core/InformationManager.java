package Core;

import java.util.ArrayList;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class InformationManager: This class will process any additions or removals to the Driver and Race lists.

 */
public class InformationManager {

    private ArrayList<Driver> drivers;
    private ArrayList<Race> races;

    // Constructor
    public InformationManager() {
        drivers = new ArrayList<>();
        races = new ArrayList<>();
    }

/* ------------------------------------------------------------- DRIVERS ----------------------------------------------------------------- */
    
    //Gets all drivers from database, if any
    public ArrayList<Driver> getAllDrivers() {
        return new ArrayList<>(drivers);
    }

    //Adds Driver to database
    public boolean addDriver(Driver driver) {
        if(driverExists(driver.getDriverId())) {
            return false;
        }
        drivers.add(driver);
        return true;
    }

    //Updates driver in database
    public boolean  updateDriver(int index, Driver updatedDriver) {
        if (index >= 0 && index < drivers.size()) {
            drivers.set(index, updatedDriver);
            return true;
        }
        return false;
    }

    //Removes driver from database
    public boolean removeDriver(int driverId) {
        for(Driver driver : drivers){
            if(driver.getDriverId() == driverId){
                drivers.remove(driver);
                return true;
            }
        }
        return false;
    }

    //Checks if driver exists in database
    public boolean driverExists(int driverId){
        for(Driver driver : drivers){
            if(driver.getDriverId() == driverId){
                return true;
            }
        }
        return false;
    }

    //Loads Drivers from text file
    public ArrayList<Driver> loadDrivers(ArrayList<Driver> loadedDrivers) {
        drivers = loadedDrivers;
        return drivers;
    }



/* ------------------------------------------------------------------ RACES ----------------------------------------------------------------------- */

    //Gets all races from database, if any
    public ArrayList<Race> getAllRaces() {
        return new ArrayList<>(races);
    }

    //Adds race to database
    public  boolean addRace(Race race) {
        if(raceExists(race.getRaceId())) {
            return false;
        }
        races.add(race);
        return true;
    }

    //Updates race in database
    public  boolean updateRace(int index, Race updatedRace) {
        if (index >= 0 && index < races.size()) {
            races.set(index, updatedRace);
            return true;
        }
        return false;
    }

    //deletes race from database
    public  boolean deleteRace(int raceId) {
        for(Race race : races){
            if(race.getRaceId() == raceId){
                races.remove(race);
                return true;
            }
        }
        return false;
    }

    //Checks if race exists in database
    public boolean raceExists(int raceId) {
        for(Race race : races){
            if(race.getRaceId() == raceId){
                return true;
            }
        }
        return false;
    }

    //Loads the Races from text file
    public ArrayList<Race> loadRaces(ArrayList<Race> loadedRaces) {
        races = loadedRaces;
        return races;
    }
}
