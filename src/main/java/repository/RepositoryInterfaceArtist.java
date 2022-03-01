package repository;

import model.Artist;
import model.Performance;
import java.util.List;

public interface RepositoryInterfaceArtist extends RepositoryInterface<Artist> {

    List<Performance> findPerformancesForAnArtist(Long ID);
}
