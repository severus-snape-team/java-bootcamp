package com.bootcamp.demo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ScooterService {

    public String saveScooter(Scooter scooter) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("scooters").document(scooter.getSerialNumber()+"").set(scooter);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }}