package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.repository.ScooterRepository;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;

    @Autowired
    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }

    public void insertScooter(Scooter scooter) {
        this.scooterRepository.insertScooter(scooter);
    }

    public ArrayList<String> returnAllScooters() {
        //TODO change from String to Scooter and add Scooter objects in the ArrayList
        List<QueryDocumentSnapshot> returned = this.scooterRepository.readScooters();
        ArrayList<String> scooters = new ArrayList<>();
        for (QueryDocumentSnapshot qds : returned) {
            scooters.add(qds.getId());
        }
        return scooters;
    }

}
