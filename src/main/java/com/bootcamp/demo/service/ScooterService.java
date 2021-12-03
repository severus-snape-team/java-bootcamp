package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.ScooterRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    public List<Scooter> returnAvailableScooters() throws ExecutionException, InterruptedException {
        return this.scooterRepository.readScooters().stream().filter(s -> s.getState().equals(State.OUT_OF_USE)).collect(Collectors.toList());
    }

    public Scooter getScooterByName(String scooterName) {
        return this.scooterRepository.getScooterByName(scooterName);
    }

    public List<Long> returnNumberStates() throws ExecutionException, InterruptedException {
        long inUse = this.scooterRepository.readScooters().stream().filter(s -> s.getState().equals(State.IN_USE)).count();
        long inService = this.scooterRepository.readScooters().stream().filter(s -> s.getState().equals(State.IN_SERVICE)).count();
        long broken = this.scooterRepository.readScooters().stream().filter(s -> s.getState().equals(State.BROKEN)).count();
        long outOfUse = this.scooterRepository.readScooters().stream().filter(s -> s.getState().equals(State.OUT_OF_USE)).count();
        return List.of(inUse, inService, broken, outOfUse);
    }
}
