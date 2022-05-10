package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@ComponentScan({"repository", "rest"})
@SpringBootApplication
public class StartRestServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }

    @Bean(name="properties")
    public Properties getDatabaseProperties() {
        Properties properties = new Properties();
        try {
            properties.load(StartRestServices.class.getResourceAsStream("/bd.config"));
        } catch (IOException e) {
            System.err.println("Configuration file bd.config not found" + e);
        }
        return properties;
    }
}
