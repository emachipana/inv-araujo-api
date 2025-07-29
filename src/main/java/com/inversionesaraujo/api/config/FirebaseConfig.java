package com.inversionesaraujo.api.config;

import java.io.IOException;
import java.io.FileInputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;

@Configuration
public class FirebaseConfig {
    
    private FirebaseApp firebaseApp;

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream(System.getenv("ADMINSDK_PATH"));

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(System.getenv("FIREBASE_BUCKET"))
                .build();

            this.firebaseApp = FirebaseApp.initializeApp(options);
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }
    
    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
