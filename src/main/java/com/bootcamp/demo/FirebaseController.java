package com.bootcamp.demo;

import com.bootcamp.demo.model.Scooter;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.System.getProperty;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Sample RestController
 * Demo - Used for testing purposes
 */
@ConditionalOnProperty("firebaseKey")
@RestController
@RequestMapping(path = "/firebase", produces = APPLICATION_JSON_VALUE)
public class FirebaseController {

    private Firestore firestoreDB;

    @PostConstruct
    private void initFirestore() throws IOException {
        InputStream serviceAccount = new ByteArrayInputStream(getProperty("firebaseKey").getBytes(UTF_8));

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        firestoreDB = FirestoreClient.getFirestore();
    }

    /**
     * Returns the paths for all collections stored in Firestore
     */
    @GetMapping("/getAllPaths")
    public Set<String> getAllPaths() {
        return StreamSupport.stream(firestoreDB.listCollections().spliterator(), false)
                .map(CollectionReference::getPath)
                .collect(Collectors.toUnmodifiableSet());
    }

    @PostMapping("/create")
    public void insertScooter(Scooter scooter){
        this.firestoreDB.collection("scooters").document(scooter.getDocumentName()).set(scooter);
        System.out.println("INSERTED");
    }

    @GetMapping("/delete/{documentName}")
    public String deleteScooter(@PathVariable("documentName") String documentName) {
        this.firestoreDB.collection("scooters").document(documentName).delete();
        return "Document " + documentName + " was deleted";
    }

    @GetMapping("/update/{documentName}/{fieldName}/{newValue}")
    public String updateScooter(@PathVariable String documentName,@PathVariable String fieldName,@PathVariable String newValue) {
        DocumentReference doc = this.firestoreDB.collection("scooters").document(documentName);
        switch (fieldName){
            case "documentName", "serialNumber", "brand", "state" -> doc.update(fieldName, newValue);
            case "weight" -> doc.update(fieldName, Double.parseDouble(newValue));
            case "cost" -> doc.update(fieldName, new BigDecimal(newValue));
            case "prodYear" -> doc.update(fieldName, Integer.parseInt(newValue));
        }
        return "Document " + documentName + " had " + fieldName + " updated with " + newValue;
    }

    @GetMapping("/read")
    public void readAll() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = this.firestoreDB.collection("scooters").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(document.getId());
        }
    }
}
