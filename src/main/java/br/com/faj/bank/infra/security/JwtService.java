package br.com.faj.bank.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    public String rsaPrivateKey;

    public JwtService(@Value("${jwt.secret}") String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String encode(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(rsaPrivateKey);
            return JWT.create()
                    .withIssuer("auth0-fajbank")
                    .withSubject(email)
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Invalid Signing configuration / Couldn't convert Claims.", exception);
        }
    }

    public @Nullable String verify(String token) {
        try {
           Algorithm algorithm = Algorithm.HMAC256(rsaPrivateKey);
           return JWT.require(algorithm)
                   .withIssuer("auth0-fajbank")
                   .build()
                   .verify(token)
                   .getSubject();
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    private Date getExpirationDate() {
        LocalDateTime expiration = LocalDateTime.now().plusHours(2);
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

}
