package com.bootcamp.demo.repository;

import com.bootcamp.demo.FirebaseController;
import com.bootcamp.demo.model.Scooter;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ScooterRepository {

    private final FirebaseController firebaseController;

    @Autowired
    public ScooterRepository(FirebaseController firebaseController) {
        this.firebaseController = firebaseController;
    }

    public void insertScooter(Scooter scooter) {
        this.firebaseController.getFirestoreDB().collection("scooters").document(scooter.getDocumentName()).set(scooter);
    }

    public List<QueryDocumentSnapshot> readScooters() {
        ApiFuture<QuerySnapshot> future = this.firebaseController.getFirestoreDB().collection("scooters").get();
        try {
            return future.get().getDocuments();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("ERROR IN RETRIEVING SCOOTER OBJECTS");
            e.printStackTrace();
            return null;
        }
        // TODO convert the previous list to Scooter objects in order to return all the scooters to list them in html
    }
}
