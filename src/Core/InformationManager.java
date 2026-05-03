package Core;

import java.util.ArrayList;

/**
 * Author: Paulina Flores Colasante
 * Course: Software Development 1
 * Date: 3/8/2026
 * Class InformationManager: This class will process any additions or removals to the Driver and Race lists.
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


    /**
     * getAllDrivers: Method. Gets all drivers from database, if any, returns drivers
     *
     * @return drivers
     */
    public ArrayList<Driver> getAllDrivers() {
        System.out.println("\n--- Drivers on Record ---\n");
        if (drivers.isEmpty()) {
            System.out.println("No Drivers on Record.");
        }
        for (Driver driver : drivers) {
            System.out.println(driver);
        }
        return drivers;
    }


    /**
     * getDriversData: Method. Get Driver Data, returns array list of drivers
     *
     * @return ArrayList
     */
    public ArrayList<Driver> getDriversData() {
        return new ArrayList<>(drivers);
    }


    /**
     * getDriverById: Gets driver data by Id, returns driver or null
     *
     * @param driverId
     * @return driver
     */
    public Driver getDriverById(int driverId) {
        for (Driver driver : drivers) {
            if (driver.getDriverId() == driverId) {
                return driver;
            }
        }
        return null;
    }


    /**
     * addDriver: Method. Adds Driver to database, returns boolean
     *
     * @param driver
     * @return boolean
     */
    public boolean addDriver(Driver driver) {
        if (driverExists(driver.getDriverId())) {
            return false;
        }
        drivers.add(driver);
        return true;
    }


    /**
     * updateDriver: Method. Updates driver in database, returns boolean
     *
     * @param index
     * @param updatedDriver
     * @return boolean
     */
    public boolean updateDriver(int index, Driver updatedDriver) {
        if (index >= 0 && index < drivers.size()) {
            drivers.set(index, updatedDriver);
            return true;
        }
        return false;
    }


    /**
     * updateDriverById: Method. Updates driver in database by ID, returns boolean
     *
     * @param driverId
     * @param updatedDriver
     * @return boolean
     */
    public boolean updateDriverById(int driverId, Driver updatedDriver) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverId() == driverId) {
                drivers.set(i, updatedDriver);
                return true;
            }
        }
        return false;
    }


    /**
     * removeDriver: Method. Removes driver from database, returns boolean
     *
     * @param driverId
     * @return boolean
     */
    public boolean removeDriver(int driverId) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverId() == driverId) {
                drivers.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * driverExists: Method. Checks if driver exists in database, returns boolean
     *
     * @param driverId
     * @return boolean
     */
    public boolean driverExists(int driverId) {
        for (Driver driver : drivers) {
            if (driver.getDriverId() == driverId) {
                return true;
            }
        }
        return false;
    }

    /**
     * loadDrivers: Method. Loads Drivers from text file, returns drivers
     *
     * @param loadedDrivers
     * @return drivers
     */
    public ArrayList<Driver> loadDrivers(ArrayList<Driver> loadedDrivers) {
        drivers = loadedDrivers;
        return drivers;
    }


    /* ------------------------------------------------------------------ RACES ----------------------------------------------------------------------- */

    /**
     * getAllRaces: Method. Gets all races from database, if any. Returns races
     *
     * @return ArrayList
     */
    public ArrayList<Race> getAllRaces() {
        System.out.println("\n--- Races on Record ---\n");
        if (races.isEmpty()) {
            System.out.println("No Races on Record.");
        }
        for (Race race : races) {
            System.out.println(race);
        }
        return races;
    }

    /**
     * getRacesData: Method. Get Race Data, returns ArrayList of races
     *
     * @return ArrayList
     */
    public ArrayList<Race> getRacesData() {
        return new ArrayList<>(races);
    }

    /**
     * addRace: Method. Adds race to database, returns boolean
     *
     * @param race
     * @return boolean
     */
    public boolean addRace(Race race) {
        if (raceExists(race.getRaceId())) {
            return false;
        }
        races.add(race);
        return true;
    }

    /**
     * updateRace: Method. Updates race in database, returns boolean.
     *
     * @param index
     * @param updatedRace
     * @return boolean
     */
    public boolean updateRace(int index, Race updatedRace) {
        if (index >= 0 && index < races.size()) {
            races.set(index, updatedRace);
            return true;
        }
        return false;
    }

    /**
     * updateRaceById: Method. Updates Race in database by ID, returns boolean
     *
     * @param raceId
     * @param updatedRace
     * @return boolean
     */
    public boolean updateRaceById(int raceId, Race updatedRace) {
        for (int i = 0; i < races.size(); i++) {
            if (races.get(i).getRaceId() == raceId) {
                races.set(i, updatedRace);
                return true;
            }
        }
        return false;
    }

    /**
     * deleteRace: Method that deletes race from database, returns boolean
     *
     * @param raceId
     * @return boolean
     */
    public boolean deleteRace(int raceId) {
        for (Race race : races) {
            if (race.getRaceId() == raceId) {
                races.remove(race);
                return true;
            }
        }
        return false;
    }

    /**
     * raceExists: Method Checks if race exists in database, returns boolean
     *
     * @param raceId
     * @return boolean
     */
    public boolean raceExists(int raceId) {
        for (Race race : races) {
            if (race.getRaceId() == raceId) {
                return true;
            }
        }
        return false;
    }

    /**
     * loadRaces: Loads the Races from text file, returns ArrayList
     *
     * @param loadedRaces
     * @return ArrayList
     */
    public ArrayList<Race> loadRaces(ArrayList<Race> loadedRaces) {
        races = loadedRaces;
        return races;
    }
}
