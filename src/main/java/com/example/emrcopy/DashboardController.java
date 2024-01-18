package com.example.emrcopy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;
import javafx.stage.Stage;

public class DashboardController implements Initializable {
    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year, month;

    @FXML
    private FlowPane calendar;

    @FXML
    private Button btn1, btn2, btn3, btn4, v_close; // Overview

    @FXML
    private Hyperlink dashboard, logout, schedule, setting, profile;

    // method to open doc's dashboard pg
    public void open_pov() throws IOException {
        Stage stage = (Stage) btn1.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Dashboard.fxml"));
        primaryStage.setTitle("Doctor's Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // method to open doc's schedule pg
    public void open_schedule() throws IOException {
        Stage stage = (Stage) btn2.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Schedule.fxml"));
        primaryStage.setTitle("Doctor's Schedule");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // method to open patient's record pg
    public void open_record() throws IOException {
        Stage stage = (Stage) btn3.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/PatientRecord.fxml"));
        primaryStage.setTitle("Patient's Record");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // method to open patient's lab pg
    public void open_lab() throws IOException {
        Stage stage = (Stage) btn4.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/PatientHistory.fxml"));
        primaryStage.setTitle("Patient's Lab");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // List of activities for a given month
        Map<Integer, List<CalendarActivityDashboard>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        // Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime
                .of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek()
                .getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 8) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 7) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 3.0) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                    }

                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth()
                            && today.getDayOfMonth() == currentDate) {
                        rectangle.setFill(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }

    }

    private Map<Integer, List<CalendarActivityDashboard>> createCalendarMap(
            List<CalendarActivityDashboard> calendarActivities) {
        Map<Integer, List<CalendarActivityDashboard>> calendarActivityMap = new HashMap<>();

        for (CalendarActivityDashboard activity : calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if (!calendarActivityMap.containsKey(activityDate)) {
                calendarActivityMap.put(activityDate, Arrays.asList(activity));
            } else {
                List<CalendarActivityDashboard> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivityDashboard> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivityDashboard>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivityDashboard> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        return createCalendarMap(calendarActivities);
    }
}
