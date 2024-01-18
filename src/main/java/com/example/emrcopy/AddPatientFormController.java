package com.example.emrcopy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.emrcopy.User.*;

public class AddPatientFormController implements Initializable {

    @FXML
    private Label patientIDLabel;
    @FXML
    private TextField fullNameTextField, NRICTextField, telNoTextField, heightTextField, weightTextField;
    @FXML
    private ComboBox<String> sexComboBox;
    @FXML
    private Button infoAddButton, back;

    /**
     * Initializes the controller class.
     */
    public void back() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/PatientRecord.fxml"));
        primaryStage.setTitle("Patient's Record");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void getInfo(ActionEvent event) {

        String fullName = fullNameTextField.getText().trim();
        String nric = NRICTextField.getText().trim();
        String telNo = telNoTextField.getText().trim();
        String sex = sexComboBox.getValue();
        double height = Double.parseDouble(heightTextField.getText().trim());
        double weight = Double.parseDouble(weightTextField.getText().trim());

        createUser(fullName, sex, nric, height, weight, telNo);

        (new PatientRecordController()).addPatientLabel(fullName, nric);
        saveDatabase();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    private boolean infoValidate() {
        String fullName = fullNameTextField.getText().trim();
        String NRIC = NRICTextField.getText().trim();
        String telNo = telNoTextField.getText().trim();
        String sex = sexComboBox.getValue();

        if (fullName == null || fullName.trim().isEmpty()) {
            // Name is empty
            fullNameTextField.setStyle("-fx-border-color: red;");
            fullNameTextField.requestFocus();
            showAlert("Invalid Name Format!");
            return false;
        } else if (!fullName.matches("[\\p{L}\\p{M}\\s'-]+")) {
            // Name contains invalid characters
            fullNameTextField.setStyle("-fx-border-color: red;");
            fullNameTextField.requestFocus();
            showAlert("Invalid Name Format!");
            return false;
        } else {
            // Name is valid
            fullNameTextField.setStyle("");
        }

        try {
            double height = Double.parseDouble(heightTextField.getText().trim());
            if (height < 0.546 || height > 2.72) {
                heightTextField.setStyle("-fx-border-color: red;");
                heightTextField.requestFocus();
                showAlert("Invalid Height!");
            } else {
                heightTextField.setStyle("");
            }
        } catch (NumberFormatException e) {
            heightTextField.setStyle("-fx-border-color: red;");
            heightTextField.requestFocus();
            showAlert("Invalid Height Format!");
            return false;
        }

        try {
            double weight = Double.parseDouble(weightTextField.getText().trim());
            if (weight <= 1 || weight > 635) {
                weightTextField.setStyle("-fx-border-color: red;");
                weightTextField.requestFocus();
                showAlert("Invalid Height!");
            } else {
                weightTextField.setStyle("");
            }
        } catch (NumberFormatException e) {
            weightTextField.setStyle("-fx-border-color: red;");
            weightTextField.requestFocus();
            showAlert("Invalid Height Format!");
            return false;
        }

        if (!telNo.startsWith("601") || !(telNo.length() == 10 || telNo.length() == 11)) {
            telNoTextField.setStyle("-fx-border-color: red;");
            telNoTextField.requestFocus();
            showAlert("Invalid Phone Format!");
            return false;
        } else {
            telNoTextField.setStyle("");
        }

        if (sex.equals("-")) {
            sexComboBox.setStyle("-fx-border-color: red;");
            sexComboBox.requestFocus();
            showAlert("Select Your Sex!");
            return false;
        } else {
            sexComboBox.setStyle("");
        }

        return verifyNRIC(NRIC);

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean verifyNRIC(String nric) {
        if (nric.length() != 12) {
            NRICTextField.setStyle("-fx-border-color: red;");
            NRICTextField.requestFocus();
            showAlert("Invalid NRIC Format");
            return false;
        }

        // Check if first six digits represent a valid birthdate in YYMMDD format
        String birthdate = nric.substring(0, 6);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate.parse(birthdate, formatter);
            NRICTextField.setStyle("");
        } catch (DateTimeParseException e) {
            NRICTextField.setStyle("-fx-border-color: red;");
            NRICTextField.requestFocus();
            showAlert("Invalid NRIC Format");
            return false;
        }

        // Check if seventh and eighth digits in PB format represent a valid place of
        // birth
        String placeOfBirth = nric.substring(6, 8);
        List<String> invalidPlacesOfBirth = Arrays.asList("00", "17", "18", "19", "20", "69", "70", "73", "80", "81",
                "94", "95", "96", "97");
        if (invalidPlacesOfBirth.contains(placeOfBirth)) {
            NRICTextField.setStyle("-fx-border-color: red;");
            NRICTextField.requestFocus();
            showAlert("Invalid NRIC Format");
            return false;
        }

        // Check if last four digits in ###G format represent a valid gender
        String genderDigit = nric.substring(11);
        int gender = Integer.parseInt(genderDigit);
        if (sexComboBox.getValue().equals("Male") && gender % 2 == 0) {
            NRICTextField.setStyle("-fx-border-color: red;");
            NRICTextField.requestFocus();
            showAlert("Invalid NRIC Format");
            return false;
        } else if (sexComboBox.getValue().equals("Female") && gender % 2 == 1) {
            NRICTextField.setStyle("-fx-border-color: red;");
            NRICTextField.requestFocus();
            showAlert("Invalid NRIC Format");
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<String> list = FXCollections.observableArrayList("Male", "Female");
            sexComboBox.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        infoAddButton.setOnAction(e -> {
            if (infoValidate()) {
                String fullName = fullNameTextField.getText().trim();
                String nric = NRICTextField.getText().trim();
                String telNo = telNoTextField.getText().trim();
                String sex = sexComboBox.getValue();
                double height = Double.parseDouble(heightTextField.getText().trim());
                double weight = Double.parseDouble(weightTextField.getText().trim());

                createUser(fullName, sex, nric, height, weight, telNo);

                (new PatientRecordController()).addPatientLabel(fullName, nric);
                saveDatabase();

                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("fxml/PatientRecord.fxml"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                primaryStage.setTitle("Patient's Record");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }

        });
    }
}
