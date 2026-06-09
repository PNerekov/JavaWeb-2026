package hr.algebra.javaweb.javaweb2026.service;

import hr.algebra.javaweb.javaweb2026.exeptions.InvalidRefreshTokenException;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.model.RefreshToken;
import hr.algebra.javaweb.javaweb2026.model.User;
import hr.algebra.javaweb.javaweb2026.repository.RefreshTokenRepository;
import hr.algebra.javaweb.javaweb2026.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){

        refreshTokenRepository.deleteByUserInfo_Name(username);

        User user = userRepository.findByName(username);

        if (user == null) {
            throw new ResourceNotFoundException("User not found: " + username);
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if(refreshToken.isPresent()){
            refreshTokenRepository.delete(refreshToken.get());
        } else {
            throw new InvalidRefreshTokenException("Refresh token was not found.");
        }
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new InvalidRefreshTokenException("Refresh token has expired for user: " + token.getUserInfo().getName());
        }
        return token;
    }

    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUserInfo_Name(username);
    }

}