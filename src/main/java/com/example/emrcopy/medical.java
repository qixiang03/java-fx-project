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
class medical {
    private final StringProperty mDate;
    private final StringProperty mProblem;
    private final StringProperty mTreatment;
    private final StringProperty mDescription;
    //Done
    public medical(String mDate, String mProblem, String mTreatment, String mDescription) {
        this.mDate = new SimpleStringProperty(mDate);
        this.mProblem = new SimpleStringProperty(mProblem);
        this.mTreatment = new SimpleStringProperty(mTreatment);
        this.mDescription = new SimpleStringProperty(mDescription);
    }

    public String getMDate() {
        return mDate.get();
    }

    public void setMDate(String mDate) {
        this.mDate.set(mDate);
    }

    public StringProperty mDateProperty() {
        return mDate;
    }

    public String getMProblem() {
        return mProblem.get();
    }

    public void setMProblem(String mProblem) {
        this.mProblem.set(mProblem);
    }

    public StringProperty mProblemProperty() {
        return mProblem;
    }

    public String getMTreatment() {
        return mTreatment.get();
    }

    public void setMTreatment(String mTreatment) {
        this.mTreatment.set(mTreatment);
    }

    public StringProperty mTreatmentProperty() {
        return mTreatment;
    }

    public String getMDescription() {
        return mDescription.get();
    }

    public void setMDescription(String reportDescription) {
        this.mDescription.set(reportDescription);
    }

    public StringProperty mDescriptionProperty() {
        return mDescription;
    }
}
