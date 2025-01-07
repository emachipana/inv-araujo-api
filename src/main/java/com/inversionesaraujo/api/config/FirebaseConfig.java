package com.inversionesaraujo.api.config;

import java.io.IOException;
import java.io.FileInputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream(System.getenv("ADMINSDK_PATH"));

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(System.getenv("FIREBASE_BUCKET"))
                .build();

            FirebaseApp.initializeApp(options);
        }catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }
}
