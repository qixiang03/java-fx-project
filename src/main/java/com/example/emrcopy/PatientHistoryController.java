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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javafx.scene.web.HTMLEditor;

import static com.example.emrcopy.User.readUser;
import static com.example.emrcopy.User.updateUser;

public class PatientHistoryController implements Initializable{
    private static boolean editable = false;
    protected static String patientNRIC;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    @FXML
    private TextField ageTextField, heightTextField, weightTextField, dobTextField, sexTextField, telnoTextField;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, v_close, editInfoButton;

    @FXML
    private Label overviewNameNricLabel;

    @FXML
    private Hyperlink dashboard, logout, schedule,profile,setting;
    @FXML
    private ImageView patientPfpImageview;
    @FXML
    private TextArea Field;
    private HTMLEditor htmlEditor;
    @FXML
    private DatePicker startDatePicker, endDatePicker;

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
        Stage stage = (Stage)btn5.getScene().getWindow();
        stage.close();
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
    private void loadPatient(){

        User patient = readUser(patientNRIC);

        try{

            telnoTextField.setText(patient.getTelno());
            dobTextField.setText(patient.getDob().format(formatter));
            heightTextField.setText(String.valueOf(patient.getHeight()));
            weightTextField.setText(String.valueOf(patient.getWeight()));
            sexTextField.setText(patient.getSex());
            ageTextField.setText(String.valueOf(getAge(patient.getnric())));
            overviewNameNricLabel.setText(patient.getUsername().toUpperCase() + " (" + patientNRIC.substring(0,6) + "-" + patientNRIC.substring(6,8) + "-" + patientNRIC.substring(8,12) + ")");
        }
        catch (NullPointerException e){
            telnoTextField.setText("");
            dobTextField.setText("");
            heightTextField.setText("");
            weightTextField.setText("");
            sexTextField.setText("");
            ageTextField.setText("");
            overviewNameNricLabel.setText("Unknown");
        }

    }

    @FXML
    private void editInfoButtonClicked(){
        if(infoValidate()){
        editable = !editable;
        }

        if(editable){
            editInfoButton.setText("Update");
        }
        else{
            if(infoValidate()) {
                String telNo = telnoTextField.getText();
                double height = Double.parseDouble(heightTextField.getText());
                double weight = Double.parseDouble(weightTextField.getText());
                updateUser(readUser(patientNRIC), height, weight, telNo);
                editInfoButton.setText("Edit");



            }
        }
        telnoTextField.setEditable(editable);
        heightTextField.setEditable(editable);
        weightTextField.setEditable(editable);

    }

    public long getAge(String nric){
        String birthdateString = nric.substring(0, 6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthdate = LocalDate.parse(birthdateString, formatter);

        LocalDate now = LocalDate.now();
        return ChronoUnit.YEARS.between(birthdate, now);
    }

    private boolean infoValidate(){
        String telNo = telnoTextField.getText();
        String height = heightTextField.getText();
        String weight = weightTextField.getText();

        if(!validateNumber(height)){
            heightTextField.setStyle("-fx-border-color: red;");
            heightTextField.requestFocus();
            showAlert("Invalid Height!");
            return false;
        }
        else{heightTextField.setStyle("");}

        if(!validateNumber(weight)){
            weightTextField.setStyle("-fx-border-color: red;");
            weightTextField.requestFocus();
            showAlert("Invalid Height!");
            return false;
        }
        else{weightTextField.setStyle("");}


        if (!telNo.startsWith("601") || !(telNo.length() == 11 || telNo.length() == 12)) {
            telnoTextField.setStyle("-fx-border-color: red;");
            telnoTextField.requestFocus();
            showAlert("Invalid Phone Format!");
            return false;
        }
        else{
            telnoTextField.setStyle("");
        }


        return true;

    }

    public static boolean validateNumber(String height) {
        String regex = "^[0-9.]+$"; // Regular expression to match digits and a dot
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(height);

        return matcher.matches();
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid!");
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPatient();
        Image pfp = null;

        try{
            ObservableList<String> list = FXCollections.observableArrayList("E08","E09","E10", "E11","E13");
            diagnosisComboBox.setItems(list);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

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
            patientPfpImageview.setImage(pfp);

        }


        Map<String, String> diagnosisDescriptions = new HashMap<>();

        diagnosisDescriptions.put("E08", "Secondary Diabetes");
        diagnosisDescriptions.put("E09", "Secondary Diabetes");
        diagnosisDescriptions.put("E10", "Type 1 Diabetes Mellitus (T1DM)");
        diagnosisDescriptions.put("E11", "Type 2 Diabetes Mellitus (T2DM)");
        diagnosisDescriptions.put("E13", "Other Specified Diabetes Mellitus Including Secondary Diabetes");

        telnoTextField.setEditable(editable);
        heightTextField.setEditable(editable);
        weightTextField.setEditable(editable);

        diagnosisComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Look up the description for the selected item in the map
            String description = diagnosisDescriptions.get(newValue);
            // Update the text field with the description
            DiagnosisDescription.setText(description);
        });

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
    private DatePicker DiagnosisDatePicker;

