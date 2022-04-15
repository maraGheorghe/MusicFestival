using model;

namespace persistence.interfaces;

public interface IRepositoryTicket: IRepository<Ticket>
{
    Performance GetPerformanceOfTicket(long ticketId);
}