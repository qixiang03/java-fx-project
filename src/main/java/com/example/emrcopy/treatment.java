/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.emrcopy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class treatment {
    //Done
    private final StringProperty treatStartDate;
    private final StringProperty treatEndDate;
    private final StringProperty treatCode;
    private final StringProperty treatName;
    private final StringProperty treatDosage;
    private final StringProperty treatDescription;

    public treatment(String treatStartDate, String treatEndDate, String treatCode, String treatName, String treatDosage,String treatDescription){
        this.treatStartDate = new SimpleStringProperty(treatStartDate);
        this.treatCode = new SimpleStringProperty(treatCode);
        this.treatEndDate = new SimpleStringProperty(treatEndDate);
        this.treatName = new SimpleStringProperty(treatName);
        this.treatDosage = new SimpleStringProperty(treatDosage);
        this.treatDescription = new SimpleStringProperty(treatDescription);
    }

    public String getTreatStartDate() {
        return treatStartDate.get();
    }

    public void setTreatStartDate(String treatStartDate) {
        this.treatStartDate.set(treatStartDate);
    }

    public StringProperty treatStartDateProperty() {
        return treatStartDate;
    }

    public String getTreatEndDate() {
        return treatEndDate.get();
    }

    public void setTreatEndDate(String treatEndDate) {
        this.treatEndDate.set(treatEndDate);
    }

    public StringProperty treatEndDateProperty() {
        return treatEndDate;
    }

    public String getTreatCode() {
        return treatCode.get();
    }

    public void setTreatCode(String treatCode) {
        this.treatCode.set(treatCode);
    }

    public StringProperty treatCodeProperty() {
        return treatCode;
    }

    public String getTreatName() {
        return treatName.get();
    }

    public void setTreatName(String treatName) {
        this.treatName.set(treatName);
    }

    public StringProperty treatNameProperty() {
        return treatName;
    }

    public String getTreatDosage() {
        return treatDosage.get();
    }

    public void setTreatDosage(String treatDosage) {
        this.treatDosage.set(treatDosage);
    }

    public StringProperty treatDosageProperty() {
        return treatDosage;
    }

    public String getTreatDescription() {
        return treatDescription.get();
    }

    public void setTreatDescription(String treatDescription) {
        this.treatDescription.set(treatDescription);
    }

    public StringProperty treatDescriptionProperty() {
        return treatDescription;
    }
}



