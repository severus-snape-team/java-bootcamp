package com.bootcamp.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ScooterTest {
    Scooter scooter;

    @BeforeEach
    void setUp() {
        scooter = new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
    }

    @Test
    void testCreation() {
        Scooter scooter1 = new Scooter();
        assertNotNull(scooter1);
        assertNull(scooter1.getDocumentName());
        assertNull(scooter1.getSerialNumber());
        assertNull(scooter1.getBrand());
        assertNull(scooter1.getCost());
        assertEquals(0, scooter1.getProdYear());
        assertEquals(0f, scooter1.getWeight());
        assertNull(scooter1.getState());
    }

    @Test
    void testToString() {
        assertEquals("Scooter{documentName='doc1', serialNumber='serial1', brand='brand1', cost=1, prodYear=2001, weight=1.100000023841858, state=IN_USE, latitude='-27.409918931537973', longitude='128.06496968889238'}", scooter.toString());
    }

    @Test
    void getDocumentName() {
        assertEquals("doc1", scooter.getDocumentName());
    }

    @Test
    void setDocumentName() {
        scooter.setDocumentName("docx");
        assertEquals("docx", scooter.getDocumentName());
        scooter.setDocumentName("doc1");
    }

    @Test
    void getSerialNumber() {
        assertEquals("serial1", scooter.getSerialNumber());
    }

    @Test
    void setSerialNumber() {
        scooter.setSerialNumber("serialx");
        assertEquals("serialx", scooter.getSerialNumber());
        scooter.setSerialNumber("serial1");
    }

    @Test
    void getBrand() {
        assertEquals("brand1", scooter.getBrand());
    }

    @Test
    void setBrand() {
        scooter.setBrand("brandx");
        assertEquals("brandx", scooter.getBrand());
        scooter.setBrand("doc1");
    }

    @Test
    void getCost() {
        assertEquals(new BigDecimal("1"), scooter.getCost());
    }

    @Test
    void setCost() {
        scooter.setCost(new BigDecimal("666"));
        assertEquals(new BigDecimal("666"), scooter.getCost());
        scooter.setCost(new BigDecimal("1"));
    }

    @Test
    void getProdYear() {
        assertEquals(2001, scooter.getProdYear());
    }

    @Test
    void setProdYear() {
        scooter.setProdYear(2006);
        assertEquals(2006, scooter.getProdYear());
        scooter.setProdYear(2001);
    }

    @Test
    void getWeight() {
        assertEquals(1.1f, scooter.getWeight());
    }

    @Test
    void setWeight() {
        scooter.setWeight(6.6f);
        assertEquals(6.6f, scooter.getWeight());
        scooter.setWeight(1.1f);
    }

    @Test
    void getState() {
        assertEquals(State.IN_USE, scooter.getState());
    }

    @Test
    void setState() {
        scooter.setState(State.BROKEN);
        assertEquals(State.BROKEN, scooter.getState());
        scooter.setState(State.IN_USE);
    }

    @Test
    void getLatitude() {
        assertEquals("-27.409918931537973", scooter.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals("128.06496968889238", scooter.getLongitude());
    }

    @Test
    void setLatitude() {
        scooter.setLatitude("2");
        assertEquals("2", scooter.getLatitude());
        scooter.setLatitude("1");
    }

    @Test
    void setLongitude() {
        scooter.setLongitude("2");
        assertEquals("2", scooter.getLongitude());
        scooter.setLongitude("1");
    }

    @Test
    void testEquals() {
        assertTrue(scooter.equals(scooter));
        assertFalse(scooter.equals(null));
        assertFalse(scooter.equals(new Object()));
        Scooter scooter1 = new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter2 = new Scooter("doc2", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter3 = new Scooter("doc1", "serial2", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter4 = new Scooter("doc1", "serial1", "brand2", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter5 = new Scooter("doc1", "serial1", "brand1", "2", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter6 = new Scooter("doc1", "serial1", "brand1", "1", 2002, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter7 = new Scooter("doc1", "serial1", "brand1", "1", 2001, 2.2f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        Scooter scooter8 = new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.BROKEN, "-27.409918931537973", "128.06496968889238");
        Scooter scooter9 = new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27", "128.06496968889238");
        Scooter scooter10 = new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128");
        assertTrue(scooter.equals(scooter1));
        assertFalse(scooter.equals(scooter2));
        assertFalse(scooter.equals(scooter3));
        assertFalse(scooter.equals(scooter4));
        assertFalse(scooter.equals(scooter5));
        assertFalse(scooter.equals(scooter6));
        assertFalse(scooter.equals(scooter7));
        assertFalse(scooter.equals(scooter8));
        assertFalse(scooter.equals(scooter9));
        assertFalse(scooter.equals(scooter10));
    }

    @Test
    void testHashCode() {
        int h1 = scooter.hashCode();
        assertEquals(h1, scooter.hashCode());
        Scooter scooter2 = new Scooter("doc2", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE, "-27.409918931537973", "128.06496968889238");
        assertNotEquals(h1, scooter2.hashCode());
    }
}