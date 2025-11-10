package br.thullyoo.login_user.services;

import br.thullyoo.login_user.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private String secretKey = "secret";

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(3000))
                .withIssuer(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("email", user.getEmail())
                .sign(algorithm);
    }



}
