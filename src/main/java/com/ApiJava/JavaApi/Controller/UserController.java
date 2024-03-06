package com.ApiJava.JavaApi.Controller;


import com.ApiJava.JavaApi.Model.Login;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Service.UserServiceImplement;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class UserController {

  private UserServiceImplement userServiceImplement;

  @CrossOrigin
  @GetMapping
  public List<User> read(@RequestHeader String token) throws BadRequestException {
    return userServiceImplement.get(token);
  }

  @GetMapping(value = "/{id}")
  public User read(@PathVariable("id") Long id, @RequestHeader String token) throws BadRequestException {
    return userServiceImplement.getById(id, token);
  }


  @PostMapping("/signin")
  public ResponseEntity<User> create(@RequestBody User newUser) {
    try {
      User savedUser = userServiceImplement.post(newUser);
      return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } catch (BadRequestException e) {
      // Grer l exception BadRequestException et renvoyer une réponse HTTP appropriée
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Login login) {
    return new ResponseEntity<>(userServiceImplement.login(login), HttpStatus.OK);
  }


  @PutMapping(value = "/{id}")
  public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User updateUser, @RequestHeader String token) throws BadRequestException {
    User result = userServiceImplement.updateById(id, updateUser, token);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestHeader String token) throws BadRequestException {
    userServiceImplement.deleteById(id, token);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
