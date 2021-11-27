package com.bootcamp.demo.model;

import com.bootcamp.demo.validation.ProductionYearConstraint;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Scooter {
    @NotBlank(message = "Document name can't be empty!")
    @Pattern(regexp = "(^ *$|^[0-9a-zA-Z]*$)", message = "Document name can contain only numbers and letters!")
    private String documentName;
    @NotBlank(message = "Serial number can't be empty!")
    @Pattern(regexp = "(^ *$|^[0-9a-zA-Z]*$)", message = "Serial number can contain only numbers and letters!")
    private String serialNumber;
    @NotBlank(message = "Brand name can't be empty!")
    @Pattern(regexp = "(^ *$|^[0-9a-zA-Z]*$)", message = "Brand name can contain only numbers and letters!")
    private String brand;
    @DecimalMin(value = "0.0000001", message = "Cost can't be less than zero!")
    private BigDecimal cost;
    @Min(value = 1996, message = "Production year can't be earlier than 1996!")
    @ProductionYearConstraint(message = "Production year cant be greater than the current year!")
    private int prodYear;
    @DecimalMin(value = "0.0000001", message = "Weight can't be less than zero!")
    private double weight;
    private State state;

    public Scooter() {
        // firestore requires a constructor without parameters
    }

    public Scooter(String documentName, String serialNumber, String brand, String cost, int prodYear, double weight, State state) {
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scooter scooter = (Scooter) o;
        return prodYear == scooter.prodYear && Double.compare(scooter.weight, weight) == 0 && Objects.equals(documentName, scooter.documentName) && Objects.equals(serialNumber, scooter.serialNumber) && Objects.equals(brand, scooter.brand) && Objects.equals(cost, scooter.cost) && state == scooter.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentName, serialNumber, brand, cost, prodYear, weight, state);
    }
}
