package com.bootcamp.demo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalHistory {
    private String email;
    private String scooterDocumentName;
    private Date startDate;
    private Date endDate;
    private BigDecimal tripCost;

    public RentalHistory() {
    }

    public RentalHistory(String email, String scooterDocumentName, Date startDate, Date endDate, BigDecimal tripCost) {
        this.email = email;
        this.scooterDocumentName = scooterDocumentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripCost = tripCost;
    }

    public String getStartDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return formatter.format(startDate);
    }

    public String getEndDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return formatter.format(endDate);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTripCost() {
        return tripCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTripCost(BigDecimal tripCost) {
        this.tripCost = tripCost;
    }

    @Override
    public String toString() {
        return "RentalHistory{" +
                "email='" + email + '\'' +
                ", scooterDocumentName='" + scooterDocumentName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", tripCost=" + tripCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
