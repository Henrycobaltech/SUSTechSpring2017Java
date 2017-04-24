package com.sustech.flightbooking.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Henry on 4/19/2017.
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public Datastore createDataStore() {
        final Morphia morphia = new Morphia();

        morphia.mapPackage("com.sustech.flightbooking.domainmodel");

        final Datastore datastore = morphia.createDatastore(new MongoClient("localhost:27017"), "flightbooking");
        datastore.ensureIndexes();
        return datastore;
    }
}