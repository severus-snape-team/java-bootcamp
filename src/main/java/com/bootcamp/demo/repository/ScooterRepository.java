package com.bootcamp.demo.repository;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
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

@ConditionalOnProperty("firebaseKey")
@Repository
public class ScooterRepository {
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
            String docName = qds.getString("documentName");
            String serNumb = qds.getString("serialNumber");
            String brand = qds.getString("brand");
            String cost = qds.getString("cost");
            int prod = qds.getLong("prodYear").intValue();
            double weight = qds.getDouble("weight");
            String state = qds.getString("state");
            scooters.add(new Scooter(docName, serNumb, brand, cost, prod, weight, State.valueOf(state),"",""));
        }
        return scooters;
    }

    public Set<String> getAllPaths() {
        return StreamSupport.stream(this.firestoreDB.listCollections().spliterator(), false)
                .map(CollectionReference::getPath)
                .collect(Collectors.toUnmodifiableSet());
    }


    public DocumentSnapshot getScooterByName(String scooterName) {
        ApiFuture<DocumentSnapshot> future = this.firestoreDB.collection("scooters").document(scooterName).get();
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("ERROR IN RETRIEVING SCOOTER OBJECT FOR: "+ scooterName);
            e.printStackTrace();
            return null;
        }
    }

    @VisibleForTesting
    public void setFirestoreDB(Firestore firestoreDB) {
        this.firestoreDB = firestoreDB;
    }
}
