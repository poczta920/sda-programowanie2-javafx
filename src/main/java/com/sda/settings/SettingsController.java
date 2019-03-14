package com.sda.settings;

import com.sda.lib.ConfigDTO;
import com.sda.lib.OpenSaveSettings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private Logger logger = LogManager.getLogger(SettingsController.class);
    private ConfigDTO configDTO = new ConfigDTO();


    @FXML
    Parent settingsPane;

    @FXML
    Slider slider;

    @FXML
    Label lblUserName;

    @FXML
    Label lblLocalization;

    @FXML
    Label lblSilderValue;

    @FXML

    Label lblLimitCalories;

    @FXML
    TextField txtUserName;

    @FXML
    TextField txtLocalization;


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        configDTO = OpenSaveSettings.openConfig();

        slider.setValue(configDTO.getLimitCalories());
        txtUserName.setText(configDTO.getUserName());
        txtLocalization.setText(configDTO.getLocalization());

        this.lblSilderValue.setText(String.valueOf(slider.getValue()));
        this.slider.valueProperty().addListener(((observable, oldValue, newValue) -> lblSilderValue.setText(String.valueOf((int) slider.getValue()))));

    }

    public void returnFromSettings(MouseEvent mouseEvent) {
        configDTO.setUserName(txtUserName.getText());
        configDTO.setLocalization(txtLocalization.getText());
        configDTO.setLimitCalories((int) slider.getValue());

        OpenSaveSettings.saveConfig(configDTO);

        URL resource = getClass().getClassLoader().getResource("main.fxml");
        if (resource != null) {
            try {
                Pane newPane = FXMLLoader.load(resource);
                Stage stage = (Stage) settingsPane.getScene().getWindow();
                stage.setScene(new Scene(newPane, 1040, 1000));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
