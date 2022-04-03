package services;

import model.Performance;
import model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ServiceInterface {

    void login(String username, String password, ObserverInterface client) throws ServiceException;
    void logout(User user, ObserverInterface client) throws ServiceException;
    List<Performance> findAllPerformances() throws ServiceException;
    List<Performance> findAllPerformancesForADay(LocalDate date) throws ServiceException;
    void saveTicket(Long performanceID, LocalDateTime date, String place,
                    Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist,
                    String owner, int noOfSeats) throws ServiceException;
}
