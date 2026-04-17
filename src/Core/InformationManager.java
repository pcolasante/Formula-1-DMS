package Core;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Objects;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        if (drivers.isEmpty()) {
            System.out.println("\n--- No Drivers on Record ---\n");
        }
        for (Driver driver : drivers) {
            System.out.println(driver);
        }
        return drivers;
    }

    //Get Driver Data
    public ArrayList<Driver> getDriversData() {
        return new ArrayList<>(drivers);
    }

    //Get Driver by ID
    public Driver getDriverById(int driverId) {
        for (Driver driver : drivers) {
            if (driver.getDriverId() == driverId) {
                return driver;
            }
        }
        return null;
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

    //Updates driver in database by ID
    public boolean updateDriverById(int driverId, Driver updatedDriver) {
    for (int i = 0; i < drivers.size(); i++) {
        if (drivers.get(i).getDriverId() == driverId) {
            drivers.set(i, updatedDriver);
            return true;
        }
    }
    return false;
}

    //Removes driver from database
    public boolean removeDriver(int driverId) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverId() == driverId) {
                drivers.remove(i);
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
        if (races.isEmpty()) {
            System.out.println("\n--- No Races on Record ---\n");
        }
        for (Race race : races) {
            System.out.println(race);
        }
        return races;
    }

    //Get Race Data
    public ArrayList<Race> getRacesData() {
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

    //Updates Race in database by ID
    public boolean updateRaceById(int raceId, Race updatedRace) {
    for (int i = 0; i < races.size(); i++) {
        if (races.get(i).getRaceId() == raceId) {
            races.set(i, updatedRace);
            return true;
        }
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
