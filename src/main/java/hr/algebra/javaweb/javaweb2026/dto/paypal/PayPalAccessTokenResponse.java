package hr.algebra.javaweb.javaweb2026.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayPalAccessTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
