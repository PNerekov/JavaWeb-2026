package hr.algebra.javaweb.javaweb2026.listener;

import hr.algebra.javaweb.javaweb2026.service.interfaces.LoginHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@AllArgsConstructor
public class LoginSuccessListener {

    private static final String UNKNOWN_IP = "unknown";

    private final LoginHistoryService loginHistoryService;

    @EventListener
    public void onLoginSuccess(InteractiveAuthenticationSuccessEvent event){
        String username = event.getAuthentication().getName();
        String ip = getIp();

        loginHistoryService.saveLogin(username, ip);
    }

    private String getIp() {
        if(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes){
            return attributes.getRequest().getRemoteAddr();
        }

        return UNKNOWN_IP;
    }

}
