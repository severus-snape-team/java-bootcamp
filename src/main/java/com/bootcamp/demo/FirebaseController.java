package com.bootcamp.demo;

import com.bootcamp.demo.dto.ScooterDTO;
import com.bootcamp.demo.entity.Scooter;
import com.bootcamp.demo.enums.State;
import com.bootcamp.demo.service.ScooterService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
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

    private final ScooterService scooterService;

    @Autowired
    public FirebaseController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

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

    @RequestMapping(value = "/createAndSaveScooter")
    @PostMapping()
    public void createAndSaveScooter() throws ExecutionException, InterruptedException {

        Scooter scooter = new Scooter("12345TI", "MyBrand", 355.67, 2020, 15.8, State.IN_SERVICE);

        scooterService.addScooter(scooter);
    }


    @RequestMapping(value = "/addScooter")
    @PostMapping()
    public ResponseEntity<UUID> addScooter(@RequestBody ScooterDTO scooterDTO) throws ExecutionException, InterruptedException {

        System.out.println(scooterDTO);

        scooterService.addScooterDTO(scooterDTO);

        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK );
    }


}
