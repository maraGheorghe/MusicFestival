import model.Performance;
import repository.RepositoryPerformance;

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
        repositoryPerformance.save(new Performance(LocalDate.of(2022, 8, 26), "Bontida", 200, 2000, "Scorpions"));
    }
}
