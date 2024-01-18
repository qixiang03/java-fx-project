package com.example.emrcopy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.emrcopy.User.readUser;

public class PatientPovController implements Initializable {
    @FXML
    private TableView<?> table;
    protected static String patientNRIC;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, v_close; // Overview
    @FXML
    private Label overviewNameLabel, profileNameLabel;
    @FXML
    private Label telnoLabel, dobLabel, heightLabel, weightLabel, sexLabel, ageLabel;

    @FXML
    private Hyperlink logout, schedule;
    @FXML
    private TextArea Field;
    @FXML
    private ImageView patientPfpImageView;


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

    private void loadPatient() {
        User patient = readUser(patientNRIC);
        try {
            telnoLabel.setText(patient.getTelno());
            dobLabel.setText(patient.getDob().format(formatter));
            heightLabel.setText(String.valueOf(patient.getHeight()));
            weightLabel.setText(String.valueOf(patient.getWeight()));
            sexLabel.setText(patient.getSex());
            ageLabel.setText(String.valueOf(getAge(patient.getnric())));
            overviewNameLabel.setText(patient.getUsername().toUpperCase() + " (" + patientNRIC.substring(0, 6) + "-"
                    + patientNRIC.substring(6, 8) + "-" + patientNRIC.substring(8, 12) + ")");
            profileNameLabel.setText(patient.getUsername());

        } catch (NullPointerException e) {
            telnoLabel.setText("");
            dobLabel.setText("");
            heightLabel.setText("");
            weightLabel.setText("");
            sexLabel.setText("");
            ageLabel.setText("");
            overviewNameLabel.setText("Unknown");
            profileNameLabel.setText("Unknown");
        }

    }

