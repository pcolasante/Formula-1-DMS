/*Author: Paulina Flores Colasante
 Course: Software Development 1
 Date: 4/26/2026

 Class F1AppGUI: This class will create the F1 GUI JavaFX, mirroring the menu system that we created previously.
 It will initiate the controllers and the FXML set up.
 User should be able to click, see the list of drivers and races, modify as needed or desired, and leave when desired.

 */


package UI;

import Core.*;
import javafx.fxml.FXMLLoader;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;


//Method that initiates the F1AppGUI, to start the main private final methods
public class F1AppGUI extends Application {

    //Launches the GUI
    public static void main(String[] args) {
        launch(args);
    }

    //Method to start the GUI and all its fxml settings.
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        if (getClass().getResource("/fxml/F1AppGUI.fxml") != null) {
            loader.setLocation(getClass().getResource("/fxml/F1AppGUI.fxml"));
        } else {
            loader.setLocation(getClass().getResource("/resources/fxml/F1AppGUI.fxml"));
        }
        Scene scene;
        try {
            scene = new Scene(loader.load(), 1200, 700);
        } catch (IOException exception) {
            throw new RuntimeException("Could not load F1AppGUI.fxml", exception);
        }

        scene.getStylesheets().add(getClass().getResource("/css/App Theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Formula 1 DMS");
        primaryStage.show();
    }
}