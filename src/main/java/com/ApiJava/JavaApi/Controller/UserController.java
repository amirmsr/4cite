package com.ApiJava.JavaApi.Controller;


import com.ApiJava.JavaApi.Model.Login;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Service.UserServiceImplement;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class UserController {
    private UserServiceImplement userServiceImplement;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @CrossOrigin
    @GetMapping
    public List<User> read(){
        return userServiceImplement.get();
    }

    @GetMapping(value = "/{id}")
    public User read(@PathVariable("id") Long id, @RequestHeader String token){
        try {
            return userServiceImplement.getById(id, token);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
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
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User updateUser){
        try{
            User result = userServiceImplement.updateById(id, updateUser);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (BadRequestException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id){
        try{
            userServiceImplement.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (BadRequestException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }




}
