package Core;

import java.util.ArrayList;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class Core.InformationManager: This class will process any additions or removals to the Core.Driver and Core.Race lists.

 */
public class InformationManager {

    private ArrayList<Driver> drivers;
    private ArrayList<Race> races;

    // Constructor
    public InformationManager() {
        drivers = new ArrayList<>();
        races = new ArrayList<>();
    }


    public ArrayList<Driver> getAllDrivers() {
        return new ArrayList<>(drivers);
    }

    public boolean addDriver(Driver driver) {
        if(driverExists(driver.getDriverId())) {
            return false;
        }
        drivers.add(driver);
        return true;
    }

    public  updateDriver(int index, Driver updatedDriver) {
        if (index >= 0 && index < drivers.size()) {
            drivers.set(index, updatedDriver);
            return true;
        }
        return false;
    }

    public boolean removeDriver(int driverId) {
        for(Driver driver : drivers){
            if(driver.getDriverId() == driverId){
                drivers.remove(driver);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Driver> loadDrivers(ArrayList<Driver> loadedDrivers) {
        drivers = loadedDrivers;
        return drivers;
    }

    public boolean driverExists(int driverId){
        for(Driver driver : drivers){
            if(driver.getDriverId() == driverId){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Race> getAllRaces() {
        return new ArrayList<>(races);
    }

    public  boolean addRace(Race race) {
        if(raceExists(race.getRaceId())) {
            return false;
        }
        races.add(race);
        return true;
    }

    public  updateRace(int index, Race updatedRace) {
        if (index >= 0 && index < races.size()) {
            races.set(index, updatedRace);
            return true;
        }
        return false;
    }

    public  boolean deleteRace(int raceId) {
        for(Race race : races){
            if(race.getRaceId() == raceId){
                races.remove(race);
                return true;
            }
        }
        return false;
    }

    public boolean raceExists(int raceId) {
        for(Race race : races){
            if(race.getRaceId() == raceId){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Race> loadRaces(ArrayList<Race> loadedRaces) {
        races = loadedRaces;
        return races;
    }
}
