import model.Performance;
import repository.RepositoryPerformance;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartApplication {
    public static void main(String[] args) {
        System.out.println("Am inceput executia!");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RepositoryPerformance repositoryPerformance = new RepositoryPerformance(properties);
        Performance performance = new Performance(LocalDateTime.of(2023, 8, 26, 22, 30), "CJ", 50, 0, "Scorpions");
       // repositoryPerformance.save(performance);
        performance = repositoryPerformance.find(28L).get();
        System.out.println(performance.getDate());
       // RepositoryTicket repositoryTicket = new RepositoryTicket(properties);
       // repositoryTicket.save(new Ticket(performance, "Mara", 2));
       // System.out.println(repositoryTicket.getPerformanceOfTicket(16L).getArtist());
        // repositoryTicket.delete(new Ticket(14L, performance, "Emilia", 2));
        System.out.println(repositoryPerformance.find(23L).get().getNoOfAvailableSeats());
        List<Performance> performances = new ArrayList<>();
        performances = repositoryPerformance.findAllPerformancesForADay(LocalDate.of(2022, 8, 26));
        performances.forEach(x -> System.out.println(x.getPlace()));
    }
}
