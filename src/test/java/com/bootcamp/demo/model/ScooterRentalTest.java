package com.bootcamp.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ScooterRentalTest {
    ScooterRental scooterRental1;
    ScooterRental scooterRental2;
    Date date;
    @BeforeEach
    void setUp() {
        date = new Date();
        date.setTime(10);
        scooterRental1 = new ScooterRental("doc1", date);
        scooterRental2 = new ScooterRental("doc2");

    }

    @Test
    void getScooterDocumentName() {
        assertEquals("doc1",scooterRental1.getScooterDocumentName());
    }

    @Test
    void setScooterDocumentName() {
        scooterRental2.setScooterDocumentName("doc3");
        assertEquals("doc3",scooterRental2.getScooterDocumentName());
        scooterRental2.setScooterDocumentName("doc2");

    }

    @Test
    void getStartDate() {
        assertEquals(date,scooterRental1.getStartDate());
    }

    @Test
    void setStartDate() {
        Date date2 = new Date();
        date2.setTime(20);
        scooterRental1.setStartDate(date2);
        assertEquals(date2,scooterRental1.getStartDate());
        scooterRental1.setStartDate(date);
    }

    @Test
    void testToString() {
    assertEquals("Scooter document name: doc1\nStart renting at: 01/01/1970 02:00:00",scooterRental1.toString());
    }

    @Test
    void testEquals() {
        assertTrue(scooterRental1.equals(scooterRental1));
        assertFalse(scooterRental1.equals(null));
        assertFalse(scooterRental1.equals(new Object()));
        ScooterRental r1 = new ScooterRental("doc1",date);
        ScooterRental r2 = new ScooterRental("doc2",date);
        ScooterRental r3 = new ScooterRental("doc1",new Date());
        assertTrue(scooterRental1.equals(r1));
        assertFalse(scooterRental1.equals(r2));
        assertFalse(scooterRental1.equals(r3));
    }
}