package com.sora.ryokka.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Configuration
public class FirebaseConfig {

    private final FirebaseConfigProperties firebaseConfigProperties;

    public FirebaseConfig(FirebaseConfigProperties firebaseConfigProperties) {
        this.firebaseConfigProperties = firebaseConfigProperties;
    }

    @PostConstruct
    public void initialize() throws IOException {

        firebaseConfigProperties.setPrivate_key(
                firebaseConfigProperties.getPrivate_key().replace("\\n", "\n"));

        String json = new Gson().toJson(firebaseConfigProperties);

        GoogleCredentials credentials =
                GoogleCredentials.fromStream(new ByteArrayInputStream(json.getBytes()));

        FirebaseOptions options =
                FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .setDatabaseUrl(firebaseConfigProperties.getUrl())
                        .build();

        FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }

}
