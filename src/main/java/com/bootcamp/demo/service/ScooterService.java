package com.bootcamp.demo.service;

import com.bootcamp.demo.dto.ScooterDTO;
import com.bootcamp.demo.dto.builder.ScooterBuilder;
import com.bootcamp.demo.entity.Scooter;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class ScooterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScooterService.class);

    public void addScooter(Scooter scooter) throws ExecutionException, InterruptedException {

        Firestore firestoreDB = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestoreDB.document("scooters/scooterMeHigh").set(scooter);

        WriteResult writeResult = apiFuture.get();

        LOGGER.info("Update time: {}", writeResult.getUpdateTime());

    }

    public void addScooterDTO(ScooterDTO scooterDTO) throws ExecutionException, InterruptedException {

        Firestore firestoreDB = FirestoreClient.getFirestore();

        Scooter scooter = ScooterBuilder.toEntity(scooterDTO);

        ApiFuture<WriteResult> apiFuture = firestoreDB.document("scooters/"+scooterDTO.getDocument_id()).set(scooter);

        WriteResult writeResult = apiFuture.get();

        LOGGER.info("User with id: {} created successfully",scooterDTO.getDocument_id());
    }

}