    @FXML
    private TextField DiagnosisDescription;

    @FXML
    private TableView<diagnosis> diagnosisTable;

    private ObservableList<diagnosis> DiagnosisList;
    @FXML
    private ComboBox<String> diagnosisComboBox;

    // actions in Diagnosis Code
    @FXML
    private void DiagnosisAddButtonClicked() {
        String date = String.valueOf(DiagnosisDatePicker.getValue());
        String Code = diagnosisComboBox.getValue();
        String Description = DiagnosisDescription.getText();

        if (DiagnosisDatePicker.getValue() == null || Code.isEmpty() || Description.isEmpty()) {
            showInformationAlert("Error", "Please enter all fields");
            return;
        }

        diagnosis diagnosis = new diagnosis(date, Code, Description);
        DiagnosisList.add(diagnosis);

        // Clear input fields
        DiagnosisDatePicker.setValue(null);
        diagnosisComboBox.setValue(null);
        DiagnosisDescription.clear();

        saveDataDiagnosis();
    }

    private void saveDataDiagnosis() {
        BufferedWriter writer = null;
        try {
            File filePath = new File(readUser(patientNRIC).getUsername()+ "Diagnosis.txt");
            if(!filePath.exists()){filePath.createNewFile();}
            writer = new BufferedWriter(new FileWriter(filePath));
            for (diagnosis diagnosis : DiagnosisList) {
                writer.write(diagnosis.getDate() + "," + diagnosis.getDiagnosisCode() + ","
                        + diagnosis.getDiagnosisDescription());
                writer.newLine();
            }
            showInformationAlert("Success", "Data saved to file");
        } catch (IOException e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }
    }

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




