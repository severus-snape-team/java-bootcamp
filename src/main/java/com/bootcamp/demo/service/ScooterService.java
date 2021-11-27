package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.ScooterRepository;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;

    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }

    public void insertScooter(Scooter scooter) {
        this.scooterRepository.insertScooter(scooter);
    }

    public String updateScooter(String documentName, String fieldName, String newValue) {
        return this.scooterRepository.updateScooter(documentName, fieldName, newValue);
    }

    public String deleteScooter(String documentName) {
        return this.scooterRepository.deleteScooter(documentName);
    }

    public Set<String> getAllPaths() {
        return this.scooterRepository.getAllPaths();
    }

    public List<Scooter> returnAllScooters() throws ExecutionException, InterruptedException {
        return this.scooterRepository.readScooters();
    }

}
