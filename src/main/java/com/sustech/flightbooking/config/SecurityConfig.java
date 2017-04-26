package com.sustech.flightbooking.config;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationProvider;
import com.sustech.flightbooking.infrastructure.FlightBookingAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by Henry on 4/25/2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new FlightBookingAuthorizationFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FlightBookingAuthenticationProvider();
    }
}
