package com.bootcamp.demo.controller;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.bootcamp.demo.service.ScooterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class ScooterControllerTest {
    private ScooterController scooterController;
    @Mock
    private ScooterService scooterServiceMock;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scooterController = new ScooterController(scooterServiceMock);
    }

    @Test
    void register() {
        assertEquals("createScooterForm", scooterController.register(model));
    }

    @Test
    void submitForm() {
        doNothing().when(scooterServiceMock).insertScooter(any(Scooter.class));
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals("createScooterForm", scooterController.submitForm(new Scooter(), bindingResult, model));
        verify(scooterServiceMock, times(1)).insertScooter(any(Scooter.class));

    }

    @Test
    void notSubmitForm() {
        doNothing().when(scooterServiceMock).insertScooter(any(Scooter.class));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("createScooterForm", scooterController.submitForm(new Scooter(), bindingResult, model));
    }

    @Test
    void viewAllScooters() throws ExecutionException, InterruptedException {
        assertEquals("listScooters", scooterController.viewAllScooters(model));
    }

    @Test
    void getAllPaths() {
        Set<String> test = new HashSet<>();
        when(scooterServiceMock.getAllPaths()).thenReturn(test);
        doReturn(test).when(scooterServiceMock).getAllPaths();
        assertEquals(test, scooterController.getAllPaths());
        verify(scooterServiceMock, times(1)).getAllPaths();

    }

    @Test
    void shouldNotViewScooter() {
        when(scooterServiceMock.getScooterByName(anyString())).thenReturn(null);
        assertEquals("redirect:/firebase/scooters", this.scooterController.viewScooter("", model));
        verify(scooterServiceMock, times(1)).getScooterByName(anyString());

    }

    @Test
    void shouldViewScooter() {
        when(scooterServiceMock.getScooterByName(anyString())).thenReturn(new Scooter());
        assertEquals("getScooter", this.scooterController.viewScooter("", model));
        verify(scooterServiceMock, times(1)).getScooterByName(anyString());

    }

    @Test
    void updateScooter() {
        doNothing().when(scooterServiceMock).insertScooter(any(Scooter.class));
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals("getScooter", scooterController.updateScooter(new Scooter(), bindingResult, model));
        verify(scooterServiceMock, times(1)).insertScooter(any(Scooter.class));
    }

    @Test
    void notUpdateScooter() {
        doNothing().when(scooterServiceMock).insertScooter(any(Scooter.class));
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("getScooter", scooterController.updateScooter(new Scooter(), bindingResult, model));
    }

    @Test
    void deleteScooter() {
        when(scooterServiceMock.deleteScooter(anyString())).thenReturn("deleted");
        assertEquals("redirect:/firebase/scooters", scooterController.deleteScooter(new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE)));
        verify(scooterServiceMock, times(1)).deleteScooter(anyString());

    }
}