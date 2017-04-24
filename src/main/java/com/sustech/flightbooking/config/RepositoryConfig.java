package com.sustech.flightbooking.config;

import com.sustech.flightbooking.domainmodel.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

/**
 * Created by Henry on 4/19/2017.
 */
@Configuration
public class RepositoryConfig {

    @Bean
    @Scope("singleton")
    public SessionFactory sessionFactory() {
        Properties prop = new Properties();
        prop.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/flightbooking");
        prop.setProperty("hibernate.connection.username", "projects");
        prop.setProperty("hibernate.connection.password", "sustc@2017");
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration()
                //.addPackage("com.sustech.flightbooking.domainmodel")
                .addAnnotatedClass(EntityBase.class)
                .addAnnotatedClass(FlightBookingUser.class)
                .addAnnotatedClass(Administrator.class)
                .addAnnotatedClass(Passenger.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Flight.class)
                .addProperties(prop);
        return configuration.buildSessionFactory();
    }


}
