package com.bootcamp.demo.model;

import java.math.BigDecimal;

public class Scooter {
    private String documentName;
    private String serialNumber;
    private String brand;
    private BigDecimal cost;
    private int prodYear;
    private double weight;
    private String state;

    public Scooter() {
        // firestore requires a constructor without parameters
    }

    public Scooter(String documentName, String serialNumber, String brand, String cost, int prodYear, double weight, String state) {
        this.documentName = documentName;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.cost = new BigDecimal(cost);
        this.prodYear = prodYear;
        this.weight = weight;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Document " + this.documentName + " was created";
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getProdYear() {
        return prodYear;
    }

    public void setProdYear(int prodYear) {
        this.prodYear = prodYear;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
