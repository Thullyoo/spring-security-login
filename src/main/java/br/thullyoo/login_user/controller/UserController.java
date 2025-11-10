package br.thullyoo.login_user.controller;

import br.thullyoo.login_user.dto.request.RegisterRequest;
import br.thullyoo.login_user.dto.response.RegisterResponse;
import br.thullyoo.login_user.repository.UserRepository;
import br.thullyoo.login_user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request){
        RegisterResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "This is a private endpoint accessible only to authenticated users.";
    }
}
