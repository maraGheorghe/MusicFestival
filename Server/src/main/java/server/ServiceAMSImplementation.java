package server;

import model.Performance;
import model.Ticket;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.RepositoryInterfacePerformance;
import repository.interfaces.RepositoryInterfaceTicket;
import repository.interfaces.RepositoryInterfaceUser;
import services.INotificationService;
import services.IServiceAMS;
import services.ObserverInterface;
import services.ServiceException;
import validators.TicketValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ServiceAMSImplementation implements IServiceAMS {
    private RepositoryInterfaceUser userRepository;
    private RepositoryInterfacePerformance performanceRepository;
    private RepositoryInterfaceTicket ticketRepository;
    private INotificationService notificationService;

    public ServiceAMSImplementation(RepositoryInterfaceUser userRepository, RepositoryInterfacePerformance performanceRepository, RepositoryInterfaceTicket ticketRepository, INotificationService notificationService) {
        this.userRepository = userRepository;
        this.performanceRepository = performanceRepository;
        this.ticketRepository = ticketRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void login(String username, String password) throws ServiceException {
        Optional<User> optionalUser = userRepository.checkConnection(username, password);
        if(optionalUser.isEmpty())
            throw new ServiceException("Username and password do not match! Please try again.");
    }

    @Override
    public void logout(User user) throws ServiceException {

    }

    @Override
    public List<Performance> findAllPerformances() throws ServiceException {
        return performanceRepository.findAll();
    }

    @Override
    public List<Performance> findAllPerformancesForADay(LocalDate date) throws ServiceException {
        return performanceRepository.findAllPerformancesForADay(date);
    }

    @Override
    public void saveTicket(Long performanceID, LocalDateTime date, String place, Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist, String owner, int noOfSeats) throws ServiceException {
        Performance performance = new Performance(performanceID, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
        Ticket ticket = new Ticket(performance, owner, noOfSeats);
        TicketValidator validator = new TicketValidator();
        validator.validate(ticket);
        Optional<Ticket> optionalTicket = ticketRepository.save(ticket);
        if (optionalTicket.isPresent())
            notificationService.ticketBought(ticket);
    }
}
