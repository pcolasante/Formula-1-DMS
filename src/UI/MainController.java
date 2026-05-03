package UI;

import Core.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

/**
 * Author: Paulina Flores Colasante
 * Course: Software Development 1
 * Date: 4/8/2026
 * <p>
 * Class MainControllerJava: This class will launch the controllers F1App GUI, including all the FXML, CSS, Script, tables, and other settings. Essentially, it is the logic of the GUI.
 */


public class MainController {

    private final InformationManager informationManager = new InformationManager();
    private final FileManager fileManager = new FileManager();
    private final ChampionshipCalculator championshipCalculator = new ChampionshipCalculator();
    private final DataValidation validator = new DataValidation();
    private final String defaultDataFileName = "f1-data.db";
    private final ObservableList<Driver> driverRows = FXCollections.observableArrayList();
    private final ObservableList<Race> raceRows = FXCollections.observableArrayList();
    private final ObservableList<String> standingsRows = FXCollections.observableArrayList();

    //Declaring controllers for FXML and CSS actions
    @FXML
    private TabPane mainTabPane;
    @FXML
    private BorderPane driversContentRoot;
    @FXML
    private BorderPane racesContentRoot;
    @FXML
    private BorderPane standingsContentRoot;
    @FXML
    private BorderPane fileContentRoot;
    @FXML
    private TableView<Driver> driverTable;
    @FXML
    private TextField driverIdField;
    @FXML
    private TextField driverNameField;
    @FXML
    private TextField driverCarField;
    @FXML
    private TableView<Race> racesTable;
    @FXML
    private TextField raceIdField;
    private String dataFileName = defaultDataFileName;


    /**
     * initialize: Method to initialize the app with the current design, pulls FXML tabs and wires them correctly, other wise fails
     */
    @FXML
    private void initialize() {
        try {
            if (mainTabPane == null || mainTabPane.getTabs().size() < 4) {
                throw new IllegalStateException("FXML tabs are not wired correctly.");
            }
            loadExistingData();
            refreshAll();
            mainTabPane.getTabs().get(0).setContent(buildDriversPane());
            mainTabPane.getTabs().get(1).setContent(buildRacesPane());
            mainTabPane.getTabs().get(2).setContent(buildStandingsPane());
            mainTabPane.getTabs().get(3).setContent(buildFilePane());
            mainTabPane.getSelectionModel().select(0);
        } catch (Exception exception) {
            if (mainTabPane != null) {
                Tab errorTab = new Tab("Error", new Label("UI initialization failed: " + exception.getMessage()));
                errorTab.setClosable(false);
                mainTabPane.getTabs().setAll(errorTab);
            }
            exception.printStackTrace();
        }
    }


