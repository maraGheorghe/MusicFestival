using System;
using System.Collections.Generic;
using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository.interfaces;

public interface IRepositoryPerformance: IRepository<Performance>
{
    IList<Performance> FindAllPerformancesForADay(DateTime date);
}