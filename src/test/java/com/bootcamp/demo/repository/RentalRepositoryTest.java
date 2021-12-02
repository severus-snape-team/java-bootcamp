package com.bootcamp.demo.repository;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.ScooterRental;
import com.bootcamp.demo.model.State;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
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
import static org.mockito.Mockito.when;

class RentalRepositoryTest {
    private RentalRepository repository;

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
        repository = new RentalRepository();
        repository.setFirestoreDB(firestoreDBMock);

        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
    }

    @Test
    void saveRental() {
        when(collectionReferenceMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.set(any())).thenReturn(apiFutureMock);
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString())).thenReturn(documentReferenceMock);
        when(firestoreDBMock.collection(anyString()).document(anyString()).set(any())).thenReturn(apiFutureMock);

        this.repository.saveRental(new ScooterRental("doc1"));

    }

    @Test
    void deleteRental() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenReturn(querySnapshotMock);
        when(querySnapshotMock.getDocuments()).thenReturn(queryDocumentSnapshotListMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);

        when(queryDocumentSnapshotMock.getString(anyString())).thenReturn("doc1");
        when(queryDocumentSnapshotMock.getId()).thenReturn("id");

        repository.deleteRental(new ScooterRental("doc1"));
    }

    @Test
    void shouldGetRentals() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenReturn(querySnapshotMock);
        when(querySnapshotMock.getDocuments()).thenReturn(queryDocumentSnapshotListMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);

        repository.getRentals();
    }

    @Test
    void shouldNotGetRentals() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenThrow(interruptedExceptionMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);
        repository.getRentals();
    }

    @Test
    void shouldGetDocument() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenReturn(querySnapshotMock);
        when(querySnapshotMock.getDocuments()).thenReturn(queryDocumentSnapshotListMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);

        when(queryDocumentSnapshotMock.getString(anyString())).thenReturn("doc1");
        when(queryDocumentSnapshotMock.getId()).thenReturn("id");
        String result = repository.getDocument(new ScooterRental("doc1"));
        assertNotNull(result);
    }

    @Test
    void shouldNotGetDocument() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenReturn(querySnapshotMock);
        when(querySnapshotMock.getDocuments()).thenReturn(queryDocumentSnapshotListMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);

        when(queryDocumentSnapshotMock.getString(anyString())).thenReturn("doc2");
        String result = repository.getDocument(new ScooterRental("doc1"));
        assertNull(result);
    }

    @Test
    void shouldNotGetDocument2() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenThrow(interruptedExceptionMock);

        when(queryDocumentSnapshotMock.getString(anyString())).thenReturn("doc2");
        String result = repository.getDocument(new ScooterRental("doc1"));
        assertNull(result);
    }

    @Test
    void shouldGetRentalsByScooterName() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenReturn(querySnapshotMock);
        when(querySnapshotMock.getDocuments()).thenReturn(queryDocumentSnapshotListMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);
        when(queryDocumentSnapshotMock.getString(anyString())).thenReturn("doc1");
        when(queryDocumentSnapshotMock.toObject(any())).thenReturn(new ScooterRental("doc1"));

        ScooterRental scooterRental = repository.getRentalByScooterDocName("doc1");
        assertNotNull(scooterRental);
    }

    @Test
    void shouldNotGetRentalsByScooterName() throws ExecutionException, InterruptedException {
        when(firestoreDBMock.collection(anyString())).thenReturn(collectionReferenceMock);
        when(collectionReferenceMock.get()).thenReturn(querySnapshotApiFutureMock);
        when(querySnapshotApiFutureMock.get()).thenThrow(interruptedExceptionMock);

        when(queryDocumentSnapshotListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iteratorMock.next()).thenReturn(queryDocumentSnapshotMock, queryDocumentSnapshotMock);
        assertNull(repository.getRentalByScooterDocName(""));
    }
}