package com.bootcamp.demo.repository;

import com.bootcamp.demo.model.Repair;
import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.ScooterRental;
import com.bootcamp.demo.model.State;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.System.getProperty;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;

@ConditionalOnProperty("firebaseKey")
@Repository
public class ScooterRepository {

    private static final Logger LOGGER = getLogger(ScooterRepository.class);
    private Firestore firestoreDB;

    @PostConstruct
    private void initFirestore() throws IOException {
        InputStream serviceAccount = new ByteArrayInputStream(getProperty("firebaseKey").getBytes(UTF_8));

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        try {
            FirebaseApp.initializeApp(options);
        } catch (Exception ignored) {
        }
        firestoreDB = FirestoreClient.getFirestore();
    }

    public void insertScooter(Scooter scooter) {
        this.firestoreDB.collection("scooters").document(scooter.getDocumentName()).set(scooter);
    }

    public String updateScooter(String documentName, String fieldName, String newValue) {
        DocumentReference doc = this.firestoreDB.collection("scooters").document(documentName);
        switch (fieldName) {
            case "documentName":
                doc.update(fieldName, newValue);
                break;
            case "serialNumber":
                doc.update(fieldName, newValue);
                break;
            case "brand":
                doc.update(fieldName, newValue);
                break;
            case "state":
                doc.update(fieldName, newValue);
                break;
            case "weight":
                doc.update(fieldName, Double.parseDouble(newValue));
                break;
            case "cost":
                doc.update(fieldName, new BigDecimal(newValue));
                break;
            case "prodYear":
                doc.update(fieldName, Integer.parseInt(newValue));
                break;
            case "latitude":
                doc.update(fieldName, newValue);
                break;
            case "longitude":
                doc.update(fieldName, newValue);
                break;
            default:
                break;
        }
        return "Document " + documentName + " had " + fieldName + " updated with " + newValue;
    }

    public String deleteScooter(String documentName) {
        this.firestoreDB.collection("scooters").document(documentName).delete();
        return "Document " + documentName + " was deleted";
    }

    public List<Scooter> readScooters() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = this.firestoreDB.collection("scooters").get();
        List<QueryDocumentSnapshot> returned = future.get().getDocuments();
        ArrayList<Scooter> scooters = new ArrayList<>();
        for (QueryDocumentSnapshot qds : returned) {
            var scooter = qds.toObject(Scooter.class);
            scooter.setRepairs(loadRepairsFor(scooter));
            scooters.add(scooter);
        }
        return scooters;
    }

    public Set<String> getAllPaths() {
        return StreamSupport.stream(this.firestoreDB.listCollections().spliterator(), false)
                .map(CollectionReference::getPath)
                .collect(Collectors.toUnmodifiableSet());
    }


    public Scooter getScooterByName(String scooterName) {
        try {
            DocumentSnapshot document = this.firestoreDB.collection("scooters").document(scooterName).get().get();
            return document.toObject(Scooter.class);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void insertReparation(Scooter scooter, Repair repair){
        this.firestoreDB.collection("scooters").document(scooter.getDocumentName()).collection("repairs").add(repair);
    }

    public List<Repair> loadRepairsFor(Scooter scooter){
        return StreamSupport.stream(this.firestoreDB.collection("scooters").document(scooter.getDocumentName()).collection("repairs")
                .listDocuments().spliterator(), false).map(this::toRepair).collect(Collectors.toList());
    }

    public Repair toRepair(DocumentReference docRef){
        var repair = new Repair();
        try {
            var documentSnapshot = docRef.get().get();
            if(documentSnapshot.exists()){
                repair = documentSnapshot.toObject(Repair.class);
                LOGGER.info("loaded repair entry: " + repair);
            }
        } catch (Exception e){
            LOGGER.error("Failed to load repairs", e);
        }
        return repair;
    }

    @VisibleForTesting
    public void setFirestoreDB(Firestore firestoreDB) {
        this.firestoreDB = firestoreDB;
    }
}
