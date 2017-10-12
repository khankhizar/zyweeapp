package com.infovita.zywee.Supports;

import android.widget.Button;

/**
 * Created by Khizarkhan on 03-02-2017.
 */

public class PrescriptionCart {
    public String prescriptionImageUriString;
    private Button modifyButton, removeButton;

    public PrescriptionCart(){}


    public int row_id;

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getPrescriptionImage() {
        return prescriptionImageUriString;
    }


    public void setPrescriptionImage(String prescriptionImageUriString) {
        this.prescriptionImageUriString = prescriptionImageUriString;
    }
}
