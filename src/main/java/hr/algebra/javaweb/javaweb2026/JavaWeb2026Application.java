package hr.algebra.javaweb.javaweb2026;

import hr.algebra.javaweb.javaweb2026.configuration.PayPalProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PayPalProperties.class)
public class JavaWeb2026Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaWeb2026Application.class, args);
    }

}
