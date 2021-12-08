package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.ScooterRental;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.RentalRepository;
import com.bootcamp.demo.repository.ScooterRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

@Service
public class RentalService {
    private final ScooterRepository scooterRepository;
    private final RentalRepository rentalRepository;

    public RentalService(ScooterRepository scooterRepository, RentalRepository rentalRepository) {
        this.scooterRepository = scooterRepository;
        this.rentalRepository = rentalRepository;
    }

    public void saveRental(String scooterDocumentName) {
        Scooter scooter = this.scooterRepository.getScooterByName(scooterDocumentName);
        if (!scooter.getState().equals(State.OUT_OF_USE)) {
            throw new RuntimeException("Scooter is not available for renting!");
        }
        ScooterRental scooterRental = new ScooterRental(scooterDocumentName);
        rentalRepository.saveRental(scooterRental);
        scooterRepository.updateScooter(scooterDocumentName, "state", "IN_USE");
    }

    public void deleteRental(String scooterDocumentName) {
        Scooter scooter = this.scooterRepository.getScooterByName(scooterDocumentName);
        if (!scooter.getState().equals(State.IN_USE)) {
            throw new RuntimeException("Scooter is not currently rented!");
        }
        ScooterRental scooterRental = this.rentalRepository.getRentalByScooterDocName(scooterDocumentName);
        rentalRepository.deleteRental(scooterRental);
        scooterRepository.updateScooter(scooterDocumentName, "state", "OUT_OF_USE");
    }

    public ScooterRental getRentalByName(String scooterDocumentName) {
        return this.rentalRepository.getRentalByScooterDocName(scooterDocumentName);
    }

    public boolean isScooterAvailable(String scooterDocumentName) {
        return this.scooterRepository.getScooterByName(scooterDocumentName).getState().equals(State.OUT_OF_USE);
    }

    public long getTimeRental(String scooterDocumentName) {
        ScooterRental scooterRental = rentalRepository.getRentalByScooterDocName(scooterDocumentName);
        Date startDate = scooterRental.getStartDate();
        Date endDate = new Date();
        return (endDate.getTime() - startDate.getTime()) / (1000);
    }

    public BigDecimal getRentalCost(String scooterDocumentName) {
        return BigDecimal.valueOf(getTimeRental(scooterDocumentName) * 0.05).round(new MathContext(4));
    }


}
