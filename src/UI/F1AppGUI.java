/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 4/26/2026

 Class F1AppGUI: This class will create the F1 GUI JavaFX, mirroring the menu system that we created previously

 */
package UI;

import Core.*;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;


//Method that initiates the F1AppGUI, to start the main private final methods
public class F1AppGUI extends Application {

    private final InformationManager informationManager = new InformationManager();
    private final FileManager fileManager = new FileManager();
    private final ChampionshipCalculator championshipCalculator = new ChampionshipCalculator();
    private final DataValidation validator = new DataValidation();
    private final String defaultDataFileName = "f1-data.txt";
    private final ObservableList<Driver> driverRows = FXCollections.observableArrayList();
    private final ObservableList<Race> raceRows = FXCollections.observableArrayList();
    private final ObservableList<String> standingsRows = FXCollections.observableArrayList();
    private String dataFileName = defaultDataFileName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadExistingData();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Drivers", buildDriversPane()));
        tabPane.getTabs().add(new Tab("Races", buildRacesPane()));
        tabPane.getTabs().add(new Tab("Standings", buildStandingsPane()));
        tabPane.getTabs().add(new Tab("File", buildFilePane()));
        tabPane.getTabs().forEach(tab -> tab.setClosable(false));

        refreshDrivers();
        refreshRaces();
        refreshStandings();

