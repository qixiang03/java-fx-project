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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author marcu
 */
public class SupportController implements Initializable {
    
    @FXML
    public void okay_clicked(ActionEvent event) throws IOException {
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}