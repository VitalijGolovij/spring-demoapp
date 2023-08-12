package ru.example.demoapp.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.example.demoapp.dto.JwtResponse;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.minutesLifeTime}")
    private Integer minutesLifeTime;
    private final Clock clock;

    public JwtResponse generateToken(String username){
        Date exiparationDate = Date.from(LocalDateTime.now(clock).plusMinutes(minutesLifeTime).toInstant(ZoneOffset.UTC));
        String jwt =  JWT.create()
                        .withSubject("UserDetails")
                        .withClaim("username", username)
                        .withIssuedAt(new Date())
                        .withIssuer("demoapp")
                        .withExpiresAt(exiparationDate)
                        .sign(Algorithm.HMAC256(secret));
        return new JwtResponse(jwt, exiparationDate);
    }

    public String getClaim(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("UserDetails")
                .withIssuer("demoapp")
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("username").asString();
    }
}
