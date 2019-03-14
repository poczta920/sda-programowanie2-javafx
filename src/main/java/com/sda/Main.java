package com.sda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class Main extends Application {
    private Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL resource = getClass().getClassLoader().getResource("main.fxml");



        if(resource!=null) {
            Parent root = FXMLLoader.load(resource);
            primaryStage.setTitle("App");
            primaryStage.setScene(new Scene(root, 1040, 1000));
            primaryStage.setMaxWidth(1390.0);
            primaryStage.setMaxHeight(2000.0);
            primaryStage.setMinWidth(730.0);
            primaryStage.show();
        } else {
            logger.error("Resource is null.");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
