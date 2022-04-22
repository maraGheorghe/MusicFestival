import model.DateToString;
import model.Performance;
import model.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private static SessionFactory sessionFactory;

    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie: " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static void main(String ... arg) {
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Performance> tickets =
                            session.createQuery("from Performance as t where t.date >= :date1 and t.date <= :date2", Performance.class)
                                    .setParameter("date1", LocalDateTime.of(2022, 8, 26, 0, 0))
                                    .setParameter("date2", LocalDateTime.of(2022, 8, 26, 23, 59)).list();
                                    System.out.println( tickets.size() + " ticket(s) found:" );
                    for (var t : tickets )
                        System.out.println( t.toString() );
                    tx.commit();
                } catch(RuntimeException ex){
                    if (tx != null)
                        tx.rollback();
                }
            }
            close();
    }
}
