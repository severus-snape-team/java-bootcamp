package com.bootcamp.demo.repository;

import com.bootcamp.demo.model.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.lang.System.getProperty;
import static java.nio.charset.StandardCharsets.UTF_8;

@ConditionalOnProperty("firebaseKey")
@Repository
public class UserRepository {
    private Firestore firestoreDB;

    @PostConstruct
    private void initFirestore() throws IOException {
        InputStream serviceAccount = new ByteArrayInputStream(getProperty("firebaseKey").getBytes(UTF_8));

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        try {
            FirebaseApp.initializeApp(options);
        } catch (Exception ignored) {
        }
        firestoreDB = FirestoreClient.getFirestore();
    }

    public void saveUser(User user) {
        if (getDocument(user) != null)
            throw new RuntimeException("User with this email address already exists");
        this.firestoreDB.collection("users").add(user);
    }

    public void deleteUser(User user) {
        if (getDocument(user) == null)
            throw new RuntimeException("Can't delete inexistent user");
        this.firestoreDB.collection("users").document(getDocument(user)).delete();
    }

    public User getUserByEmail(String email) {
        try {
            List<QueryDocumentSnapshot> documents = this.firestoreDB.collection("users").get().get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                if (Objects.equals(doc.getString("email"), email))
                    return doc.toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        try {
            List<QueryDocumentSnapshot> documents = this.firestoreDB.collection("users").get().get().getDocuments();
            List<User> users = new ArrayList<>();
            for (QueryDocumentSnapshot doc : documents) {
                users.add(doc.toObject(User.class));
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDocument(User user) {
        try {
            List<QueryDocumentSnapshot> documents = this.firestoreDB.collection("users").get().get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                String email = doc.getString("email");
                if (user.getEmail().equals(email))
                    return doc.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @VisibleForTesting
    public void setFirestoreDB(Firestore firestoreDB) {
        this.firestoreDB = firestoreDB;
    }
}
