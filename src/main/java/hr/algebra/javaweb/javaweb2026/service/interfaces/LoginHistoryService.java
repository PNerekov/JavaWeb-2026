package hr.algebra.javaweb.javaweb2026.service.interfaces;

import hr.algebra.javaweb.javaweb2026.form.HistorySearchForm;
import hr.algebra.javaweb.javaweb2026.model.LoginHistory;

import java.util.List;

public interface LoginHistoryService {

    void saveLogin(String username, String ip);

    List<LoginHistory> findAll();

    List<LoginHistory> findBySearchCriteria(HistorySearchForm historySearchForm);
}
