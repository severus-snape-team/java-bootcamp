package com.bootcamp.demo.repository;

import com.bootcamp.demo.model.ScooterRental;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
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
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.getProperty;
import static java.nio.charset.StandardCharsets.UTF_8;

@ConditionalOnProperty("firebaseKey")
@Repository
public class RentalRepository {
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

    public void saveRental(ScooterRental scooterRental) {
        this.firestoreDB.collection("rentals").add(scooterRental);
    }

    public void deleteRental(ScooterRental scooterRental) {
        this.firestoreDB.collection("rentals").document(getDocument(scooterRental)).delete();
    }

    public List<ScooterRental> getRentals() {
        try {
            List<QueryDocumentSnapshot> documents = this.firestoreDB.collection("rentals").get().get().getDocuments();
            List<ScooterRental> rentals = new ArrayList<>();
            for (QueryDocumentSnapshot doc : documents) {
                String scooterDocumentName = doc.getString("scooterDocumentName");
                Date startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(doc.getString("startDate"));
                rentals.add(new ScooterRental(scooterDocumentName, startDate));
//                rentals.add(doc.toObject(ScooterRental.class));
            }
            return rentals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDocument(ScooterRental scooterRental) {
        try {
            List<QueryDocumentSnapshot> documents = this.firestoreDB.collection("rentals").get().get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                String scooterDocumentName = doc.getString("scooterDocumentName");
                if (scooterRental.getScooterDocumentName().equals(scooterDocumentName))
                    return doc.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ScooterRental getRentalByScooterDocName(String scooterDocumentName) {
        try {
            List<QueryDocumentSnapshot> documents = this.firestoreDB.collection("rentals").get().get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                if (Objects.equals(doc.getString("scooterDocumentName"), scooterDocumentName)) {
                    Timestamp timestamp = doc.getTimestamp("startDate");
                    Date startDate = timestamp.toDate();
                    return new ScooterRental(scooterDocumentName, startDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @VisibleForTesting
    public void setFirestoreDB(Firestore firestoreDB) {
        this.firestoreDB = firestoreDB;
    }
}
