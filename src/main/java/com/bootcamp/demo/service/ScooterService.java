package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
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