    /**
     * buildDriversPane: Method to build Driver tab on DMS, user should be able to see all drivers, add, update, delete, select to update or delete, and clear fields
     *
     * @return BordenPane
     */
    @FXML
    private BorderPane buildDriversPane() {

        TableView<Driver> tableViewDriver = new TableView<>(driverRows);

        tableViewDriver.getColumns().addAll(
                driverNumberCol("ID", Driver::getDriverId),
                driverStringCol("Name", Driver::getDriverName),
                driverNumberCol("Car Number", Driver::getCarNumber),
                driverStringCol("Nationality", Driver::getNationality),
                driverStringCol("Team", Driver::getTeam),
                driverNumberCol("Races", Driver::getRaceEntered),
                driverNumberCol("Podiums", Driver::getPodiums),
                driverNumberCol("Wins", Driver::getRaceWins),
                driverNumberCol("Total Points", Driver::getTotalPoints),
                new TableColumn<Driver, Boolean>("Active") {{
                    setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isActiveStatus()));
                }}

        );

        tableViewDriver.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TextField id = new TextField();
        TextField name = new TextField();
        TextField carNumber = new TextField();
        TextField nationality = new TextField();
        TextField team = new TextField();
        TextField races = new TextField();
        TextField podiums = new TextField();
        TextField wins = new TextField();
        TextField totalPoints = new TextField();
        CheckBox active = new CheckBox("Active");
        active.setSelected(true);

        //Driver Information
        GridPane gridPane = formGrid();
        gridPane.addRow(0, new Label("Driver ID:"), id);
        gridPane.addRow(1, new Label("Driver Name:"), name);
        gridPane.addRow(2, new Label("Car Number:"), carNumber);
        gridPane.addRow(3, new Label("Nationality:"), nationality);
        gridPane.addRow(4, new Label("Team:"), team);
        gridPane.addRow(5, new Label("Races:"), races);
        gridPane.addRow(6, new Label("Podiums:"), podiums);
        gridPane.addRow(7, new Label("Wins:"), wins);
        gridPane.addRow(8, new Label("Total Points:"), totalPoints);
        gridPane.addRow(9, new Label("Status:"), active);


        //Adds Driver
        Button add = new Button("Add");
        add.setOnAction(e -> {
            Driver driver = buildDriver(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
            if (driver == null) return;
            if (!informationManager.addDriver(driver)) {
                showAlert(Alert.AlertType.WARNING, "DUPLICATE", "Driver already exists!");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        //Updates Driver
        Button update = new Button("Update");
        update.setOnAction(e -> {
            Driver driver = buildDriver(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
            if (driver == null) return;
            if (!informationManager.updateDriverById(driver.getDriverId(), driver)) {
                showAlert(Alert.AlertType.WARNING, "NOT FOUND", "Driver does not exist!");
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        //Deletes Driver
        Button delete = new Button("Delete by ID");
        delete.setOnAction(e -> {
            Integer driverId = validator.parseInt(id.getText().trim());
            if (driverId == null || !informationManager.removeDriver(driverId)) {
                showAlert(Alert.AlertType.WARNING, "Not found", "Driver ID not found.");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        //Clear button for Driver Tab
        Button clear = new Button("Clear");
        clear.setOnAction(e -> clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active));

        //Button to load selected
        Button loadSelected = new Button("Load selected");
        loadSelected.setOnAction(e -> {
            Driver selected = tableViewDriver.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a driver row first.");
                return;
            }
            populateDriverFields(selected, id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        //Button to delete selected
        Button deleteSelected = new Button("Delete selected");
        deleteSelected.setOnAction(e -> {
            Driver selected = tableViewDriver.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a driver row first.");
                return;
            }
            if (!informationManager.removeDriver(selected.getDriverId())) {
                showAlert(Alert.AlertType.WARNING, "Not found", "Selected driver no longer exists.");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        VBox left = new VBox(10, gridPane, new HBox(8, add, update, delete), new HBox(8, loadSelected, deleteSelected, clear));
        left.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableViewDriver);
        borderPane.setLeft(left);
        return borderPane;

    }


    /**
     * buildRacesPane: Method to build Race tab on DMS, user should be able to see all races, add, update, delete, select to update or delete, and clear fields
     *
     * @return BordenPane
     */
    private BorderPane buildRacesPane() {
        TableView<Race> tableView = new TableView<>(raceRows);
        tableView.getColumns().addAll(raceNumberCol("Race ID", Race::getRaceId),
                raceStringCol("Race", Race::getRaceName),
                raceStringCol("Location", Race::getLocation),
                raceStringCol("Country", Race::getCountry),
                raceStringCol("Date", Race::getDate),
                raceNumberCol("Laps", Race::getTotalLaps),
                raceNumberCol("Position", Race::getPosition),
                raceNumberCol("Result", Race::getResult),
                raceStringCol("Driver", r -> r.getDriver() == null ? "Unknown" : r.getDriver().getDriverName()));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TextField raceId = new TextField();
        TextField driverId = new TextField();
        TextField raceName = new TextField();
        TextField location = new TextField();
        TextField country = new TextField();
        TextField date = new TextField();
        TextField totalLaps = new TextField();
        TextField position = new TextField();
        TextField results = new TextField();

        //Race information
        GridPane gridPane = formGrid();
        gridPane.addRow(0, new Label("Race ID:"), raceId);
        gridPane.addRow(1, new Label("Driver ID (0 or Blank - If None):"), driverId);
        gridPane.addRow(2, new Label("Race Name:"), raceName);
        gridPane.addRow(3, new Label("Location:"), location);
        gridPane.addRow(4, new Label("Country:"), country);
        gridPane.addRow(5, new Label("Date:"), date);
        gridPane.addRow(6, new Label("Total Laps:"), totalLaps);
        gridPane.addRow(7, new Label("Position:"), position);
        gridPane.addRow(8, new Label("Result:"), results);

        //Add Race
        Button add = new Button("Add");
        add.setOnAction(e -> {
            Race race = buildRace(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
            if (race == null) return;
            if (!informationManager.addRace(race)) {
                showAlert(Alert.AlertType.WARNING, "DUPLICATE", "Race already exists!");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);

        });

        // Update Race
        Button update = new Button("Update");
        update.setOnAction(e -> {
            Race race = buildRace(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
            if (race == null) return;
            if (!informationManager.updateRaceById(race.getRaceId(), race)) {
                showAlert(Alert.AlertType.WARNING, "NOT FOUND", "Race does not exist!");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

        //Delete Race
        Button delete = new Button("Delete by ID");
        delete.setOnAction(e -> {
            Integer id = validator.parseInt(raceId.getText().trim());
            if (id == null || !informationManager.deleteRace(id)) {
                showAlert(Alert.AlertType.WARNING, "NOT FOUND", "Race ID not found!");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

        //Load Selected
        Button loadSelected = new Button("Load selected");
        loadSelected.setOnAction(e -> {
            Race selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a race row first.");
                return;
            }
            populateRaceFields(selected, raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

        //Delete Selected
        Button deleteSelected = new Button("Delete selected");
        deleteSelected.setOnAction(e -> {
            Race selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a race row first.");
                return;
            }
            if (!informationManager.deleteRace(selected.getRaceId())) {
                showAlert(Alert.AlertType.WARNING, "Not found", "Selected race no longer exists.");
                return;
            }
            refreshAll();
            persistChangesIfDatabaseSource();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

        //Button to clear fields
        Button clear = new Button("Clear");
        clear.setOnAction(e -> clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results));


        VBox left = new VBox(10, gridPane, new HBox(8, add, update, delete), new HBox(8, loadSelected, deleteSelected, clear));
        left.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setLeft(left);
        return borderPane;
    }


    /**
     * Method that builds Standings tab on DMS, user is able to see calculated standings after calculation
     *
     * @return VBox
     */
    private VBox buildStandingsPane() {
        ListView<String> standingsView = new ListView<>(standingsRows);
        Button calculate = new Button("Calculate Standings");
        calculate.setOnAction(e -> refreshStandings());
        return new VBox(10, calculate, standingsView);
    }


    /**
     * Method that builds File tab on the DMS, user is able to browse, type, load, and save from/to file
     *
     * @return
     */
    private VBox buildFilePane() {
        Label fileLabel = new Label("Current File: " + dataFileName);

        TextField fileName = new TextField(dataFileName);
        fileName.setPromptText("Enter SQLite path (.db/.sqlite) or text file path");

        //Button to browse file
        Button browse = new Button("Browse");
        browse.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select F1 Data file");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("SQLite files", "*.db", "*.sqlite", ".sqlite3"),
                    new FileChooser.ExtensionFilter("SQL scripts", "*.sql"),
                    new FileChooser.ExtensionFilter("Text files", "*.txt", "."),
                    new FileChooser.ExtensionFilter("All files", "*.*"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                fileName.setText(file.getAbsolutePath());
                dataFileName = file.getAbsolutePath();
                fileLabel.setText("Current File: " + dataFileName);
            }
        });

        //Button to type file name
        Button useTyped = new Button("Type file name");
        useTyped.setOnAction(e -> {
            String typed = fileName.getText() == null ? "" : fileName.getText().trim();
            dataFileName = typed.isEmpty() ? dataFileName : typed;
            fileLabel.setText("Current File: " + dataFileName);
        });

        //Button to load from file
        Button load = new Button("Load from file");
        load.setOnAction(e -> {
            String typed = fileName.getText() == null ? "" : fileName.getText().trim();
            dataFileName = typed.isEmpty() ? dataFileName : typed;
            fileLabel.setText("Current File: " + dataFileName);

            informationManager.loadDrivers(new ArrayList<>());
            informationManager.loadRaces(new ArrayList<>());
            loadExistingData();
            refreshAll();
        });

        //Button to save file
        Button save = new Button("Save");
        save.setOnAction(e -> {
            String typed = fileName.getText() == null ? "" : fileName.getText().trim();
            dataFileName = typed.isEmpty() ? dataFileName : typed;
            fileLabel.setText("Current File: " + dataFileName);

            boolean saved = fileManager.saveToFile(dataFileName, informationManager);
            if (saved) showAlert(Alert.AlertType.INFORMATION, "Saved", "Data saved successfully to " + dataFileName);
            else showAlert(Alert.AlertType.ERROR, "Error", "Could not save data to " + dataFileName);
        });

        return new VBox(10, fileLabel, fileName, new HBox(8, browse, useTyped), new HBox(8, load, save));
    }


    /**
     * buildDriver: Builds driver from the input given by the user, should return driver added to the file
     *
     * @param id
     * @param name
     * @param car
     * @param nationality
     * @param team
     * @param races
     * @param podiums
     * @param wins
     * @param totalPoints
     * @param active
     * @return
     */
    private Driver buildDriver(TextField id, TextField name, TextField car, TextField nationality, TextField team, TextField races, TextField podiums, TextField wins, TextField totalPoints, CheckBox active) {
        Integer driverId = validator.parseInt(id.getText().trim());
        Integer carNumber = validator.parseInt(car.getText().trim());
        Integer raceCount = validator.parseInt(races.getText().trim());
        Integer podiumCount = validator.parseInt(podiums.getText().trim());
        Integer winCount = validator.parseInt(wins.getText().trim());
        Integer totalPointCount = validator.parseInt(totalPoints.getText().trim());

        if (driverId == null ||
                carNumber == null ||
                raceCount == null ||
                podiumCount == null ||
                winCount == null ||
                totalPointCount == null ||
                !validator.isValidString(name.getText()) ||
                !validator.isValidString(nationality.getText()) ||
                !validator.isValidString(team.getText())) {

            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Input! Please provide valid driver information!");
            return null;
        }

        return new Driver(driverId, name.getText().trim(), carNumber, nationality.getText().trim(), team.getText().trim(), raceCount, podiumCount, winCount, totalPointCount, active.isSelected());

    }


    /**
     * buildRace: Builds a race from the input given by the user, should return the race added to the file
     *
     * @param raceId
     * @param driverIdField
     * @param raceName
     * @param location
     * @param country
     * @param date
     * @param laps
     * @param position
     * @param result
     * @return
     */
    private Race buildRace(TextField raceId, TextField driverIdField, TextField raceName, TextField location, TextField country, TextField date, TextField laps, TextField position, TextField result) {
        Integer raceIdField = validator.parseInt(raceId.getText().trim());
        String driverText = driverIdField.getText() == null ? "" : driverIdField.getText().trim();
        Integer driverId = driverText.isEmpty() ? 0 : validator.parseInt(driverText);
        Integer lapsCount = validator.parseInt(laps.getText().trim());
        Integer positionNumber = validator.parseInt(position.getText().trim());
        Integer resultNumber = validator.parseInt(result.getText().trim());

        if (raceIdField == null ||
                driverId == null ||
                lapsCount == null ||
                positionNumber == null ||
                resultNumber == null ||
                !validator.isValidString(raceName.getText()) ||
                !validator.isValidString(location.getText()) ||
                !validator.isValidString(country.getText()) ||
                !validator.isValidString(date.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid Input! Please provide valid race information!");
            return null;
        }

        Driver driver = null;
        if (driverId != null && driverId > 0) {
            driver = informationManager.getDriverById(driverId);
        }
        if (driverId != null && driverId > 0 && driver == null) {
            showAlert(Alert.AlertType.ERROR, "Driver not found", "Driver not found! Add driver first!");
            return null;
        }

        return new Race(raceIdField, driver, raceName.getText().trim(), location.getText().trim(), country.getText().trim(), date.getText().trim(), lapsCount, positionNumber, resultNumber);
    }

    /**
     * populateDriverFields: Populates Drivers fields when selecting a driver from the file
     *
     * @param driver
     * @param id
     * @param name
     * @param car
     * @param nationality
     * @param team
     * @param races
     * @param podiums
     * @param wins
     * @param totalPoints
     * @param active
     */
    private void populateDriverFields(Driver driver, TextField id, TextField name, TextField car, TextField nationality, TextField team, TextField races, TextField podiums, TextField wins, TextField totalPoints, CheckBox active) {
        id.setText(String.valueOf(driver.getDriverId()));
        name.setText(driver.getDriverName());
        car.setText(String.valueOf(driver.getCarNumber()));
        nationality.setText(driver.getNationality());
        team.setText(driver.getTeam());
        races.setText(String.valueOf(driver.getRaceEntered()));
        podiums.setText(String.valueOf(driver.getPodiums()));
        wins.setText(String.valueOf(driver.getRaceWins()));
        totalPoints.setText(String.valueOf(driver.getTotalPoints()));
        if (active != null) active.setSelected(driver.isActiveStatus());

    }

    /**
     * populateRaceFields: Populates races fields when selecting a race from the file
     *
     * @param race
     * @param raceId
     * @param driverIdField
     * @param raceName
     * @param location
     * @param country
     * @param date
     * @param laps
     * @param position
     * @param result
     */
    private void populateRaceFields(Race race, TextField raceId, TextField driverIdField, TextField raceName, TextField location, TextField country, TextField date, TextField laps, TextField position, TextField result) {
        raceId.setText(String.valueOf(race.getRaceId()));
        driverIdField.setText(race.getDriver() == null ? "" : String.valueOf(race.getDriver().getDriverId()));
        raceName.setText(race.getRaceName());
        location.setText(race.getLocation());
        country.setText(race.getCountry());
        date.setText(race.getDate());
        laps.setText(String.valueOf(race.getTotalLaps()));
        position.setText(String.valueOf(race.getPosition()));
        result.setText(String.valueOf(race.getResult()));

    }

    /**
     * clearDriverFields: Method to clear driver fields after any action made, updating, adding, or deleting
     *
     * @param id
     * @param name
     * @param car
     * @param nationality
     * @param team
     * @param races
     * @param podiums
     * @param wins
     * @param totalPoints
     * @param active
     */
    private void clearDriverFields(TextField id, TextField name, TextField car, TextField nationality, TextField team, TextField races, TextField podiums, TextField wins, TextField totalPoints, CheckBox active) {
        id.clear();
        name.clear();
        car.clear();
        nationality.clear();
        team.clear();
        races.setText("0");
        podiums.setText("0");
        wins.setText("0");
        totalPoints.setText("0");
        if (active != null) active.setSelected(true);
    }

    /**
     * clearRaceFields: Method to clear race fields after any action made, updating, adding, or deleting
     *
     * @param raceId
     * @param driverIdField
     * @param raceName
     * @param location
     * @param country
     * @param date
     * @param laps
     * @param position
     * @param result
     */
    private void clearRaceFields(TextField raceId, TextField driverIdField, TextField raceName, TextField location, TextField country, TextField date, TextField laps, TextField position, TextField result) {
        raceId.clear();
        driverIdField.clear();
        raceName.clear();
        location.clear();
        country.clear();
        date.clear();
        laps.clear();
        position.clear();
        result.clear();
    }

    /**
     * refreshDrivers: Method to refresh drivers, should return drivers all updated
     */
    private void refreshDrivers() {
        driverRows.setAll(informationManager.getDriversData());
    }

    /**
     * refreshRaces: Method to refresh races, should return races all updated
     */
    private void refreshRaces() {
        raceRows.setAll(informationManager.getRacesData());
    }


    /**
     * refreshStandings: Method to refresh standings, should return changed standings
     */
    private void refreshStandings() {

        Map<String, Integer> standings = championshipCalculator.calculateStandings(informationManager.getRacesData());
        standingsRows.setAll(standings.entrySet().stream().sorted(Comparator.comparingInt((Map.Entry<String, Integer> e) -> e.getValue()).reversed()).map(e -> e.getKey() + " - " + e.getValue() + " pts").toList());
    }

    /**
     * refreshAll: Method to update drivers, races, standings, after any action
     */
    private void refreshAll() {
        refreshDrivers();
        refreshRaces();
        refreshStandings();

    }

    /**
     * Method that refreshes and updates the table after an action
     */
    private void refreshTable() {

        driverRows.setAll(informationManager.getDriversData());

    }

    /**
     * Method to load any existing data from the persistent file
     */
    private void loadExistingData() {

        boolean loaded = fileManager.loadFromFile(dataFileName, informationManager);
        if (!loaded) {
            showAlert(Alert.AlertType.ERROR, "Loading Warning", "Could not load file: " + dataFileName + "\n" + fileManager.getLastError());
        }
    }

    /**
     * Method to persist changes to the database, returns true if data is valid
     */
    private void persistChangesIfDatabaseSource() {
        if (!fileManager.isSqlitePath(dataFileName)) {
            return;
        }
        boolean saved = fileManager.saveToFile(dataFileName, informationManager);
        if (!saved) {
            showAlert(Alert.AlertType.ERROR, "Save Error", "Could not save changes to " + dataFileName);

        }
    }

    /**
     * Method that forms the grid for the database
     *
     * @return GridPane
     */
    private GridPane formGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    /**
     * Method to build Driver Number column items, should return a number
     *
     * @param name
     * @param getter
     * @return TableColumn
     */
    private TableColumn<Driver, Number> driverNumberCol(String name, java.util.function.ToIntFunction<Driver> getter) {
        TableColumn<Driver, Number> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleIntegerProperty(getter.applyAsInt(data.getValue())));
        return col;
    }

    /**
     * Method to build Driver String column items. should return a string
     *
     * @param name
     * @param getter
     * @return Table Column
     */
    private TableColumn<Driver, String> driverStringCol(String name, java.util.function.Function<Driver, String> getter) {
        TableColumn<Driver, String> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleStringProperty(getter.apply(data.getValue())));
        return col;
    }

    /**
     * Method to build Race Number column items, should return a number
     *
     * @param name
     * @param getter
     * @return TableColumn
     */
    private TableColumn<Race, Number> raceNumberCol(String name, java.util.function.ToIntFunction<Race> getter) {
        TableColumn<Race, Number> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleIntegerProperty(getter.applyAsInt(data.getValue())));
        return col;
    }

    /**
     * Method to build Race String column items, should return a Strin
     *
     * @param name
     * @param getter
     * @return TableColumn
     */
    private TableColumn<Race, String> raceStringCol(String name, java.util.function.Function<Race, String> getter) {
        TableColumn<Race, String> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleStringProperty(getter.apply(data.getValue())));
        return col;
    }

    /**
     * Method to show alert box during any wrong input event, should return the message alert
     *
     * @param alertType
     * @param title
     * @param message
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (alert.getDialogPane() != null) {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/App Theme.css").toExternalForm());
            alert.getDialogPane().getStyleClass().add("app-alert");
        }
        alert.showAndWait();
    }


}