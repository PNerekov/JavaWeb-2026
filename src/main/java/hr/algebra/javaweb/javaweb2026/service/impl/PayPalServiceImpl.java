package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.configuration.PayPalProperties;
import hr.algebra.javaweb.javaweb2026.dto.paypal.PayPalAccessTokenResponse;
import hr.algebra.javaweb.javaweb2026.dto.paypal.PayPalCaptureResponse;
import hr.algebra.javaweb.javaweb2026.dto.paypal.PayPalCreateOrderResponse;
import hr.algebra.javaweb.javaweb2026.service.interfaces.PayPalService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PayPalServiceImpl implements PayPalService {

    private static final String INTENT_CAPTURE = "CAPTURE";
    private static final String CURRENCY = "EUR";
    private static final String STATUS_COMPLETED = "COMPLETED";

    private final PayPalProperties payPalProperties;

    @Override
    public String createOrder(BigDecimal totalPrice) {
        String accessToken = getAccessToken();

        Map<String, Object> requestBody = Map.of(
                "intent", INTENT_CAPTURE,
                "purchase_units", List.of(
                        Map.of(
                                "amount", Map.of(
                                        "currency_code", CURRENCY,
                                        "value", formatAmount(totalPrice)
                                )
                        )
                )
        );

        PayPalCreateOrderResponse response = RestClient.create(payPalProperties.baseUrl())
                .post()
                .uri("/v2/checkout/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .body(requestBody)
                .retrieve()
                .body(PayPalCreateOrderResponse.class);

        if(response == null || response.id() == null){
            throw new IllegalStateException("PayPal order could not be created");
        }

        return response.id();
    }

    @Override
    public void captureOrder(String paypalOrderId) {
        String accessToken = getAccessToken();

        PayPalCaptureResponse response = RestClient.create(payPalProperties.baseUrl())
                .post()
                .uri("v2/checkout/orders/{orderId}/capture", paypalOrderId)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .body(PayPalCaptureResponse.class);

        if(response == null || !STATUS_COMPLETED.equals(response.status())){
            throw new IllegalStateException("PayPal payment was not compleated");
        }
    }

    private String getAccessToken(){
        LinkedMultiValueMap<String, String> fromData = new LinkedMultiValueMap<>();
        fromData.add("grant_type", "client_credentials");

            PayPalAccessTokenResponse response = RestClient.create(payPalProperties.baseUrl())
                .post()
                .uri("/v1/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .headers(headers -> headers.setBasicAuth(
                        payPalProperties.clientId(),
                        payPalProperties.clientSecret()
                ))
                .body(fromData)
                .retrieve()
                .body(PayPalAccessTokenResponse.class);

        if(response == null || response.accessToken() == null){
            throw new IllegalStateException("PayPal access token could not be created");
        }
        return response.accessToken();
    }

    private String formatAmount(BigDecimal amount){
        return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
