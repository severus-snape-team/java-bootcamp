package com.bootcamp.demo.repository;

import com.bootcamp.demo.model.RentalHistory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.lang.System.getProperty;
import static java.nio.charset.StandardCharsets.UTF_8;

@ConditionalOnProperty("firebaseKey")
@Repository
public class RentalHistoryRepository {
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

    public void saveRentalHistory(RentalHistory rentalHistory) {
        this.firestoreDB.collection("rental_history").add(rentalHistory);
    }

    public List<RentalHistory> getRentalHistoryForUser(String email) {
        try {
            List<RentalHistory> history = new ArrayList<>();
            List<QueryDocumentSnapshot> documents = firestoreDB.collection("rental_history").whereEqualTo("email", email).get().get().getDocuments();
            for (DocumentSnapshot doc : documents) {
                String scooterDocumentName = doc.getString("scooterDocumentName");
                Date startDate = doc.getTimestamp("startDate").toDate();
                Date endDate = doc.getTimestamp("endDate").toDate();
                BigDecimal tripCost = new BigDecimal(Objects.requireNonNull(doc.getString("tripCost")));
                history.add(new RentalHistory(email, scooterDocumentName, startDate, endDate, tripCost));
            }
            return history;
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
