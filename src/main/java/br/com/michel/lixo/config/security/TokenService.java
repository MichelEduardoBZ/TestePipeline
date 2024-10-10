package br.com.michel.lixo.config.security;

import br.com.michel.lixo.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${my.secret.key}")
    private String secretWord;

    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretWord);

            String token = JWT.create()
                    .withIssuer("trocaAgua")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException("Unable to generate token!");
        }

    }

    public String validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretWord);

            return JWT.require(algorithm)
                    .withIssuer("trocaAgua")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            return "";
        }

    }
    private Instant generateExpirationDate(){
        return LocalDateTime
                .now()
                .plusHours(12)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}