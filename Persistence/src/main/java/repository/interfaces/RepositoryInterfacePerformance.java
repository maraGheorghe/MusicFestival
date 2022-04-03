package repository.interfaces;

import model.Performance;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryInterfacePerformance extends RepositoryInterface<Performance> {

    List<Performance> findAllPerformancesForADay(LocalDate date);
}
