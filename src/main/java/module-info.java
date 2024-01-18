module com.example.emrcopy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires jdk.jdeps;

    opens com.example.emrcopy to javafx.fxml;
    exports com.example.emrcopy;
}