/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.emrcopy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author user
 */
public class report {
    private final StringProperty reportDate;
    private final StringProperty reportCode;
    private final StringProperty reportDescription;
    //Done
    public report(String reportDate, String reportCode, String reportDescription) {
        this.reportDate = new SimpleStringProperty(reportDate);
        this.reportCode = new SimpleStringProperty(reportCode);
        this.reportDescription = new SimpleStringProperty(reportDescription);
    }

    public String getReportDate() {
        return reportDate.get();
    }

    public void setReportDate(String reportDate) {
        this.reportDate.set(reportDate);
    }

    public StringProperty reportDateProperty() {
        return reportDate;
    }

    public String getReportCode() {
        return reportCode.get();
    }

    public void setReportCode(String reportCode) {
        this.reportCode.set(reportCode);
    }

    public StringProperty reportCodeProperty() {
        return reportCode;
    }

    public String getReportDescription() {
        return reportDescription.get();
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription.set(reportDescription);
    }

    public StringProperty reportDescriptionProperty() {
        return reportDescription;
    }
}



