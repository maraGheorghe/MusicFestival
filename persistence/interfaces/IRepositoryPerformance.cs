using System;
using System.Collections.Generic;
using model;

namespace persistence.interfaces;

public interface IRepositoryPerformance: IRepository<Performance>
{
    IList<Performance> FindAllPerformancesForADay(DateTime date);
}