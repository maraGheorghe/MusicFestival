package server;

import model.Performance;
import model.Ticket;
import model.User;
import repository.interfaces.RepositoryInterfacePerformance;
import repository.interfaces.RepositoryInterfaceTicket;
import repository.interfaces.RepositoryInterfaceUser;
import services.ObserverInterface;
import services.ServiceException;
import services.ServiceInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validators.TicketValidator;

public class ServiceImplementation implements ServiceInterface {

    private RepositoryInterfaceUser userRepository;
    private RepositoryInterfacePerformance performanceRepository;
    private RepositoryInterfaceTicket ticketRepository;
    private Set<ObserverInterface> loggedClients;
    private static final Logger logger = LogManager.getLogger();
    private final int defaultThreadsNumber = 5;


    public ServiceImplementation(RepositoryInterfaceUser userRepository, RepositoryInterfacePerformance
            performanceRepository, RepositoryInterfaceTicket ticketRepository) {
        logger.info("Initializing ServiceImplementation.");
        this.userRepository = userRepository;
        this.performanceRepository = performanceRepository;
        this.ticketRepository = ticketRepository;
        this.loggedClients = new CopyOnWriteArraySet<ObserverInterface>();
    }

    @Override
    public void login(String username, String password, ObserverInterface client) throws ServiceException {
        logger.traceEntry("Login user with username {} and password {}.", username, password);
        Optional<User> optionalUser = userRepository.checkConnection(username, password);
        if(optionalUser.isEmpty()) {
            logger.error("Username and password do not match! Please try again.");
            throw new ServiceException("Username and password do not match! Please try again.");
        }
        loggedClients.add(client);
        logger.traceExit();
    }

    @Override
    public void logout(User user, ObserverInterface client) throws ServiceException {
        logger.info("Logging out user {}.", user.getUsername());
        boolean localClient = loggedClients.remove(client);
        if(!localClient) {
            logger.error("This user is not logged in.");
            throw new ServiceException("This user is not logged in.");
        }
        logger.traceExit();
    }

    @Override
    public synchronized List<Performance> findAllPerformances() {
        logger.traceEntry("Finding all the performances.");
        List<Performance> performances = performanceRepository.findAll();
        logger.traceExit();
        return performances;
    }

    @Override
    public synchronized List<Performance> findAllPerformancesForADay(LocalDate date) {
        logger.info("Finding all the performances for the day {}.", date);
        List<Performance> performances = performanceRepository.findAllPerformancesForADay(date);
        logger.traceExit();
        return performances;
    }

    @Override
    public synchronized void saveTicket(Long performanceID, LocalDateTime date, String place, Integer noOfAvailableSeats,
                           Integer noOfSoldSeats, String artist, String owner, int noOfSeats) throws ServiceException {
        logger.info("Saving ticket...");
        Performance performance = new Performance(performanceID, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
        Ticket ticket = new Ticket(performance, owner, noOfSeats);
        TicketValidator validator = new TicketValidator();
        validator.validate(ticket);
        Optional<Ticket> optionalTicket = ticketRepository.save(ticket);
        if (optionalTicket.isPresent()) {
            logger.traceExit();
            notifyTicketBought(ticket);
        }
        else logger.error("Didn't save!");
    }

    private void notifyTicketBought(Ticket ticket) {
        logger.info("Notifying...");
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNumber);
        for(ObserverInterface observer : loggedClients){
            executor.execute(() -> {
                try {
                    logger.info("Notifying.");
                    observer.ticketBought(ticket);
                } catch (ServiceException e) {
                    logger.error(e);
                    System.err.println("Error notifying: " + e);
                }
            });
        }
        logger.traceExit();
        executor.shutdown();
    }
}
