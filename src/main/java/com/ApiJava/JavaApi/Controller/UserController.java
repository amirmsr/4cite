package com.ApiJava.JavaApi.Controller;


import com.ApiJava.JavaApi.Service.UserService;
import com.ApiJava.JavaApi.UsersApi;
import com.ApiJava.JavaApi.model.JwtToken;
import com.ApiJava.JavaApi.model.Login;
import com.ApiJava.JavaApi.model.UserDetails;
import com.ApiJava.JavaApi.model.UserRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController implements UsersApi {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<UserDetails> createUser(UserRequest userRequest) {
    UserDetails savedUser = userService.post(userRequest);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> deleteUser(Integer id, String token) {
    userService.deleteById(Long.valueOf(id), token);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<UserDetails>> getAllUsers(String token) {
    List<UserDetails> users = userService.get(token);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<UserDetails> getUserById(Integer id, String token) {
    UserDetails userDetails = userService.getById(Long.valueOf(id), token);
    return new ResponseEntity<>(userDetails, HttpStatus.OK);
  }


  @Override
  public ResponseEntity<JwtToken> logUserIn(Login login) {
    JwtToken token = userService.login(login);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }


  @Override
  public ResponseEntity<UserDetails> updateUser(
      Integer id,
      String token,
      UserRequest userRequest
  ) {
    UserDetails result = userService.updateById(Long.valueOf(id), token, userRequest);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
