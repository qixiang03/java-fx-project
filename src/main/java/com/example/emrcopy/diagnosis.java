/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.emrcopy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class diagnosis {

    //Done
    private final StringProperty date;
    private final StringProperty diagnosisCode;
    private final StringProperty diagnosisDescription;

    public diagnosis(String date, String diagnosisCode, String diagnosisDescription) {
        this.date = new SimpleStringProperty(date);
        this.diagnosisCode = new SimpleStringProperty(diagnosisCode);
        this.diagnosisDescription = new SimpleStringProperty(diagnosisDescription);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty DateProperty() {
        return date;
    }

    public String getDiagnosisCode() {
        return diagnosisCode.get();
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode.set(diagnosisCode);
    }

    public StringProperty DiagnosisCodeProperty() {
        return diagnosisCode;
    }

    public String getDiagnosisDescription() {
        return diagnosisDescription.get();
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription.set(diagnosisDescription);
    }

    public StringProperty DiagnosisDescriptionProperty() {
        return diagnosisDescription;
    }
}



