package hr.algebra.javaweb.javaweb2026.repository;

import hr.algebra.javaweb.javaweb2026.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserInfo_Name(String name);
    void deleteByToken(String token);
    void deleteByUserInfo_Name(String userInfoName);
}
