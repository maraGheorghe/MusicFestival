using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository.interfaces;

public interface IRepositoryTicket: IRepository<Ticket>
{
    Performance GetPerformanceOfTicket(long ticketId);
}