package com.bootcamp.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.ScooterRepository;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentSnapshot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScooterServiceTest {

    private ScooterService scooterService;

    @Mock
    private ScooterRepository scooterRepositoryMock;
    @Mock
    private DocumentSnapshot documentSnapshotMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scooterService = new ScooterService(scooterRepositoryMock);
    }

    @Test
    void shouldNotInsertScooter() {
        scooterService.insertScooter(new Scooter("documentName", "serialNumber_INVALID", "brand", "100", 2019, 100, State.IN_SERVICE, "-27.409918931537973", "128.06496968889238"));
        verify(scooterRepositoryMock, times(1)).insertScooter(any(Scooter.class));
    }

    @Test
    void shouldInsertScooter() {
        doNothing().when(scooterRepositoryMock).insertScooter(any(Scooter.class));

        scooterService.insertScooter(new Scooter("documentName", "serialNumber", "brand", "100", 2019, 100, State.IN_SERVICE, "-27.409918931537973", "128.06496968889238"));

        verify(scooterRepositoryMock, times(1)).insertScooter(any(Scooter.class));
    }

    @Test
    void shouldUpdateScooter() {
        when(scooterRepositoryMock.updateScooter("documentName", "fieldName", "newValue")).thenReturn("Updated");
        doReturn("Updated").when(scooterRepositoryMock).updateScooter("documentName", "fieldName", "newValue");

        scooterService.updateScooter("documentName", "fieldName", "newValue");

        verify(scooterRepositoryMock, times(1)).updateScooter("documentName", "fieldName", "newValue");
    }

    @Test
    void shouldDeleteScooter() {
        when(scooterRepositoryMock.deleteScooter("documentName")).thenReturn("Deleted");
        doReturn("Deleted").when(scooterRepositoryMock).deleteScooter("documentName");

        scooterService.deleteScooter("documentName");

        verify(scooterRepositoryMock, times(1)).deleteScooter("documentName");
    }

    @Test
    void shouldReturnScooters() throws ExecutionException, InterruptedException {
        ArrayList<Scooter> scooters = new ArrayList<>();
        when(scooterRepositoryMock.readScooters()).thenReturn(scooters);
        doReturn(scooters).when(scooterRepositoryMock).readScooters();

        scooterService.returnAllScooters();

        verify(scooterRepositoryMock, times(1)).readScooters();
    }

    @Test
    void getAllPaths() {
        Set<String> test = new HashSet<>();
        when(scooterRepositoryMock.getAllPaths()).thenReturn(test);
        doReturn(test).when(scooterRepositoryMock).getAllPaths();
        assertEquals(test, scooterService.getAllPaths());
        verify(scooterRepositoryMock, times(1)).getAllPaths();
    }

    @Test
    void shouldGetScooterByName() {
        when(scooterRepositoryMock.getScooterByName(anyString())).thenReturn(new Scooter("documentName", "serialNumber_INVALID", "brand", "100", 2019, 100, State.IN_SERVICE, "1", "1"));
        assertEquals(new Scooter("documentName", "serialNumber_INVALID", "brand", "100", 2019, 100, State.IN_SERVICE, "1", "1"), scooterService.getScooterByName(""));
        verify(scooterRepositoryMock, times(1)).getScooterByName(anyString());

    }

    @Test
    void returnAvailableScooters() throws ExecutionException, InterruptedException {
        List<Scooter> scooterList = new ArrayList<>();
        when(scooterRepositoryMock.readScooters()).thenReturn(scooterList);
        List<Scooter> result = scooterService.returnAvailableScooters();
        assertEquals(new ArrayList<>(),result);
    }
}