package com.example.emrcopy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorLoginController implements Initializable{

    @FXML
    private TextField usernameTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public PasswordField hiddenPasswordTextField;
    @FXML
    public CheckBox showPassword;

    @FXML
    void ChangeVisibility(ActionEvent event){
        if(showPassword.isSelected()){
            passwordTextField.setText(hiddenPasswordTextField.getText());
            passwordTextField.setVisible(true);
            hiddenPasswordTextField.setVisible(false);
            return;
        }
        hiddenPasswordTextField.setText(passwordTextField.getText());
        hiddenPasswordTextField.setVisible(true);
        passwordTextField.setVisible(false);
    }
    @FXML
    public void register_clicked(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/PatientLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Patient Log In");
            stage.setScene(scene);
            stage.show();

            // Hide the previous scene's stage
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.hide();
        }catch(IOException e){
            System.out.println("Can't load the scene" + e);
        }
    }


    //doc login
    @FXML
    private void login_clicked(ActionEvent event) {
        // Get the entered username and password
        String username = usernameTextField.getText();
        String password = hiddenPasswordTextField.getText();

        try {
            // Perform username and password verification
            if (isDocLogin(username, password)){
                // Valid login, proceed with the desired action
                //login successful, success message shown
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + username + "!");
                alert.showAndWait();

                // Proceeds to dashboard page
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Doctor's dashboard");
                    stage.setScene(scene);
                    stage.show();

                    // Hide the previous scene's stage
                    Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    previousStage.hide();
                }catch(IOException e){
                    System.out.println("Can't load the scene" + e);
                }
            }
            else {
                // Invalid login, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the login process
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred during login: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public boolean isDocLogin(String username, String password) {
        // TODO: Implement the logic to verify the username and password
        // against the stored values in your data source (e.g., database)

        // For demonstration purposes, we'll use a simple hardcoded check
        String storedUsername = "admin";
        String storedPassword = "password";
        String enteredPassword = showPassword.isSelected() ? passwordTextField.getText() : hiddenPasswordTextField.getText();
        return storedUsername.equals(username) && storedPassword.equals(enteredPassword);
    }


    @FXML
    public void handleExitButton() {
        try {
            Platform.exit();
        } catch (Exception e) {
            // Handle any exception that might occur during Platform.exit()
            e.printStackTrace();
            // You can also show an error message or perform other error handling tasks here
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
