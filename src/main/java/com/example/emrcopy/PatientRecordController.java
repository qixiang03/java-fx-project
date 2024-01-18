package com.example.emrcopy;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.emrcopy.User.*;

public class PatientRecordController implements Initializable {

    @FXML
    private TableView<?> table;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, v_close, selectPatientButton;
    @FXML
    private Hyperlink dashboard, logout, schedule,profile,setting;
    @FXML
    private VBox overviewPatientVBox;
    public LineChart<Number, Number> heartdiagramChart;
    private static String tmpNRIC;
    private Label lastClickedLabel = null;
    @FXML
    private TextField searchField;

    protected static int curHeight =0;
    protected final static int vboxHeight =339;
    protected ObservableList<Label> labels = FXCollections.observableArrayList();


    //method to open doc's dashboard pg
    public void open_pov()throws IOException {
        Stage stage = (Stage)btn1.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Dashboard.fxml"));
        primaryStage.setTitle("Doctor's Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //method to open doc's schedule pg
    public void open_schedule()throws IOException {
        Stage stage = (Stage)btn2.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Schedule.fxml"));
        primaryStage.setTitle("Doctor's Schedule");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //method to open patient's record pg
    public void open_record()throws IOException {
        Stage stage = (Stage)btn3.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/PatientRecord.fxml"));
        primaryStage.setTitle("Patient's Record");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void open_lab()throws IOException {
        Stage stage = (Stage)btn4.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/PatientHistory.fxml"));
        primaryStage.setTitle("Patient's Lab");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //method to open doc's dashboard pg
    public void open_addPatient()throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AddPatientForm.fxml"));
        primaryStage.setTitle("Add Patient");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    //method to open dashboard using hyperlink
    public void dash()throws IOException {
        Stage stage = (Stage)dashboard.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Dashboard.fxml"));
        primaryStage.setTitle("Doctor's Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //method to oepn schedule using hyperlink
    public void sche()throws IOException {
        Stage stage = (Stage)schedule.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Schedule.fxml"));
        primaryStage.setTitle("Schedule");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //method to logout
    public void logOut()throws IOException {
        Stage stage = (Stage)logout.getScene().getWindow();
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
    public void addPatientLabel(String fullname, String nric){

        Label tmpLabel = new Label(fullname);
        tmpLabel.setPrefSize(106,28);
        tmpLabel.setAlignment(Pos.CENTER);
        tmpLabel.setMinHeight(28);
        tmpLabel.setMaxHeight(28);
        tmpLabel.setFont(Font.font("Times New Roman Bold", 14));
        tmpLabel.setAccessibleText(nric);
        tmpLabel.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                lastClickedLabel = tmpLabel;
                heartdiagramChart.setCreateSymbols(false);
                User patient = readUser(tmpLabel.getAccessibleText());
                XYChart.Series<Number, Number> heartRateSeries = new XYChart.Series<>();

                for (DataPoint dataPoint : patient.getHeartRateDataPoint()) {
                    heartRateSeries.getData().add(new XYChart.Data<>(dataPoint.getX(), dataPoint.getY()));
                }
                heartdiagramChart.getData().removeAll();
                heartdiagramChart.getData().add(heartRateSeries);
            }
            if (event.getClickCount() == 2) {
                // Double-click detected
                selectPatientButtonClicked();
            }
        });
        labels.add(tmpLabel);

    }


    public void loadPatient() {
        loadDatabase();
        Iterator<User> i = database.iterator();
        for (User user : database) {
            addPatientLabel(user.getUsername(), user.getnric());
        }
    }

    @FXML
    private void selectPatientButtonClicked(){
        if(lastClickedLabel != null){
            tmpNRIC = lastClickedLabel.getAccessibleText();
            PatientHistoryController.patientNRIC = tmpNRIC;
            Stage prev = (Stage) selectPatientButton.getScene().getWindow();
            prev.close();
            Stage primaryStage = new Stage();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("fxml/PatientHistory.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            primaryStage.setTitle("Patient's Lab");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    private int getSimilarity(String text1, String text2) {
        // Convert the input strings to lower case
        text1 = text1.toLowerCase();
        text2 = text2.toLowerCase();

        // Initialize the distance matrix
        int[][] distance = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i <= text1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= text2.length(); j++) {
            distance[0][j] = j;
        }

        // Compute the Levenshtein distance
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                int cost = (text1.charAt(i - 1) == text2.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1), distance[i - 1][j - 1] + cost);
            }
        }

        // Return the Levenshtein distance as the similarity score
        return distance[text1.length()][text2.length()];
    }

    private void updatePatientList(String searchText) {
        // Clear the patient VBox
        overviewPatientVBox.getChildren().clear();

        // Filter the patient labels based on the search text
        List<Label> filteredLabels = labels.stream()
                .filter(label -> label.getText().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());

        // Sort the filtered labels based on their similarity to the search text
        filteredLabels.sort(Comparator.comparing(label -> getSimilarity(label.getText(), searchText)));

        // Add the filtered and sorted labels to the patient VBox
        overviewPatientVBox.getChildren().addAll(filteredLabels);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadPatient();
        overviewPatientVBox.getChildren().addAll(labels);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updatePatientList(newValue);
        });
        labels.addListener((ListChangeListener<Label>) change -> {
            while (change.next()) {
                if (!change.wasAdded()) {
                    overviewPatientVBox.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    overviewPatientVBox.getChildren().removeAll(change.getRemoved());
                }
            }
        });

    }
}
