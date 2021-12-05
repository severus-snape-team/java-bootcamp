package com.bootcamp.demo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScooterRental {
    private String scooterDocumentName;
    private Date startDate;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ScooterRental(String scooterDocumentName) {
        this.scooterDocumentName = scooterDocumentName;
        startDate = new Date();
    }

    public ScooterRental(String scooterDocumentName, Date date) {
        this.scooterDocumentName = scooterDocumentName;
        this.startDate = date;
    }

    public String getScooterDocumentName() {
        return scooterDocumentName;
    }

    public void setScooterDocumentName(String scooterDocumentName) {
        this.scooterDocumentName = scooterDocumentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Scooter document name: " + this.scooterDocumentName + "\nStart renting at: " + formatter.format(startDate.getTime());
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }
}
