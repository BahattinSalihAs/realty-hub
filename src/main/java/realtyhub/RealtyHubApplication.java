package realtyhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RealtyHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealtyHubApplication.class, args);
    }

}