    @FXML
    private void DiagnosisEditButtonClicked() {
        diagnosis selectedDiagnosis = diagnosisTable.getSelectionModel().getSelectedItem();
        if (selectedDiagnosis != null) {
            // Open a dialog or form to edit the selected diagnosis
            Dialog<diagnosis> editDialog = new Dialog<>();
            editDialog.setTitle("Edit Diagnosis");

            // Set the dialog buttons (e.g., OK and Cancel)
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            editDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Create a form or content for editing the diagnosis
            TextField dateField = new TextField(selectedDiagnosis.getDate());
            TextField codeField = new TextField(selectedDiagnosis.getDiagnosisCode());
            TextField descriptionField = new TextField(selectedDiagnosis.getDiagnosisDescription());

            // Set the dialog content with the form
            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Date:"), 0, 0);
            gridPane.add(dateField, 1, 0);
            gridPane.add(new Label("Diagnosis Code:"), 0, 1);
            gridPane.add(codeField, 1, 1);
            gridPane.add(new Label("Description:"), 0, 2);
            gridPane.add(descriptionField, 1, 2);
            editDialog.getDialogPane().setContent(gridPane);

            // Convert the result of the dialog button press to a diagnosis object
            editDialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String editedDate = dateField.getText();
                    String editedCode = codeField.getText();
                    String editedDescription = descriptionField.getText();
                    // Create and return the edited diagnosis object
                    return new diagnosis(editedDate, editedCode, editedDescription);
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<diagnosis> editedDiagnosis = editDialog.showAndWait();
            editedDiagnosis.ifPresent(diagnosis -> {
                // Update the selected diagnosis with the edited values
                selectedDiagnosis.setDate(diagnosis.getDate());
                selectedDiagnosis.setDiagnosisCode(diagnosis.getDiagnosisCode());
                selectedDiagnosis.setDiagnosisDescription(diagnosis.getDiagnosisDescription());
                // Refresh the table view to reflect the changes
                diagnosisTable.refresh();
                // Save the updated data to the file or storage
                saveDataDiagnosis();
            });
        } else {
            showInformationAlert("Error", "No diagnosis selected");
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

    // Action in report page
    @FXML
    private void reportAddButtonClicked() {
        String Rdate = String.valueOf(reportDatePicker.getValue());
        String RCode = reportCode.getText();
        String RDescription = reportDescription.getText();

        if (reportDatePicker.getValue() == null || RCode.isEmpty() || RDescription.isEmpty()) {
            showInformationAlert("Error", "Please enter all fields");
            return;
        }

        report report = new report(Rdate, RCode, RDescription);
        reportList.add(report);

        // Clear input fields
        reportDatePicker.setValue(null);
        reportCode.clear();
        reportDescription.clear();

        saveDataReport();
    }

    private void saveDataReport() {
        BufferedWriter writer = null;
        try {
            File filePath = new File(readUser(patientNRIC).getUsername() + "Report.txt");
            if(!filePath.exists()){filePath.createNewFile();}
            writer = new BufferedWriter(new FileWriter(filePath));
            for (report report : reportList) {
                writer.write(
                        report.getReportDate() + "," + report.getReportCode() + "," + report.getReportDescription());
                writer.newLine();
            }
            showInformationAlert("Success", "Data saved to file");
        } catch (IOException e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }
    }

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

    @FXML
    private void reportEditButtonClicked() {
        report selectedReport = reportTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Open a dialog or form to edit the selected diagnosis
            Dialog<report> editDialog = new Dialog<>();
            editDialog.setTitle("Edit Report");

            // Set the dialog buttons (e.g., OK and Cancel)
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            editDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Create a form or content for editing the report
            TextField rDate = new TextField(selectedReport.getReportDate());
            TextField rCode = new TextField(selectedReport.getReportCode());
            TextField rDescription = new TextField(selectedReport.getReportDescription());

            // Set the dialog content with the form
            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Date:"), 0, 0);
            gridPane.add(rDate, 1, 0);
            gridPane.add(new Label("Report Code:"), 0, 1);
            gridPane.add(rCode, 1, 1);
            gridPane.add(new Label("Description:"), 0, 2);
            gridPane.add(rDescription, 1, 2);
            editDialog.getDialogPane().setContent(gridPane);

            // Convert the result of the dialog button press to a diagnosis object
            editDialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String editedDate = rDate.getText();
                    String editedCode = rCode.getText();
                    String editedDescription = rDescription.getText();
                    // Create and return the edited report object
                    return new report(editedDate, editedCode, editedDescription);
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<report> editedReport = editDialog.showAndWait();
            editedReport.ifPresent(report -> {
                // Update the selected report with the edited values
                selectedReport.setReportDate(report.getReportDate());
                selectedReport.setReportCode(report.getReportCode());
                selectedReport.setReportDescription(report.getReportDescription());
                // Refresh the table view to reflect the changes
                reportTable.refresh();
                // Save the updated data to the file or storage
                saveDataReport();
            });
        } else {
            showInformationAlert("Error", "No report selected");
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

    // initialize for treatment tab

    // Action in treatment page
    @FXML
    private void addTreatBtnAction() {
        String startDate = String.valueOf(startDatePicker.getValue());
        String endDate = String.valueOf(endDatePicker.getValue());
        String tCode = treatCode.getText();
        String tName = treatName.getText();
        String tDosage = treatDosage.getText();
        String tDescription = treatDescription.getText();

        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || tCode.isEmpty() || tName.isEmpty() || tDosage.isEmpty()
                || tDescription.isEmpty()) {
            showInformationAlert("Error", "Please fill in everything!");
            return;
        }

        treatment treat = new treatment(startDate, endDate, tCode, tName, tDosage, tDescription);
        treatList.add(treat);

        // clear all the textfield
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        treatCode.clear();
        treatName.clear();
        treatDosage.clear();
        treatDescription.clear();

        SaveTreatData();

    }

    private void SaveTreatData() {
        BufferedWriter writer = null;
        try {
            File filepath = new File(readUser(patientNRIC).getUsername()+ "Treatment.txt");
            if(!filepath.exists()){filepath.createNewFile();}
            writer = new BufferedWriter(new FileWriter(filepath));
            for (treatment treat : treatList) {
                writer.write(treat.getTreatStartDate() + "," + treat.getTreatEndDate() + "," + treat.getTreatCode()
                        + "," + treat.getTreatName() + "," + treat.getTreatDosage() + ","
                        + treat.getTreatDescription());
                writer.newLine();
            }
            showInformationAlert("Success", "Data has been saved!");

        } catch (IOException e) {
            showInformationAlert("Error", "Data could not be saved!");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }
    }

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

    @FXML
    private void TreatEditBtnClicked() {
        treatment selectedTreat = treatTable.getSelectionModel().getSelectedItem();
        if (selectedTreat != null) {
            // Open a dialog or form to edit the selected diagnosis
            Dialog<treatment> editDialog = new Dialog<>();
            editDialog.setTitle("Edit Treatment");

            // Set the dialog buttons (e.g., OK and Cancel)
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            editDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Create a form or content for editing the diagnosis
            TextField StartDate = new TextField(selectedTreat.getTreatStartDate());
            TextField EndDate = new TextField(selectedTreat.getTreatEndDate());
            TextField tCode = new TextField(selectedTreat.getTreatCode());
            TextField tName = new TextField(selectedTreat.getTreatName());
            TextField tDosage = new TextField(selectedTreat.getTreatDosage());
            TextField tDescription = new TextField(selectedTreat.getTreatDescription());

            // Set the dialog content with the form
            GridPane gridPane = new GridPane();
            gridPane.add(new Label(" Start Date:"), 0, 0);
            gridPane.add(StartDate, 1, 0);
            gridPane.add(new Label(" End Date:"), 0, 1);
            gridPane.add(EndDate, 1, 1);
            gridPane.add(new Label("Treatment Code:"), 0, 2);
            gridPane.add(tCode, 1, 2);
            gridPane.add(new Label(" Treatment Name:"), 0, 3);
            gridPane.add(tName, 1, 3);
            gridPane.add(new Label(" Dosage :"), 0, 4);
            gridPane.add(tDosage, 1, 4);
            gridPane.add(new Label("Description:"), 0, 5);
            gridPane.add(tDescription, 1, 5);
            editDialog.getDialogPane().setContent(gridPane);

            // Convert the result of the dialog button press to a diagnosis object
            editDialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String editedStartDate = StartDate.getText();
                    String editedEndDate = EndDate.getText();
                    String editedCode = tCode.getText();
                    String editedName = tName.getText();
                    String editedDosage = tDosage.getText();
                    String editedDescription = tDescription.getText();
                    // Create and return the edited report object
                    return new treatment(editedStartDate, editedEndDate, editedCode, editedName, editedDosage,
                            editedDescription);
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<treatment> editedTreat = editDialog.showAndWait();
            editedTreat.ifPresent(treatment -> {
                // Update the selected diagnosis with the edited values
                selectedTreat.setTreatStartDate(treatment.getTreatStartDate());
                selectedTreat.setTreatEndDate(treatment.getTreatEndDate());
                selectedTreat.setTreatCode(treatment.getTreatCode());
                selectedTreat.setTreatName(treatment.getTreatName());
                selectedTreat.setTreatDosage(treatment.getTreatDosage());
                selectedTreat.setTreatDescription(treatment.getTreatDescription());
                // Refresh the table view to reflect the changes
                treatTable.refresh();
                // Save the updated data to the file or storage
                SaveTreatData();
            });
        } else {
            showInformationAlert("Error", "No treatment selected");
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

    // Action in medical history page
    @FXML
    private void mAddBtnClicked() {
        String Date = String.valueOf(mDatePicker.getValue());
        String Problem = mProblem.getText();
        String Treatment = mTreatment.getText();
        String Description = mDescription.getText();

        if (mDatePicker.getValue() == null || Problem.isEmpty() || Treatment.isEmpty() || Description.isEmpty()) {
            showInformationAlert("Error", "Please enter all fields");
            return;
        }

        medical medicalH = new medical(Date, Problem, Treatment, Description);
        medicalList.add(medicalH);

        // Clear input fields
        mDatePicker.setValue(null);
        mProblem.clear();
        mTreatment.clear();
        mDescription.clear();

        saveMedicalData();
    }

    @FXML
    private void mEditBtnClicked() {
        medical selectedMedical = medicalTable.getSelectionModel().getSelectedItem();
        if (selectedMedical != null) {
            // Open a dialog or form to edit the selected diagnosis
            Dialog<medical> editDialog = new Dialog<>();
            editDialog.setTitle("Edit Medical History");

            // Set the dialog buttons (e.g., OK and Cancel)
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            editDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Create a form or content for editing the report
            TextField medicalDate = new TextField(selectedMedical.getMDate());
            TextField medicalProblem = new TextField(selectedMedical.getMProblem());
            TextField medicalTreatment = new TextField(selectedMedical.getMTreatment());
            TextField medicalDescription = new TextField(selectedMedical.getMDescription());

            // Set the dialog content with the form
            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Date :"), 0, 0);
            gridPane.add(medicalDate, 1, 0);
            gridPane.add(new Label("Problem :"), 0, 1);
            gridPane.add(medicalProblem, 1, 1);
            gridPane.add(new Label("Treatment :"), 0, 2);
            gridPane.add(medicalTreatment, 1, 2);
            gridPane.add(new Label("Description :"), 0, 3);
            gridPane.add(medicalDescription, 1, 3);
            editDialog.getDialogPane().setContent(gridPane);

            // Convert the result of the dialog button press to a diagnosis object
            editDialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String editedDate = medicalDate.getText();
                    String editedProblem = medicalProblem.getText();
                    String editedTreatment = medicalTreatment.getText();
                    String editedDescription = medicalDescription.getText();
                    // Create and return the edited report object
                    return new medical(editedDate, editedProblem, editedTreatment, editedDescription);
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<medical> editedMedical = editDialog.showAndWait();
            editedMedical.ifPresent(medical -> {
                // Update the selected report with the edited values
                selectedMedical.setMDate(medical.getMDate());
                selectedMedical.setMProblem(medical.getMProblem());
                selectedMedical.setMProblem(medical.getMTreatment());
                selectedMedical.setMDescription(medical.getMDescription());
                // Refresh the table view to reflect the changes
                medicalTable.refresh();
                // Save the updated data to the file or storage
                saveMedicalData();
            });
        } else {
            showInformationAlert("Error", "No report selected");
        }
    }

    private void saveMedicalData() {
        BufferedWriter writer = null;
        try {
            File filePath = new File(readUser(patientNRIC).getUsername() + "MedicalHistory.txt");
            if(!filePath.exists()){filePath.createNewFile();}
            writer = new BufferedWriter(new FileWriter(filePath));
            for (medical medicalH : medicalList) {
                writer.write(medicalH.getMDate() + "," + medicalH.getMProblem() + "," + medicalH.getMTreatment() + ","
                        + medicalH.getMDescription());
                writer.newLine();
            }
            showInformationAlert("Success", "Data saved to file");
        } catch (IOException e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }
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

    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

      @FXML
    public void addAttachment() {
        try {
            File file = new File(readUser(patientNRIC).getUsername()+"notes.txt");
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


    @FXML
    public void saveAttachment(ActionEvent event) {
         String content = Field.getText();

        try {
            File file = new File(readUser(patientNRIC).getUsername()+"notes.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Text written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



