package br.thullyoo.login_user.services;

import br.thullyoo.login_user.dto.request.LoginRequest;
import br.thullyoo.login_user.dto.response.LoginResponse;
import br.thullyoo.login_user.model.User;
import br.thullyoo.login_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authenticatorManager;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticatorManager, TokenService tokenService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticatorManager = authenticatorManager;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest loginRequest){

        User userFromDb = userRepository.findByEmail(loginRequest.email());

        if(userFromDb == null){
            throw new RuntimeException("User not found");
        }

        if (!(userFromDb.isPasswordMatch(loginRequest.password(), passwordEncoder))) {
            throw new RuntimeException("Invalid password");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =   new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticatorManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken(userFromDb);
        return new LoginResponse(token);
    }

}
