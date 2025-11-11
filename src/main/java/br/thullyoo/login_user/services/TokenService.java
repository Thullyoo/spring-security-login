package br.thullyoo.login_user.services;

import br.thullyoo.login_user.config.JWTUserData;
import br.thullyoo.login_user.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

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

    public Optional<JWTUserData> validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(new JWTUserData(decodedJWT.getClaim("userId").asLong(),
                    decodedJWT.getClaim("email").asString()));

        }catch (JWTVerificationException ex){
            throw new RuntimeException("Invalid token");


        }
    }



}
