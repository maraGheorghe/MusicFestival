package start;

import client_rest.PerformanceClient;
import model.Performance;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;

public class StartRestClient {
    private final static PerformanceClient client = new PerformanceClient();
    public static void main(String[] args) {
        Performance testPerformance =
                new Performance(LocalDateTime.now(), "place",50, 50, "Artist");
        Performance updatePerformance =
                new Performance(43L, LocalDateTime.now(), "Java updated",50, 50, "Java updated");
        try{
            show(()-> System.out.println("\nPERFORMANCE ADDED:\n" + client.create(testPerformance)));
            show(() -> {
                client.delete(54L);
                System.out.println("\nPERFORMANCE DELETED\n");
            });
            show(() -> {
                client.update(updatePerformance);
                System.out.println("\nPERFORMANCE UPDATED\n");
            });
            System.out.println("\nPERFORMANCE WITH ID 23:");
            show(()-> System.out.println(client.getByID(23L)));
            show(()->{
                Performance[] results = client.getAll();
                System.out.println("\nALL PERFORMANCES:");
                for(Performance performance : results){
                    System.out.println(performance);
                }
            });
        } catch(RestClientException ex){
            System.out.println("Exception ... " + ex.getMessage());
        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (RuntimeException e) {
            System.out.println("EXCEPTION: " + e);
        }
    }
}
