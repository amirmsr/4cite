package com.ApiJava.JavaApi.Utils;

import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.UserRepository;
import com.ApiJava.JavaApi.model.JwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtUtil {

  private final UserRepository userRepository;
  private Algorithm algorithm = Algorithm.HMAC256("baeldung");
  private JWTVerifier verifier = JWT.require(algorithm)
      .withIssuer("Baeldung")
      .build();
  private String errorMessage = "";

  public JwtUtil(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

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

  public Optional<User> getUserFrom(String token) {
    Long userId = getUserIdFrom(token);
    return userRepository.findById(userId);
  }

  public String createToken(Long userId) {
    return JWT.create()
        .withIssuer("Baeldung")
        .withClaim("userId", userId)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000L))
        .withJWTId(UUID.randomUUID()
            .toString())
        .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
        .sign(this.getAlgorithm());
  }
}