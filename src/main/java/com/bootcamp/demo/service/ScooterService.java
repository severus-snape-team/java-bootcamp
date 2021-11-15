package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.ScooterRepository;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;

    @Autowired
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

    public ArrayList<Scooter> returnAllScooters() {
        List<QueryDocumentSnapshot> returned = this.scooterRepository.readScooters();
        ArrayList<Scooter> scooters = new ArrayList<>();
        for (QueryDocumentSnapshot qds : returned) {
            String docName = qds.getString("documentName");
            String serNumb = qds.getString("serialNumber");
            String brand = qds.getString("brand");
            String cost = qds.getString("cost");
            long prod = qds.getLong("prodYear");
            double weight = qds.getDouble("weight");
            String state = qds.getString("state");
            scooters.add(new Scooter(docName, serNumb, brand, cost, (int) prod, weight, State.valueOf(state)));
        }
        return scooters;
    }

}
