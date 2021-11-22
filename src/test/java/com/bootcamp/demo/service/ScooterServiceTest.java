package com.bootcamp.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.repository.ScooterRepository;
import java.util.InputMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScooterServiceTest {

    private ScooterService scooterService;

    @Mock
    private ScooterRepository scooterRepositoryMock;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        scooterService = new ScooterService(scooterRepositoryMock);
    }

    @Test
    void shouldNotInsertScooter() {
            try {
                scooterService.insertScooter(new Scooter("dcoumentName", "serialNumber_INVALID", "brand", "100", 2019, 100, State.IN_SERVICE));
                fail("Should have thrown exception");
            }
            catch (InputMismatchException e) {
                //ok, this is expected
            }
    }

    @Test
    void shouldInsertScooter() {
        doNothing().when(scooterRepositoryMock).insertScooter(any(Scooter.class));

        scooterService.insertScooter(new Scooter("documentName", "serialNumber", "brand", "100", 2019, 100, State.IN_SERVICE));

        verify(scooterRepositoryMock, times(1)).insertScooter(any(Scooter.class));
    }
}