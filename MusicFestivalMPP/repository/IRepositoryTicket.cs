using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository;

public interface IRepositoryTicket: IRepository<Ticket>
{
    Performance GetTicketsPerformance(long ticketId);
}