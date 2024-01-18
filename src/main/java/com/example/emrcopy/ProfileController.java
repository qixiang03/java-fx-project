/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package com.example.emrcopy;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author marcu
 */
public class ProfileController implements Initializable {

    @FXML
    public void back(ActionEvent event) throws IOException {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IllegalStateException e) {
            System.out.println("Error: The stage is already closed.");
        } catch (Exception e) {
            System.out.println("An error occurred while closing the scene: " + e.getMessage());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Doctor's dashboard");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Hyperlink dashboard, logout, schedule, profile, setting;

    
    // method to open dashboard using hyperlink
    public void dash() throws IOException {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Dashboard.fxml"));
        primaryStage.setTitle("Doctor's Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // method to oepn schedule using hyperlink
    public void sche() throws IOException {
        Stage stage = (Stage) schedule.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Schedule.fxml"));
        primaryStage.setTitle("Schedule");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // method to logout
    public void logOut() throws IOException {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/DoctorLogin.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

 // method to oepn schedule using hyperlink
    public void set() throws IOException {
        Stage stage = (Stage) setting.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Support.fxml"));
        primaryStage.setTitle("Setting");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // method to logout
    public void pro() throws IOException {
        Stage stage = (Stage) profile.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Profile.fxml"));
        primaryStage.setTitle("Profile");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}