package com.bootcamp.demo.Model;

import com.bootcamp.demo.FirebaseController;
import com.google.cloud.firestore.Firestore;

public class Scooter {
    private String documentName;
    private String serialNumber;
    private String brand;
    private double cost;
    private int prodYear;
    private double weight;
    private String state;

    public Scooter() {
    }

    public Scooter(String documentName, String serialNumber, String brand, double cost, int prodYear, double weight, String state) {
        this.documentName = documentName;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.cost = cost;
        this.prodYear = prodYear;
        this.weight = weight;
        this.state = state;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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
