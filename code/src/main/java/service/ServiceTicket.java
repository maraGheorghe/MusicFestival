package service;

import model.Performance;
import model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.RepositoryInterfaceTicket;
import validator.Validator;

import java.time.LocalDateTime;
import java.util.Optional;

public class ServiceTicket {

    private RepositoryInterfaceTicket repository;
    private Validator<Ticket> validatorTicket;
    private Validator<Performance> validatorPerformance;
    private static final Logger logger = LogManager.getLogger();

    public ServiceTicket(RepositoryInterfaceTicket repository, Validator<Ticket> validatorTicket, Validator<Performance> validatorPerformance) {
        logger.info("Initializing ServiceTicket.");
        this.repository = repository;
        this.validatorTicket = validatorTicket;
        this.validatorPerformance = validatorPerformance;
    }

    public Optional<Ticket> save(Long performanceID, LocalDateTime date, String place, int noOfAvailableSeats, int noOfSoldSeats, String artist, String owner, int noOfSeats) {
        logger.traceEntry("Saving ticket at performance with ID {}, bought by {}.", performanceID, owner);
        Performance performance = new Performance(performanceID, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
        validatorPerformance.validate(performance);
        Ticket ticket = new Ticket(performance, owner, noOfSeats);
        validatorTicket.validate(ticket);
        return repository.save(ticket);
    }
}
