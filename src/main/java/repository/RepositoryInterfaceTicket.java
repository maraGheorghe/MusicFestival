package repository;

import model.Performance;
import model.Ticket;

import java.util.Optional;

public interface RepositoryInterfaceTicket extends RepositoryInterface<Ticket> {

    Optional<Performance> getTicketsPerformance(Long ticketID);
}
