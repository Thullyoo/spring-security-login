package br.thullyoo.login_user.controller;

import br.thullyoo.login_user.dto.request.LoginRequest;
import br.thullyoo.login_user.dto.response.LoginResponse;
import br.thullyoo.login_user.services.AuthService;
import br.thullyoo.login_user.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<LoginResponse> login (@RequestBody @Valid LoginRequest loginRequest){
        LoginResponse token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }
}
