package br.thullyoo.login_user.services;

import br.thullyoo.login_user.dto.request.RegisterRequest;
import br.thullyoo.login_user.dto.response.RegisterResponse;
import br.thullyoo.login_user.model.User;
import br.thullyoo.login_user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerUser(@Valid RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return new RegisterResponse(user.getEmail(), user.getPassword());
    }
}
