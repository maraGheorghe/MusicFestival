package repository.hibernateRepositories;

import model.Performance;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.jdbc.SessionFactoryUtil;
import repository.interfaces.RepositoryInterfacePerformance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryPerformanceHibernate implements RepositoryInterfacePerformance {
    @Override
    public Optional<Performance> save(Performance entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Performance> find(Long ID) {
        return Optional.empty();
    }

    @Override
    public Optional<Performance> delete(Performance entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Performance> update(Performance entity) {
        return Optional.empty();
    }

    @Override
    public List<Performance> findAll() {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Performance> performances =
                        session.createQuery("from Performance as p", Performance.class).list();
                tx.commit();
                return performances;
            } catch(RuntimeException ex){
                if (tx != null)
                    tx.rollback();
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Performance> findAllPerformancesForADay(LocalDate localDate) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Performance> performances =
                        session.createQuery("from Performance as p where p.date >= :date1 and p.date <= :date2", Performance.class)
                                .setParameter("date1", LocalDateTime.of(localDate, LocalTime.of(0, 0)))
                                .setParameter("date2", LocalDateTime.of(localDate, LocalTime.of(23, 59))).list();
                tx.commit();
                return performances;
            } catch(RuntimeException ex){
                if (tx != null)
                    tx.rollback();
                return new ArrayList<>();
            }
        }
    }
}
