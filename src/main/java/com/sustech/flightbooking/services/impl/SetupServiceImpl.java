package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.services.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SetupServiceImpl implements SetupService {

    private final AdministratorsRepository administratorsRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public SetupServiceImpl(AdministratorsRepository administratorsRepository,
                            FlightRepository flightRepository) {
        this.administratorsRepository = administratorsRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public boolean isSetup() {
        return administratorsRepository.findAll().size() > 0;
    }

    @Override
    public void setup(boolean generateFlights) {
        setupAdmin();
        if (generateFlights) {
            setupFlights();
        }
    }

    private void setupFlights() {
        createFlights("Shenzhen", "Beijing", 3, 2500)
                .forEach(flightRepository::save);
        createFlights("Beijing", "Shenzhen", 3, 2500)
                .forEach(flightRepository::save);

        createFlights("Shenzhen", "Zhanjiang", 1, 1030)
                .forEach(flightRepository::save);
        createFlights("Zhanjiang", "Shenzhen", 1, 1030)
                .forEach(flightRepository::save);

        createFlights("Shenzhen", "Seattle", 9, 5500)
                .forEach(flightRepository::save);
        createFlights("Seattle", "Shenzhen", 9, 5500)
                .forEach(flightRepository::save);
    }

    private List<Flight> createFlights(String origin, String destination, int durationHours, int price) {
        Random random = new Random();
        return IntStream.generate(() -> random.nextInt(12) * 6)
                .limit(5)
                .mapToObj(i -> {
                    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                    Flight flight = new Flight(UUID.randomUUID(),
                            "SZX" + random.nextInt(1000),
                            price,
                            origin,
                            destination,
                            now.plusHours(i + 2),
                            now.plusHours(i + 2 + durationHours),
                            200);
                    if ((i / 6) % 2 == 0) {
                        flight.publish();
                    }
                    return flight;
                })
                .collect(Collectors.toList());
    }

    private void setupAdmin() {
        Administrator admin = new Administrator(UUID.randomUUID());
        admin.setUserName("admin");
        admin.setPassword("123456");
        administratorsRepository.save(admin);
    }
}
