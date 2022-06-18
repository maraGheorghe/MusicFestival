package client_rest;

import model.Performance;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class PerformanceClient {
    public static final String URL = "http://localhost:8080/music-festival/performances";
    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Performance[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Performance[].class));
    }

    public Performance getByID(Long ID) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%d", URL, ID), Performance.class));
    }

    public Performance create(Performance performance) {
        return execute(() -> restTemplate.postForObject(URL, performance, Performance.class));
    }

    public Performance update(Performance performance) {
        return execute(() -> {
            restTemplate.put(String.format("%s/%d", URL, performance.getID()), performance);
            return null;
        });
    }

    public Performance delete(Long ID) {
        return execute(() -> {
           restTemplate.delete(String.format("%s/%d", URL, ID));
            return null;
        });
    }
}
