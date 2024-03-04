package com.ApiJava.JavaApi.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtToken {
    private Algorithm algorithm = Algorithm.HMAC256("baeldung");
    private JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("Baeldung")
            .build();

    private String errorMessage = "";

    public Long getUserIdFrom(String token) {
        DecodedJWT decodedJWT = null;
        Claim claim = null;

        try {
            decodedJWT = verifier.verify(token);
            claim = decodedJWT.getClaim("userId");
        } catch (JWTVerificationException e) {
            errorMessage = e.getMessage();
        }

        if (decodedJWT != null && claim != null) {
            return claim.asLong();
        }

        return Long.valueOf(0);
    }
}