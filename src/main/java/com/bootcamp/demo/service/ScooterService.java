package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.ScooterRepository;
import com.google.cloud.firestore.DocumentSnapshot;
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

    public boolean scooterValidator(Scooter scooter){
        // ----------- VALOAREA DEFAULT LA STRING-URI IN FIRESTORE ESTE "" NU null   --------------- ///
        boolean docNameValidator = !scooter.getDocumentName().equals("");
        // numai litere mici, mari si numere => regular expression
        boolean serialNumberValidator = scooter.getSerialNumber().matches("^[0-9a-zA-Z]*$") && !scooter.getSerialNumber().equals("");
        boolean brandValidator = !scooter.getBrand().equals("");
        boolean costValidator = scooter.getCost().intValue() > 0; // daca costul este mai mare decat 0
        boolean prodYearValidator = scooter.getProdYear() > 2000; // asta daca vrem sa avem scootere mai noi de anul 2000
        boolean weightValidator = scooter.getWeight() > 0.0; // stiu ca nu e bine ca e pe double, dar deocamdata las asa
        for (State s : State.values()) {   // daca valoarea introdusa la state este in enum atunci o sa fie true deci returnez SI intre ceilalti validatori
            if (s.equals(scooter.getState())) {
                return docNameValidator && serialNumberValidator && brandValidator && costValidator && prodYearValidator && weightValidator;
            }
        }
        return false;
    }

    public void insertScooter(Scooter scooter) {
        if(scooterValidator(scooter)) {
            this.scooterRepository.insertScooter(scooter);
        } else {
            throw new InputMismatchException();
        }
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


    public Scooter getScooterByName(String scooterName) {
        DocumentSnapshot document = scooterRepository.getScooterByName(scooterName);

        if(document.exists()) {
            return document.toObject(Scooter.class);
        }else {
            return null;
        }
    }
}
