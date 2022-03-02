using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository;

public interface IRepositoryArtist: IRepository<Artist>
{
    List<Performance> FindPerformancesForAnArtist(long id);
}