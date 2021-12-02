package com.bootcamp.demo.repository;


import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ScooterRepositoryTest {

    private ScooterRepository repository;

    @Mock
    private Firestore firestoreDBMock;
    @Mock
    private CollectionReference collectionReferenceMock;
    @Mock
    private DocumentReference documentReferenceMock;
    @Mock
    private ApiFuture<WriteResult> apiFutureMock;
    @Mock
    private List<QueryDocumentSnapshot> queryDocumentSnapshotListMock;
    @Mock
    private ApiFuture<QuerySnapshot> querySnapshotApiFutureMock;
    @Mock
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFutureMock;
    @Mock
    private DocumentSnapshot documentSnapshotMock;
    @Mock
    private QuerySnapshot querySnapshotMock;
    @Mock
    private QueryDocumentSnapshot queryDocumentSnapshotMock;
    @Mock
    private Iterator<QueryDocumentSnapshot> iteratorMock;
    @Mock
    private InterruptedException interruptedExceptionMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new ScooterRepository();
        repository.setFirestoreDB(firestoreDBMock);

        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldInsertScooter() {
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.set(any())).thenReturn(apiFutureMock);
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString())).thenReturn(documentReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString()).set(any())).thenReturn(apiFutureMock);

        this.repository.insertScooter(new Scooter("doc1", "serial1", "brand1", "1", 2001, 1.1f, State.IN_USE,"1","1"));
    }

    @Test
    void shouldUpdateScooter() {
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.update(anyString(), anyString())).thenReturn(apiFutureMock);
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString())).thenReturn(documentReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString()).update(anyString(), anyString())).thenReturn(apiFutureMock);

        this.repository.updateScooter("doc1", "f1", "v1");
        assertEquals("Document doc1 had f1 updated with v1", this.repository.updateScooter("doc1", "f1", "v1"));

        this.repository.updateScooter("doc1", "documentName", "v1");
        assertEquals("Document doc1 had documentName updated with v1", this.repository.updateScooter("doc1", "documentName", "v1"));

        this.repository.updateScooter("doc1", "serialNumber", "v1");
        assertEquals("Document doc1 had serialNumber updated with v1", this.repository.updateScooter("doc1", "serialNumber", "v1"));

        this.repository.updateScooter("doc1", "brand", "v1");
        assertEquals("Document doc1 had brand updated with v1", this.repository.updateScooter("doc1", "brand", "v1"));

        this.repository.updateScooter("doc1", "state", "v1");
        assertEquals("Document doc1 had state updated with v1", this.repository.updateScooter("doc1", "state", "v1"));

        this.repository.updateScooter("doc1", "weight", "1.1");
        assertEquals("Document doc1 had weight updated with 1.1", this.repository.updateScooter("doc1", "weight", "1.1"));

        this.repository.updateScooter("doc1", "cost", "1");
        assertEquals("Document doc1 had cost updated with 1", this.repository.updateScooter("doc1", "cost", "1"));

        this.repository.updateScooter("doc1", "prodYear", "2020");
        assertEquals("Document doc1 had prodYear updated with 2020", this.repository.updateScooter("doc1", "prodYear", "2020"));
    }

    @Test
    void shouldDeleteScooter() {
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.delete()).thenReturn(apiFutureMock);
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString())).thenReturn(documentReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString()).delete()).thenReturn(apiFutureMock);
        this.repository.deleteScooter("doc1");

        assertEquals("Document documentName was deleted", this.repository.deleteScooter("documentName"));
    }

    @Test
    void shouldReadScooter() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenReturn(querySnapshotMock);
        when(querySnapshotMock.getDocuments()).thenReturn(queryDocumentSnapshotListMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);

        when(queryDocumentSnapshotMock.toObject(any())).thenReturn(new Scooter("IN_USE", "IN_USE", "IN_USE", "1", 123, 1.1, State.IN_USE,"1","1"));
        List<Scooter> scooters = this.repository.readScooters();
        assertNotNull(scooters);
        assertEquals(scooters.get(0), new Scooter("IN_USE", "IN_USE", "IN_USE", "1", 123, 1.1, State.IN_USE,"1","1"));
        verify(firestoreDBMock, times(1)).collection(anyString());

    }

    @Test
    void shouldGetScooterByName() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(documentSnapshotApiFutureMock);
        when(documentSnapshotApiFutureMock.get()).thenReturn(documentSnapshotMock);
        when(documentSnapshotMock.toObject(any())).thenReturn(new Scooter("IN_USE", "IN_USE", "IN_USE", "1", 123, 1.1, State.IN_USE,"1","1"));
        assertEquals(repository.getScooterByName(""), new Scooter("IN_USE", "IN_USE", "IN_USE", "1", 123, 1.1, State.IN_USE,"1","1"));
        verify(firestoreDBMock, times(1)).collection(anyString());
    }

    @Test
    void shouldNotGetScooterByName() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(documentSnapshotApiFutureMock);
        when(documentSnapshotApiFutureMock.get()).thenReturn(documentSnapshotMock);
        when(documentSnapshotApiFutureMock.get()).thenThrow(interruptedExceptionMock);
        this.repository.getScooterByName("");
        verify(firestoreDBMock, times(1)).collection(anyString());

    }

}