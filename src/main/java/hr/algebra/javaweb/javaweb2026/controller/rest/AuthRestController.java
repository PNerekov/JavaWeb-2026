package hr.algebra.javaweb.javaweb2026.controller.rest;



import hr.algebra.javaweb.javaweb2026.dto.AuthRequestDTO;
import hr.algebra.javaweb.javaweb2026.dto.JwtResponseDTO;
import hr.algebra.javaweb.javaweb2026.dto.RefreshTokenRequestDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.InvalidRefreshTokenException;
import hr.algebra.javaweb.javaweb2026.model.RefreshToken;
import hr.algebra.javaweb.javaweb2026.service.JwtService;
import hr.algebra.javaweb.javaweb2026.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()
                )
        );

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());

        return JwtResponseDTO.builder()
                .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getName());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new InvalidRefreshTokenException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDTO.getToken());
    }

}
