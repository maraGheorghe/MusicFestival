package repository.hibernateRepositoryes;

import model.Performance;
import model.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SessionFactoryUtil;
import repository.interfaces.RepositoryInterfaceTicket;

import java.util.List;
import java.util.Optional;

public class RepositoryTicketHibernate implements RepositoryInterfaceTicket {

    @Override
    public Optional<Ticket> save(Ticket entity) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                return Optional.empty();
            }
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Ticket> find(Long ID) {
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> delete(Ticket entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> update(Ticket entity) {
        return Optional.empty();
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public Performance getPerformanceOfTicket(Long ticketID) {
        return null;
    }
}
