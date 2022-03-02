using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository;

public interface IRepositoryPerformance: IRepository<Performance>
{
    List<Performance> FindAllPerformancesForADay(DateOnly date);
}