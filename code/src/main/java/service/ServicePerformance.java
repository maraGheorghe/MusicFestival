package service;

import model.Performance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.RepositoryInterfacePerformance;
import validator.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ServicePerformance {
    private RepositoryInterfacePerformance repository;
    private Validator<Performance> validator;
    private static final Logger logger = LogManager.getLogger();

    public ServicePerformance(RepositoryInterfacePerformance repository, Validator<Performance> validator) {
        logger.info("Initializing ServicePerformance.");
        this.repository = repository;
        this.validator = validator;
    }

    public Optional<Performance> save(LocalDateTime date, String place, int noOfAvailableSeats, String artist) {
        logger.traceEntry("Saving performance.");
        Performance performance = new Performance(date, place, noOfAvailableSeats, 0, artist);
        validator.validate(performance);
        return repository.save(performance);
    }

    public List<Performance> findAll() {
        logger.info("Finding all the performances.");
        return repository.findAll();
    }

    public List<Performance> findAllForADay(LocalDate date) {
        logger.info("Finding all the performances for the day {}.", date);
        return repository.findAllPerformancesForADay(date);
    }
}
