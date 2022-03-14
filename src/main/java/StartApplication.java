import model.Performance;
import model.Ticket;
import repository.RepositoryPerformance;
import repository.RepositoryTicket;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class StartApplication {
    public static void main(String[] args) {
        System.out.println("Am inceput executia!");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RepositoryPerformance repositoryPerformance = new RepositoryPerformance(properties);
        Performance performance = new Performance(LocalDate.of(2022, 8, 26), "Bontida", 20, 0, "Scorpions");
       // repositoryPerformance.save(performance);
        performance = repositoryPerformance.find(2L).get();
        System.out.println(performance.getArtist());
        RepositoryTicket repositoryTicket = new RepositoryTicket(properties);
       // repositoryTicket.save(new Ticket(performance, "Stefan", 3));
        System.out.println(repositoryTicket.getPerformanceOfTicket(9L).getArtist());
        // repositoryTicket.delete(new Ticket(14L, performance, "Emilia", 2));
        System.out.println(repositoryPerformance.find(2L).get().getNoOfAvailableSeats());
    }
}
