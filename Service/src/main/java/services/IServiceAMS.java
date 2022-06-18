package services;

import model.Performance;
import model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IServiceAMS {
    void login(String username, String password) throws ServiceException;
    void logout(User user) throws ServiceException;
    List<Performance> findAllPerformances() throws ServiceException;
    List<Performance> findAllPerformancesForADay(LocalDate date) throws ServiceException;
    void saveTicket(Long performanceID, LocalDateTime date, String place,
                    Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist,
                    String owner, int noOfSeats) throws ServiceException;
}
