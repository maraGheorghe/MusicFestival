package services;

import model.Ticket;

public interface ObserverInterface {

    void ticketBought(Ticket ticket) throws ServiceException;
}
