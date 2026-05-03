package Core;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 3/8/2026

 Class FileManager: This class will load/save Driver and Race information from legacy text files or the SQLite database file provided by the user.

 */
public class FileManager {

    private String lastError = "";

    public String getLastError() {
        return lastError;
    }

    //Provides error clarification, especially relating database connection
    private void setLastError(String message) {
        lastError = message == null ? "Unknown error" : message;
    }

    // Method to load data from a file, returns true if successful, otherwise false
    public boolean loadFromFile(String fileName, InformationManager informationManager) {

        if (isSqlitePath(fileName)) {
            return loadFromDatabase(fileName, informationManager);
        }
        if (isSqlScriptPath(fileName)) {
            return importSqlScript(fileName, informationManager);
        }

        try {
            File file = new File(fileName);

            System.out.println("Looking for file at: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("File doesn't exist");
                return false;
            }

            List<String[]> raceLines = new ArrayList<>();

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    line = line.trim();

                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] parts = line.split("\\|");
                    if (parts.length < 2) {
                        continue;
                    }

                    String recordType = parts[0].trim().toUpperCase();

                    if (recordType.equals("DRIVER")) {
                        Driver driver = parseDriver(parts);

                        if (driver != null) informationManager.addDriver(driver);

                    } else if (recordType.equals("RACE")) {
                        raceLines.add(parts);
                    }
                }
            }

            for (String[] raceParts : raceLines) {
                Race race = parseRace(raceParts, informationManager);

                if (race != null) informationManager.addRace(race);
            }
            setLastError("");
            return true;

        } catch (Exception e) {
            setLastError("Text load failed: " + e.getMessage());

            return false;
        }
    }

    // Method to save data to a file, returns true if successful, otherwise false
    public boolean saveToFile(String fileName, InformationManager informationManager) {

        if (isSqlitePath(fileName)) {
            return saveToDatabase(fileName, informationManager);
        }
        if (isSqlScriptPath(fileName)) {
            setLastError("Cannot save to .sql script path. Choose a .db/.sqlite file.");
            return false;
        }

        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Driver driver : informationManager.getDriversData()) {
                writer.println("DRIVER|"
                        + driver.getDriverId() + "|"
                        + driver.getDriverName() + "|"
                        + driver.getCarNumber() + "|"
                        + driver.getNationality() + "|"
                        + driver.getTeam() + "|"
                        + driver.getRaceEntered() + "|"
                        + driver.getPodiums() + "|"
                        + driver.getRaceWins() + "|"
                        + driver.getTotalPoints() + "|"
                        + driver.isActiveStatus());
            }


            for (Race race : informationManager.getRacesData()) {
                Driver driver = race.getDriver();
                int driverId = (driver != null) ? driver.getDriverId() : -1;
                writer.println("RACE|"
                        + race.getRaceId() + "|"
                        + driverId + "|"
                        + race.getRaceName() + "|"
                        + race.getLocation() + "|"
                        + race.getCountry() + "|"
                        + race.getDate() + "|"
                        + race.getTotalLaps() + "|"
                        + race.getPosition() + "|"
                        + race.getResult());
            }

            setLastError("");
            return true;
        } catch (Exception e) {
            setLastError("Text save failed: " + e.getMessage());
            return false;
        }
    }

    //Method to load from the database, should be able to load information from any SQLite database, even after processing the script
    public boolean loadFromDatabase(String databasePath, InformationManager informationManager) {
        String url = "jdbc:sqlite:" + databasePath;

        try (Connection connection = DriverManager.getConnection(url)) {
            ensureSchema(connection);

            List<RaceRow> pendingRaces = new ArrayList<>();
            try (PreparedStatement driverStmt = connection.prepareStatement("SELECT * FROM drivers ORDER BY driver_id");
                 ResultSet driverResults = driverStmt.executeQuery()) {
                while (driverResults.next()) {
                    Driver driver = new Driver(
                            driverResults.getInt("driver_id"),
                            driverResults.getString("driver_name"),
                            driverResults.getInt("car_number"),
                            driverResults.getString("nationality"),
                            driverResults.getString("team"),
                            driverResults.getInt("races_entered"),
                            driverResults.getInt("podiums"),
                            driverResults.getInt("race_wins"),
                            driverResults.getInt("total_points"),
                            driverResults.getBoolean("active_status")
                    );
                    informationManager.addDriver(driver);
                }
            }

            try (PreparedStatement raceStmt = connection.prepareStatement("SELECT * FROM races ORDER BY race_id");
                 ResultSet raceResults = raceStmt.executeQuery()) {
                while (raceResults.next()) {
                    pendingRaces.add(new RaceRow(
                            raceResults.getInt("race_id"),
                            raceResults.getInt("driver_id"),
                            raceResults.getString("race_name"),
                            raceResults.getString("location"),
                            raceResults.getString("country"),
                            raceResults.getString("race_date"),
                            raceResults.getInt("total_laps"),
                            raceResults.getInt("position"),
                            raceResults.getInt("result")
                    ));
                }
            }

            for (RaceRow row : pendingRaces) {
                Driver driver = row.driverId > 0 ? informationManager.getDriverById(row.driverId) : null;
                informationManager.addRace(new Race(row.raceId, driver, row.raceName, row.location, row.country, row.raceDate, row.totalLaps, row.position, row.result));
            }
            setLastError("");
            return true;
        } catch (Exception e) {
            setLastError("Database load failed: " + e.getMessage());
            return false;

        }
    }

    //Method to save to database after any changes, or by user choice
    public boolean saveToDatabase(String databasePath, InformationManager informationManager) {
        String url = "jdbc:sqlite:" + databasePath;
        try (Connection connection = DriverManager.getConnection(url)) {
            ensureSchema(connection);
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM races");
                statement.executeUpdate("DELETE FROM drivers");
            }

            try (PreparedStatement driverInsert = connection.prepareStatement(
                    "INSERT INTO drivers (driver_id, driver_name, car_number, nationality, team, races_entered, podiums, race_wins, total_points, active_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                 PreparedStatement raceInsert = connection.prepareStatement(
                         "INSERT INTO races (race_id, driver_id, race_name, location, country, race_date, total_laps, position, result) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
            ) {
                for (Driver driver : informationManager.getDriversData()) {
                    driverInsert.setInt(1, driver.getDriverId());
                    driverInsert.setString(2, driver.getDriverName());
                    driverInsert.setInt(3, driver.getCarNumber());
                    driverInsert.setString(4, driver.getNationality());
                    driverInsert.setString(5, driver.getTeam());
                    driverInsert.setInt(6, driver.getRaceEntered());
                    driverInsert.setInt(7, driver.getPodiums());
                    driverInsert.setInt(8, driver.getRaceWins());
                    driverInsert.setInt(9, driver.getTotalPoints());
                    driverInsert.setBoolean(10, driver.isActiveStatus());
                    driverInsert.addBatch();
                }
                driverInsert.executeBatch();

                for (Race race : informationManager.getRacesData()) {
                    raceInsert.setInt(1, race.getRaceId());
                    raceInsert.setInt(2, race.getDriver() == null ? 0 : race.getDriver().getDriverId());
                    raceInsert.setString(3, race.getRaceName());
                    raceInsert.setString(4, race.getLocation());
                    raceInsert.setString(5, race.getCountry());
                    raceInsert.setString(6, race.getDate());
                    raceInsert.setInt(7, race.getTotalLaps());
                    raceInsert.setInt(8, race.getPosition());
                    raceInsert.setInt(9, race.getResult());
                    raceInsert.addBatch();
                }
                raceInsert.executeBatch();
            }

            connection.commit();
            connection.setAutoCommit(true);
            setLastError("");
            return true;
        } catch (Exception e) {
            setLastError("Database save failed: " + e.getMessage());
            return false;
        }
    }

    //Method to identify a Script in the path given by the user, if script, will launch the import method
    private boolean isSqlScriptPath(String path) {
        String normalized = path == null ? "" : path.toLowerCase().trim();
        return normalized.endsWith(".sql");
    }

    //Method to import script from the path, create a database, and fill it with the information in the script
    private boolean importSqlScript(String scriptPath, InformationManager informationManager) {
        try {
            Path sqlPath = Path.of(scriptPath);
            String fileName = sqlPath.getFileName().toString();
            String baseName = fileName.endsWith(".sql") ? fileName.substring(0, fileName.length() - 4) : fileName;
            Path databasePath = sqlPath.resolveSibling(baseName + ".db");

            String sqlContent = Files.readString(sqlPath, StandardCharsets.UTF_8);
            String[] statements = sqlContent.split(";\\s*(?:\\r?\\n|$)");

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath.toString());
                 Statement statement = connection.createStatement()) {
                for (String rawStatement : statements) {
                    String sql = rawStatement.trim();
                    if (sql.isEmpty() || sql.startsWith("--")) {
                        continue;
                    }
                    statement.execute(sql);
                }
            }

            boolean loaded = loadFromDatabase(databasePath.toString(), informationManager);
            if (!loaded) {
                setLastError("SQL script imported, but DB load failed: " + getLastError());
                return false;
            }
            setLastError("Imported SQL script into database: " + databasePath);
            return true;
        } catch (Exception exception) {
            setLastError("SQL script import failed: " + exception.getMessage());
            return false;
        }
    }


    //Method to identify Sqlite Path, Database path, if correct, should launch the next step
    public boolean isSqlitePath(String path) {
        String normalized = path == null ? "" : path.toLowerCase().trim();
        return normalized.endsWith(".db") || normalized.endsWith(".sqlite") || normalized.endsWith(".sqlite3");
    }

    //Method to ensure schema designed can be followed for better readability
    private void ensureSchema(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS drivers (" +
                    "driver_id INTEGER PRIMARY KEY," +
                    "driver_name TEXT NOT NULL," +
                    "car_number INTEGER NOT NULL," +
                    "nationality TEXT NOT NULL," +
                    "team TEXT NOT NULL," +
                    "races_entered INTEGER NOT NULL," +
                    "podiums INTEGER NOT NULL," +
                    "race_wins INTEGER NOT NULL," +
                    "total_points INTEGER NOT NULL," +
                    "active_status INTEGER NOT NULL CHECK (active_status IN (0,1))" +
                    ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS races (" +
                    "race_id INTEGER PRIMARY KEY," +
                    "driver_id INTEGER NOT NULL," +
                    "race_name TEXT NOT NULL," +
                    "location TEXT NOT NULL," +
                    "country TEXT NOT NULL," +
                    "race_date TEXT NOT NULL," +
                    "total_laps INTEGER NOT NULL," +
                    "position INTEGER NOT NULL," +
                    "result INTEGER NOT NULL," +
                    "FOREIGN KEY (driver_id) REFERENCES drivers(driver_id)" +
                    ")");
        }
    }

    //Method to parse the Driver information gathered from the file
    private Driver parseDriver(String[] parts) {
        if (parts.length != 11) {
            System.out.println("Incorrect driver format.");
            return null;
        }

        Integer driverId = Integer.parseInt(parts[1]);
        Integer carNumber = Integer.parseInt(parts[3]);
        Integer raceEntered = Integer.parseInt(parts[6]);
        Integer podiums = Integer.parseInt(parts[7]);
        Integer raceWins = Integer.parseInt(parts[8]);
        Integer totalPoints = Integer.parseInt(parts[9]);
        Boolean activeStatus = Boolean.parseBoolean(parts[10]);

        if (driverId == null || carNumber == null || totalPoints == null || raceWins == null || raceEntered == null || podiums == null || activeStatus == null) {
            System.out.println("Incorrect format.");
            return null;
        }

        return new Driver(driverId, parts[2], carNumber, parts[4], parts[5], raceEntered, podiums, raceWins, totalPoints, activeStatus);

    }

    //Method to parse the Race information gathered from the file
    private Race parseRace(String[] parts, InformationManager manager) {
        if (parts.length != 10) {
            System.out.println("Incorrect race format.");
            return null;
        }

        Integer raceId = Integer.parseInt(parts[1]);
        Integer driverId = Integer.parseInt(parts[2]);
        Integer totalLaps = Integer.parseInt(parts[7]);
        Integer position = Integer.parseInt(parts[8]);
        Integer result = Integer.parseInt(parts[9]);

        if (raceId == null || driverId == null || totalLaps == null || position == null || result == null) {
            System.out.println("Incorrect race format.");
            return null;
        }

        Driver driver = (driverId >= 0) ? manager.getDriverById(driverId) : null;

        return new Race(raceId, driver, parts[3], parts[4], parts[5], parts[6], totalLaps, position, result);
    }

    //Method to set the RaceRow variable
    private record RaceRow(int raceId, int driverId, String raceName, String location, String country, String raceDate, int totalLaps, int position, int result) {


    }
}



