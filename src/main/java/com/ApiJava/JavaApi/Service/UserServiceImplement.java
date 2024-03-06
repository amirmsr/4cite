package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Login;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.UserRepository;
import com.ApiJava.JavaApi.Utils.JwtToken;
import com.auth0.jwt.JWT;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImplement {

  private final UserRepository userRepository;

  @Autowired
  private JwtToken jwtTokenClass;

  public List<User> get(String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole() == 1) {
      return userRepository.findAll();
    }

    throw new BadRequestException("Unauthorized");
  }


  public User post(User newUser) throws BadRequestException {
    boolean existMail = userRepository.existsByMail(newUser.getMail());
    if (existMail) {
      throw new BadRequestException("email existe deja" + newUser.getMail());
    }

    return userRepository.save(newUser);
  }

  public String login(Login login) {
    String connectionStatus = "not logged";

    Optional<User> user = userRepository.findByMail(login.getMail());

    if (user.isEmpty()) {
      connectionStatus = "user not found";
    } else {
      if (user.get().getPassword().equals(login.getPassword())) {
        String jwtToken = JWT.create()
            .withIssuer("Baeldung")
            .withClaim("userId", user.get().getId())
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000L))
            .withJWTId(UUID.randomUUID()
                .toString())
            .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
            .sign(jwtTokenClass.getAlgorithm());

        connectionStatus = jwtToken;
      }
    }

    return connectionStatus;
  }

  public User getById(Long id, String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole() == 1 || user.get().getId().equals(id))) {
      return userRepository.findById(id)
          .orElseThrow(() -> new BadRequestException("id non trouvé " + id));
    }

    throw new BadRequestException("Unauthorized " + jwtTokenClass.getErrorMessage());
  }

  public User updateById(Long id, User updatedUser, String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole() == 1 || user.get().getId().equals(id))) {
      User existingUser = userRepository.findById(id)
          .orElseThrow(() -> new BadRequestException("User not found with id: " + id));

      existingUser.setName(updatedUser.getName());
      existingUser.setMail(updatedUser.getMail());
      existingUser.setPassword(updatedUser.getPassword());

      return userRepository.save(existingUser);
    }

    throw new BadRequestException("Unauthorized " + jwtTokenClass.getErrorMessage());
  }

  public void deleteById(Long id, String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole() == 1 || user.get().getId().equals(id))) {
      // Check if the user with the given ID exists
      if (!userRepository.existsById(id)) {
        throw new BadRequestException("User not found with id: " + id);
      }

      // Delete the user by ID
      userRepository.deleteById(id);
    }

    throw new BadRequestException("Unauthorized " + jwtTokenClass.getErrorMessage());
  }
}
