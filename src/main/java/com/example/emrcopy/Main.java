package com.example.emrcopy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent fxmlloader = FXMLLoader.load(getClass().getResource("fxml/DoctorLogin.fxml"));
        Scene scene = new Scene(fxmlloader);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {launch();}
}