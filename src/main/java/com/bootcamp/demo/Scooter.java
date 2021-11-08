package com.bootcamp.demo;


import java.util.Objects;

public class Scooter {
    private int serialNumber;
    private String brandName;
    private double acquisitionCost;
    private int productionYear;
    private double weight;
    private State state;

    public Scooter(int serialNumber, String brandName, double acquisitionCost, int productionYear, double weight, State state) {
        this.serialNumber = serialNumber;
        this.brandName = brandName;
        this.acquisitionCost = acquisitionCost;
        this.productionYear = productionYear;
        this.weight = weight;
        this.state = state;
    }
    public int getSerialNumber() {
        return serialNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public double getAcquisitionCost() {
        return acquisitionCost;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public double getWeight() {
        return weight;
    }

    public State getState() {
        return state;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setAcquisitionCost(double acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "serialNumber=" + serialNumber +
                ", brandName='" + brandName + '\'' +
                ", acquisitionCost=" + acquisitionCost +
                ", productionYear=" + productionYear +
                ", weight=" + weight +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scooter scooter = (Scooter) o;
        return serialNumber == scooter.serialNumber && Double.compare(scooter.acquisitionCost, acquisitionCost) == 0 && productionYear == scooter.productionYear && Double.compare(scooter.weight, weight) == 0 && Objects.equals(brandName, scooter.brandName) && state == scooter.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, brandName, acquisitionCost, productionYear, weight, state);
    }
}
