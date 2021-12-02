package com.bootcamp.demo.service;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.ScooterRental;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.RentalRepository;
import com.bootcamp.demo.repository.ScooterRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class RentalServiceTest {
    private RentalService rentalService;

    @Mock
    private ScooterRepository scooterRepositoryMock;
    @Mock
    private RentalRepository rentalRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalService = new RentalService(scooterRepositoryMock, rentalRepositoryMock);
    }

    Scooter scooter = new Scooter("documentName", "serialNumber_INVALID", "brand", "100", 2019, 100, State.IN_USE, "1", "1");
    Scooter scooter1 = new Scooter("documentName", "serialNumber_INVALID", "brand", "100", 2019, 100, State.OUT_OF_USE, "1", "1");

    @Test
    void shouldSaveRental() {
        when(scooterRepositoryMock.getScooterByName(anyString())).thenReturn(scooter1);
        doNothing().when(rentalRepositoryMock).saveRental(any());
        when(scooterRepositoryMock.updateScooter(anyString(), anyString(), anyString())).thenReturn("");
        rentalService.saveRental("documentName");
    }

    @Test
    void shouldNotSaveRental() {
        when(scooterRepositoryMock.getScooterByName(anyString())).thenReturn(scooter);
        assertThrows(RuntimeException.class, () -> rentalService.saveRental("documentName"));
    }

    @Test
    void shouldDeleteRental() {
        when(scooterRepositoryMock.getScooterByName(anyString())).thenReturn(scooter);
        when(rentalRepositoryMock.getRentalByScooterDocName(anyString())).thenReturn(new ScooterRental("doc1"));
        doNothing().when(rentalRepositoryMock).deleteRental(any());
        when(scooterRepositoryMock.updateScooter(anyString(), anyString(), anyString())).thenReturn("");
        rentalService.deleteRental("documentName");
    }

    @Test
    void shouldNotDeleteRental() {
        when(scooterRepositoryMock.getScooterByName(anyString())).thenReturn(scooter1);
        assertThrows(RuntimeException.class, () -> rentalService.deleteRental("documentName"));
    }

    @Test
    void getRentalByName() {
        ScooterRental scooterRental = new ScooterRental("doc1");
        when(rentalRepositoryMock.getRentalByScooterDocName(anyString())).thenReturn(scooterRental);
        assertEquals(scooterRental, rentalService.getRentalByName(""));
    }

    @Test
    void getTimeRental() {
        when(rentalRepositoryMock.getRentalByScooterDocName(anyString())).thenReturn(new ScooterRental("doc1"));
        assertEquals(0, rentalService.getTimeRental(""));
    }

    @Test
    void getRentalCost() {
        when(rentalRepositoryMock.getRentalByScooterDocName(anyString())).thenReturn(new ScooterRental("doc1"));
        assertEquals(new BigDecimal("0.0"), rentalService.getRentalCost(""));
    }
}