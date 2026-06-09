package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.exeptions.RegistrationException;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.model.Role;
import hr.algebra.javaweb.javaweb2026.model.User;
import hr.algebra.javaweb.javaweb2026.model.UserRegistrationDTO;
import hr.algebra.javaweb.javaweb2026.repository.RoleRepository;
import hr.algebra.javaweb.javaweb2026.repository.UserRepository;
import hr.algebra.javaweb.javaweb2026.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegistrationDTO userRegistrationDTO) {
        String username = userRegistrationDTO.getName().trim();
        String email = userRegistrationDTO.getEmail().trim();

        validateRegistration(userRegistrationDTO, username, email);

        Role userRole = roleRepository.findByName(DEFAULT_USER_ROLE);

        if(userRole == null){
            throw new ResourceNotFoundException("Default user role was not found");
        }

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(List.of(userRole));

        userRepository.save(user);
    }

    private void validateRegistration(UserRegistrationDTO userRegistrationDTO,
                                      String username,
                                      String email){
        if(!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())){
            throw new RegistrationException("Password do not match");
        }

        if(userRepository.findByName(username) != null){
            throw new RegistrationException("Username is already taken");
        }

        if(userRepository.findByEmail(email) != null){
            throw new RegistrationException("Email is already registered");
        }
    }
}
