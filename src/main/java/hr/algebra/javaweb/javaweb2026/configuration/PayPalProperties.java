package hr.algebra.javaweb.javaweb2026.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "paypal")
public record PayPalProperties(
        String clientId,
        String clientSecret,
        String baseUrl
) {
}
