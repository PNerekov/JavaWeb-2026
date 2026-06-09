package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.form.HistorySearchForm;
import hr.algebra.javaweb.javaweb2026.model.LoginHistory;
import hr.algebra.javaweb.javaweb2026.model.User;
import hr.algebra.javaweb.javaweb2026.repository.LoginHistoryRepository;
import hr.algebra.javaweb.javaweb2026.repository.UserRepository;
import hr.algebra.javaweb.javaweb2026.service.interfaces.LoginHistoryService;
import hr.algebra.javaweb.javaweb2026.specification.LoginHistorySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public void saveLogin(String username, String ip) {
        User user = userRepository.findByName(username);

        if(user == null){
            throw new ResourceNotFoundException("User not found: " + username);
        }

        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(user);
        loginHistory.setIp(ip);
        loginHistory.setLoginTime(LocalDateTime.now());

        loginHistoryRepository.save(loginHistory);
    }

    @Override
    public List<LoginHistory> findAll() {
        return loginHistoryRepository.findAll(
                Sort.by(Sort.Direction.DESC, "loginTime")
        );
    }

    @Override
    public List<LoginHistory> findBySearchCriteria(HistorySearchForm historySearchForm) {
        Specification<LoginHistory> specification =
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction();

        if(historySearchForm.getUsername() != null && !historySearchForm.getUsername().isBlank()){
            specification = specification.and(
                    LoginHistorySpecification.usernameContains(historySearchForm.getUsername().trim())
            );
        }

        if(historySearchForm.getDateFrom() !=null){
            specification = specification.and(
                    LoginHistorySpecification.loginTimeFrom(historySearchForm.getDateFrom())
            );
        }

        if(historySearchForm.getDateTo() != null){
            specification = specification.and(
                    LoginHistorySpecification.loginTimeTo(historySearchForm.getDateTo())
            );
        }

        return loginHistoryRepository.findAll(
                specification,
                Sort.by(Sort.Direction.DESC, "loginTime")
        );
    }
}