    public long getAge(String nric) {
        String birthdateString = nric.substring(0, 6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthdate = LocalDate.parse(birthdateString, formatter);

        LocalDate now = LocalDate.now();
        return ChronoUnit.YEARS.between(birthdate, now);
    }


    @FXML
    public void addAttachment() {
        try {
            File file = new File(readUser(patientNRIC).getUsername() + "notes.txt");
            if(!file.exists()){file.createNewFile();}
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            StringBuilder content = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            Field.setText(content.toString());

            bufferedReader.close();
            fileReader.close();

            System.out.println("File read successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image pfp = null;
        try{
            if(readUser(patientNRIC).getSex().equals("Male")){
                pfp = new Image(getClass().getResourceAsStream("img/malepatient.png"));
            }
            else if(readUser(patientNRIC).getSex().equals("Female")){
                pfp = new Image(getClass().getResourceAsStream("img/femalepatient.png"));
            }
        }
        catch(NullPointerException e){
            e.getMessage();
        }
        finally{
            patientPfpImageView.setImage(pfp);

        }
        loadPatient();
        // Set up TableView columns for report
        reportDateColumn.setCellValueFactory(cellData -> cellData.getValue().reportDateProperty());
        reportCodeColumn.setCellValueFactory(cellData -> cellData.getValue().reportCodeProperty());
        reportDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().reportDescriptionProperty());

        // Initialize the reportList
        reportList = FXCollections.observableArrayList();
        reportTable.setItems(reportList);
        loadDataReport();

        // Set up TableView columns for report
        treatStartDateColumn.setCellValueFactory(cellData -> cellData.getValue().treatStartDateProperty());
        treatEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().treatEndDateProperty());
        treatCodeColumn.setCellValueFactory(cellData -> cellData.getValue().treatCodeProperty());
        treatNameColumn.setCellValueFactory(cellData -> cellData.getValue().treatNameProperty());
        DosageColumn.setCellValueFactory(cellData -> cellData.getValue().treatDosageProperty());
        treatDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().treatDescriptionProperty());

        // Initialize the reportList
        treatList = FXCollections.observableArrayList();
        treatTable.setItems(treatList);
        LoadTreatData();

        // Set up the TableView columns for Diagnosis
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().DateProperty());
        DiagnosisCodeColumn.setCellValueFactory(cellData -> cellData.getValue().DiagnosisCodeProperty());
        DiagnosisDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().DiagnosisDescriptionProperty());

        // Initialize the DiagnosisList
        DiagnosisList = FXCollections.observableArrayList();
        diagnosisTable.setItems(DiagnosisList);
        loadDataDiagnosis();

        initializeMedicalTab();
        addAttachment();
    }

    // Table below

    @FXML
    private TableColumn<diagnosis, String> dateColumn;

    @FXML
    private TableColumn<diagnosis, String> DiagnosisCodeColumn;

    @FXML
    private TableColumn<diagnosis, String> DiagnosisDescriptionColumn;

    @FXML
    private TextField DiagnosisCode;

    @FXML
    private DatePicker DiagnosisDatePicker;

    @FXML
    private TextField DiagnosisDescription;

    @FXML
    private TableView<diagnosis> diagnosisTable;

    private ObservableList<diagnosis> DiagnosisList;

    private void loadDataDiagnosis() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(readUser(patientNRIC).getUsername() + "Diagnosis.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {

        }

        // Process the loaded data
        for (String line : data) {
            // Split the line into separate values
            String[] values = line.split(",");
            if (values.length == 3) {
                String date = values[0];
                String code = values[1];
                String description = values[2];
                diagnosis diagnosis = new diagnosis(date, code, description);
                DiagnosisList.add(diagnosis);
            }
        }
    }

    // code for report page

    @FXML
    private TableColumn<report, String> reportDateColumn;

    @FXML
    private TableColumn<report, String> reportCodeColumn;

    @FXML
    private TableColumn<report, String> reportDescriptionColumn;

    @FXML
    private TextField reportCode;

    @FXML
    private DatePicker reportDatePicker;

    @FXML
    private TextField reportDescription;

    @FXML
    private TableView<report> reportTable;

    @FXML
    private ObservableList<report> reportList;

    private void loadDataReport() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(readUser(patientNRIC).getUsername() + "Report.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {

        }

        // Process the loaded data
        for (String line : data) {
            // Split the line into separate values
            String[] values = line.split(",");
            if (values.length == 3) {
                String Rdate = values[0];
                String Rcode = values[1];
                String Rdescription = values[2];
                report report = new report(Rdate, Rcode, Rdescription);
                reportList.add(report);
            }
        }
    }

    // code for treatment page

    @FXML
    private TableColumn<treatment, String> treatStartDateColumn;

    @FXML
    private TableColumn<treatment, String> treatEndDateColumn;

    @FXML
    private TableColumn<treatment, String> treatCodeColumn;

    @FXML
    private TableColumn<treatment, String> treatNameColumn;

    @FXML
    private TableColumn<treatment, String> DosageColumn;

    @FXML
    private TableColumn<treatment, String> treatDescriptionColumn;

    @FXML
    private TextField treatCode;

    @FXML
    private TextField treatDescription;


    @FXML
    private TextField treatName;

    @FXML
    private TextField treatDosage;

    @FXML
    private TableView<treatment> treatTable;

    @FXML
    private ObservableList<treatment> treatList;

    private void LoadTreatData() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(readUser(patientNRIC).getUsername() + "Treatment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {

        }

        // Process the loaded data
        for (String line : data) {
            // Split the line into separate values
            String[] values = line.split(",");
            if (values.length == 6) {
                String StartDate = values[0];
                String EndDate = values[1];
                String tCode = values[2];
                String tName = values[3];
                String tDosage = values[4];
                String tDescription = values[5];
                treatment treat = new treatment(StartDate, EndDate, tCode, tName, tDosage, tDescription);
                treatList.add(treat);
            }
        }
    }

    // Code for medical history page

    @FXML
    private TableColumn<medical, String> mDateColumn;

    @FXML
    private TableColumn<medical, String> mProblemColumn;

    @FXML
    private TableColumn<medical, String> mTreatmentColumn;

    @FXML
    private TableColumn<medical, String> mDescriptionColumn;

    @FXML
    private TextField mProblem;

    @FXML
    private DatePicker mDatePicker;

    @FXML
    private TextField mDescription;

    @FXML
    private TextField mTreatment;

    @FXML
    private TableView<medical> medicalTable;

    @FXML
    private ObservableList<medical> medicalList;

    private void initializeMedicalTab() {
        // Set up TableView columns for report
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().mDateProperty());
        mProblemColumn.setCellValueFactory(cellData -> cellData.getValue().mProblemProperty());
        mTreatmentColumn.setCellValueFactory(cellData -> cellData.getValue().mTreatmentProperty());
        mDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().mDescriptionProperty());

        // Initialize the reportList
        medicalList = FXCollections.observableArrayList();
        medicalTable.setItems(medicalList);
        loadMedicalData();

    }

    private void loadMedicalData() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(readUser(patientNRIC).getUsername() + "MedicalHistory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {

        }

        // Process the loaded data
        for (String line : data) {
            // Split the line into separate values
            String[] values = line.split(",");
            if (values.length == 4) {
                String Date = values[0];
                String Problem = values[1];
                String Treatment = values[2];
                String Description = values[3];
                medical medicalH = new medical(Date, Problem, Treatment, Description);
                medicalList.add(medicalH);
            }
        }
    }



}
