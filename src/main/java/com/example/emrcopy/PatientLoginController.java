package com.example.emrcopy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.emrcopy.User.*;

public class PatientLoginController{

    @FXML
    private TextField usernameTextField, icTextField;
    @FXML
    private Button loginButton, exit;
    @FXML
    public void login_clicked(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/DoctorLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();

            // Hide the previous scene's stage
            Node sourceNode = (Node) event.getSource();
            if (sourceNode != null && sourceNode.getScene() != null) {
                Stage previousStage = (Stage) sourceNode.getScene().getWindow();
                if (previousStage != null) {
                    previousStage.hide();
                }
            }
        }catch(IOException e){
            System.out.println("Can't load the scene" + e);
        }
    }

    @FXML
    private void handleExitButton() {
        try {
            Platform.exit();
        } catch (Exception e) {
            // Handle any exception that might occur during Platform.exit()
            e.printStackTrace();
            // You can also show an error message or perform other error handling tasks here
        }
    }

    @FXML
    private void loginButtonClicked(){
        String name = usernameTextField.getText();
        String nric = icTextField.getText();
        loadDatabase();

        try {
            if (User.readUser(nric).getUsername().equals(name)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + name + "!");
                alert.showAndWait();

                // Proceeds to dashboard page
                try {
                    PatientPovController.patientNRIC = nric;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Patients_pov.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Doctor's dashboard");
                    stage.setScene(scene);
                    stage.show();


                    // Hide the previous scene's stage
                    Stage previousStage = (Stage) loginButton.getScene().getWindow();
                    previousStage.hide();
                } catch (IOException e) {
                    System.out.println("Can't load the scene " + e);
                }

            } else { //Typed wrong a username but have their nric correct
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username! Please Try Again!");
                alert.showAndWait();
            }
        }
        catch (NullPointerException e){ //This user is not registered or typed in a wrong nric
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("No Such User! Please try again.");
            alert.showAndWait();
        }
    }

}
