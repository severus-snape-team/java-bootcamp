package com.bootcamp.demo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Repair {

    private String id;
    private String scooterDoc;
    private Date date;
    private String description;
    private float totalCost;

    public Repair() {
    }

    public Repair(Date date, String description, float totalCost) {
        this.date = date;
        this.description = description;
        this.totalCost = totalCost;
    }


    public String getDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getScooterDoc() {
        return scooterDoc;
    }

    public void setScooterDoc(String scooterDoc) {
        this.scooterDoc = scooterDoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
