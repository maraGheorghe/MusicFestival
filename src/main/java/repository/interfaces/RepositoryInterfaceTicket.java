package repository.interfaces;

import model.Performance;
import model.Ticket;

public interface RepositoryInterfaceTicket extends RepositoryInterface<Ticket> {

    Performance getPerformanceOfTicket(Long ticketID);
}
