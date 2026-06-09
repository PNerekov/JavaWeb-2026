package hr.algebra.javaweb.javaweb2026.repository;

import hr.algebra.javaweb.javaweb2026.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long>,
        JpaSpecificationExecutor<LoginHistory> {
}
