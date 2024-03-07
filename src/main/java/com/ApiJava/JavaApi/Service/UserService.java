package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Model.UserRoleEnum;
import com.ApiJava.JavaApi.Repository.UserRepository;
import com.ApiJava.JavaApi.Service.mapper.UserMapper;
import com.ApiJava.JavaApi.Utils.JwtUtil;
import com.auth0.jwt.JWT;
import com.ApiJava.JavaApi.model.JwtToken;
import com.ApiJava.JavaApi.model.Login;
import com.ApiJava.JavaApi.model.UserDetails;
import com.ApiJava.JavaApi.model.UserRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  private final JwtUtil jwtUtilClass;

  public UserService(UserRepository userRepository, UserMapper userMapper, JwtUtil jwtUtilClass) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.jwtUtilClass = jwtUtilClass;
  }

  public UserDetails post(UserRequest userRequest) {
    boolean existMail = userRepository.existsByMail(userRequest.getMail());
    if (existMail) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "email existe deja" + userRequest.getMail());
    }
    User user = userMapper.toEntity(userRequest);
    User newUser = userRepository.save(user);
    return userMapper.toResource(newUser);
  }

  public void deleteById(Long id, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getId().equals(id))) {
      // Check if the user with the given ID exists
      if (!userRepository.existsById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
      }

      // Delete the user by ID
      userRepository.deleteById(id);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
  }

  public List<UserDetails> get(String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole().equals(UserRoleEnum.ADMIN)) {
      return userMapper.toResource(userRepository.findAll());
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
  }

  public UserDetails getById(Long id, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getId().equals(id))) {
      Optional<User> userGet = userRepository.findById(id);
      if (userGet.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
      }
      return userMapper.toResource(userGet.get());
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
  }


  public JwtToken login(Login login) {
    Optional<User> user = userRepository.findByMail(login.getMail());

    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with mail: " + login.getMail());
    }

    if (user.get().getPassword().equals(login.getPassword())) {
      JwtToken token = new JwtToken();
      token.setToken(jwtUtilClass.createToken(user.get().getId()));
      return token;
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "incorrect password");
  }

  public UserDetails updateById(Long id, String token, UserRequest userRequest) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);


    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getId().equals(id))) {
      User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

      existingUser.setName(userRequest.getName());
      existingUser.setMail(userRequest.getMail());
      existingUser.setPassword(userRequest.getPassword());

      return userMapper.toResource(userRepository.save(existingUser));
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
  }
}
