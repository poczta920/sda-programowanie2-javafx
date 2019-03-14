package com.sda.main;

import com.sda.lib.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    private Logger logger = LogManager.getLogger(Controller.class);

    @FXML
    Parent mainPane;

    @FXML
    ScrollPane mainScrollPane;

    //welcome
    @FXML
    Label usernameLabel;

    //notes
    @FXML
    Label visibleNoteLabel;

    //calories
    @FXML
    PieChart caloriesPieChart;
    @FXML
    Label caloriesAbsorbedLabel;
    @FXML
    TextField newCaloriesTextField;

    //weather
    @FXML
    Label wLocalizationLabel;
    @FXML
    Label wTheTempLabel;
    @FXML
    Label wMinTempLabel;
    @FXML
    Label wMaxTempLabel;
    @FXML
    ImageView wImageView;
    @FXML
    Label addCaloriesLabel;


    //events
    @FXML
    ListView<String> eventsTodayListView;

    private int limitCalory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timerCalory();
        timerWheater();
        changeCalory();

        ConfigDTO configDTO = OpenSaveSettings.openConfig();
        limitCalory = configDTO.getLimitCalories();
        loadData();

        usernameLabel.setText(configDTO.getUserName());

        mainScrollPane.prefWidthProperty().bind(((Pane) mainPane).widthProperty());
        mainScrollPane.prefHeightProperty().bind(((Pane) mainPane).heightProperty());

        eventsTodayListView.getItems().add("Mary's birthday.");
        eventsTodayListView.getItems().add("Annual meeting.");
        caloriesPieChart.setStartAngle(90);

    }

    public void loadSettings(MouseEvent mouseEvent) {
        URL resource = getClass().getClassLoader().getResource("settings.fxml");
        if (resource != null) {
            try {
                Pane settingsPane = FXMLLoader.load(resource);
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(new Scene(settingsPane, 1040, 1000));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadData() {
        int absorbed = ConsumedCalories.getCalory();

        newCaloriesTextField.setText(String.valueOf(absorbed));

        this.caloriesAbsorbedLabel.setText(String.valueOf(absorbed));
        ObservableList<PieChart.Data> caloriesData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Absorbed", absorbed),
                        new PieChart.Data("Pending", limitCalory - absorbed));
        this.caloriesPieChart.setData(caloriesData);
    }


    public void incrementCalory(MouseEvent mouseEvent) {
        int absorbed = Integer.parseInt(newCaloriesTextField.getText());
        this.newCaloriesTextField.setText(String.valueOf(++absorbed));
        changeChart(absorbed);
    }


    private void changeCalory() {
        newCaloriesTextField.setOnAction(e -> {
            int absorbed = Integer.parseInt(newCaloriesTextField.getText());
            ConsumedCalories.saveCalory(absorbed);
            changeChart(absorbed);
        });

    }

    private void changeChart(int absorbed) {
        ConsumedCalories.saveCalory(absorbed);
        this.caloriesAbsorbedLabel.setText(String.valueOf(absorbed));
        if(absorbed > limitCalory){
            absorbed = limitCalory;
        }
        ObservableList<PieChart.Data> caloriesData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Absorbed", absorbed),
                        new PieChart.Data("Pending", limitCalory - absorbed));
        this.caloriesPieChart.setData(caloriesData);
    }


    private void timerCalory() {
        ScheduledExecutorService updater =
                Executors.newSingleThreadScheduledExecutor();
        updater.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> { //so it can be synchronized with FX
//program logic

                int absorbedValueFromForm = Integer.parseInt(newCaloriesTextField.getText());
                int absorbedValueFromFile = ConsumedCalories.getCalory();

                if (absorbedValueFromForm != absorbedValueFromFile) {
                    this.newCaloriesTextField.setText(String.valueOf(absorbedValueFromFile));
                    this.caloriesAbsorbedLabel.setText(String.valueOf(absorbedValueFromFile));
                }

            });
        }, 0, 1, TimeUnit.MINUTES); //initialDelay, period, time unit
    }

    private void timerWheater() {
        ScheduledExecutorService updater =
                Executors.newSingleThreadScheduledExecutor();
        updater.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> { //so it can be synchronized with FX
//program logic

                CheckWheater checkWheater = null;
                Weather weather = null;
                try {
                    weather = CheckWheater.checkWeatherNow();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                wTheTempLabel.setText(String.valueOf(weather.getTemp()) + " C");
                wMinTempLabel.setText(String.valueOf(weather.getMinTemp()) + " C");
                wMaxTempLabel.setText(String.valueOf(weather.getMaxTemp()) + " C");
                wLocalizationLabel.setText(weather.getLocalization());

                StringBuilder sb = new StringBuilder("icons/");
                sb.append(weather.getWeatherStateIcon());

                URL url = getClass().getClassLoader().getResource(sb.toString());
                System.out.println(url);

                wImageView.setImage(new Image(url.toString()));

            });
        }, 0, 30, TimeUnit.MINUTES); //initialDelay, period, time unit
    }


    public void loadNotepad(MouseEvent mouseEvent) {
        URL resource = getClass().getClassLoader().getResource("notepad.fxml");
        if (resource != null) {
            try {
                Pane Pane = FXMLLoader.load(resource);
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(new Scene(settingsPane, 1040, 1000));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