        BorderPane borderPane = new BorderPane(tabPane);
        borderPane.setPadding(new Insets(10));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/F1AppGUI.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("My App");
        primaryStage.show();
    }

    private BorderPane buildDriversPane() {

        TableView<Driver> tableView = new TableView<>(driverRows);

        tableView.getColumns().addAll(
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

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

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
            clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        Button clear = new Button("Clear");
        clear.setOnAction(e -> clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active));

        Button loadSelected = new Button("Load selected");
        loadSelected.setOnAction(e -> {
            Driver selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a driver row first.");
                return;
            }
            populateDriverFields(selected, id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        Button deleteSelected = new Button("Delete selected");
        deleteSelected.setOnAction(e -> {
            Driver selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a driver row first.");
                return;
            }
            if (!informationManager.removeDriver(selected.getDriverId())) {
                showAlert(Alert.AlertType.WARNING, "Not found", "Selected driver no longer exists.");
                return;
            }
            refreshAll();
            clearDriverFields(id, name, carNumber, nationality, team, races, podiums, wins, totalPoints, active);
        });

        VBox left = new VBox(10, gridPane, new HBox(8, add, update, delete), new HBox(8, loadSelected, deleteSelected, clear));
        left.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setLeft(left);
        return borderPane;

    }

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

        Button add = new Button("Add");
        add.setOnAction(e -> {
            Race race = buildRace(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
            if (race == null) return;
            if (!informationManager.addRace(race)) {
                showAlert(Alert.AlertType.WARNING, "DUPLICATE", "Race already exists!");
                return;
            }
            refreshAll();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);

        });

        Button update = new Button("Update");
        update.setOnAction(e -> {
            Race race = buildRace(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
            if (race == null) return;
            if (!informationManager.updateRaceById(race.getRaceId(), race)) {
                showAlert(Alert.AlertType.WARNING, "NOT FOUND", "Race does not exist!");
                return;
            }
            refreshAll();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

        Button delete = new Button("Delete by ID");
        delete.setOnAction(e -> {
            Integer id = validator.parseInt(raceId.getText().trim());
            if (id == null || !informationManager.deleteRace(id)) {
                showAlert(Alert.AlertType.WARNING, "NOT FOUND", "Race ID not found!");
                return;
            }
            refreshAll();
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

        Button loadSelected = new Button("Load selected");
        loadSelected.setOnAction(e -> {
            Race selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.INFORMATION, "No selection", "Select a race row first.");
                return;
            }
            populateRaceFields(selected, raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });

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
            clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results);
        });


        Button clear = new Button("Clear");
        clear.setOnAction(e -> clearRaceFields(raceId, driverId, raceName, location, country, date, totalLaps, position, results));


        VBox left = new VBox(10, gridPane, new HBox(8, add, update, delete), new HBox(8, loadSelected, deleteSelected, clear));
        left.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setLeft(left);
        return borderPane;
    }

    private VBox buildStandingsPane() {
        ListView<String> standingsView = new ListView<>(standingsRows);
        Button calculate = new Button("Calculate Standings");
        calculate.setOnAction(e -> refreshStandings());
        return new VBox(10, calculate, standingsView);
    }

    private VBox buildFilePane() {
        Label fileLabel = new Label("Current File: " + dataFileName);

        TextField fileName = new TextField(dataFileName);
        fileName.setPromptText("Enter file name or Browse");

        Button browse = new Button("Browse");
        browse.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select F1 Data file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt", "."));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                fileName.setText(file.getAbsolutePath());
                dataFileName = file.getAbsolutePath();
                fileLabel.setText("Current File: " + dataFileName);
            }
        });

        Button useTyped = new Button("Type file name");
        useTyped.setOnAction(e -> {
            String typed = fileName.getText() == null ? "" : fileName.getText().trim();
            dataFileName = typed.isEmpty() ? dataFileName : typed;
            fileLabel.setText("Current File: " + dataFileName);
        });

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

        Button save = new Button("Save");
        save.setOnAction(e -> {
            String typed = fileName.getText() == null ? "" : fileName.getText().trim();
            dataFileName = typed.isEmpty() ? dataFileName : typed;
            fileLabel.setText("Current File: " + dataFileName);

            boolean saved = fileManager.saveToFile(dataFileName, informationManager);
            if (saved) showAlert(Alert.AlertType.INFORMATION, "Saved", "Data saved successfully!");
            else showAlert(Alert.AlertType.ERROR, "Error", "Could not save data");
        });

        return new VBox(10, fileLabel, fileName, new HBox(8, browse, useTyped), new HBox(8, load, save));
    }

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

    private Race buildRace(TextField raceId, TextField driverIdField, TextField raceName, TextField location, TextField country, TextField date, TextField laps, TextField position, TextField result) {
        Integer raceIdField = validator.parseInt(raceId.getText().trim());
        Integer driverId = validator.parseInt(driverIdField.getText().trim());
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

        Driver driver = informationManager.getDriverById(driverId);
        if (driver == null) {
            showAlert(Alert.AlertType.ERROR, "Driver not found", "Driver not found! Add driver first!");
            return null;
        }

        return new Race(raceIdField, driver, raceName.getText().trim(), location.getText().trim(), country.getText().trim(), date.getText().trim(), lapsCount, positionNumber, resultNumber);
    }

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

    private void populateRaceFields(Race race, TextField raceId, TextField driverIdField, TextField raceName, TextField location, TextField country, TextField date, TextField laps, TextField position, TextField result) {
        raceId.setText(String.valueOf(race.getRaceId()));
        raceName.setText(race.getRaceName());
        location.setText(race.getLocation());
        country.setText(race.getCountry());
        date.setText(race.getDate());
        laps.setText(String.valueOf(race.getTotalLaps()));
        position.setText(String.valueOf(race.getPosition()));
        result.setText(String.valueOf(race.getResult()));

    }

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

    private void refreshDrivers() {
        driverRows.setAll(informationManager.getDriversData());
    }

    private void refreshRaces() {
        raceRows.setAll(informationManager.getRacesData());
    }

    private void refreshStandings() {

        Map<String, Integer> standings = championshipCalculator.calculateStandings(informationManager.getRacesData());
        standingsRows.setAll(standings.entrySet().stream().sorted(Comparator.comparingInt((Map.Entry<String, Integer> e) -> e.getValue()).reversed()).map(e -> e.getKey() + " - " + e.getValue() + " pts").toList());
    }

    private void refreshAll() {
        refreshDrivers();
        refreshRaces();
        refreshStandings();

    }

    private void refreshTable() {

        driverRows.setAll(informationManager.getDriversData());

    }

    private void loadExistingData() {

        fileManager.loadFromFile(dataFileName, informationManager);
    }

    private GridPane formGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    private TableColumn<Driver, Number> driverNumberCol(String name, java.util.function.ToIntFunction<Driver> getter) {
        TableColumn<Driver, Number> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleIntegerProperty(getter.applyAsInt(data.getValue())));
        return col;
    }

    private TableColumn<Driver, String> driverStringCol(String name, java.util.function.Function<Driver, String> getter) {
        TableColumn<Driver, String> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleStringProperty(getter.apply(data.getValue())));
        return col;
    }

    private TableColumn<Race, Number> raceNumberCol(String name, java.util.function.ToIntFunction<Race> getter) {
        TableColumn<Race, Number> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleIntegerProperty(getter.applyAsInt(data.getValue())));
        return col;
    }

    private TableColumn<Race, String> raceStringCol(String name, java.util.function.Function<Race, String> getter) {
        TableColumn<Race, String> col = new TableColumn<>(name);
        col.setCellValueFactory(data -> new SimpleStringProperty(getter.apply(data.getValue())));
        return col;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
