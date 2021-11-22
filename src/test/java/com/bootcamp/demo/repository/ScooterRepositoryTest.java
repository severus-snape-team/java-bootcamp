package com.bootcamp.demo.repository;

import static org.junit.jupiter.api.Assertions.*;


import com.google.cloud.firestore.Firestore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScooterRepositoryTest {

    private ScooterRepository repository;

    @Mock
    private Firestore firestoreDBMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new ScooterRepository();
        repository.setFirestoreDB(firestoreDBMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldInsertScooter() {
    }

    @Test
    void shouldDeleteScooter() {
    }
}