package com.ApiJava.JavaApi.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Model.UserRoleEnum;
import com.ApiJava.JavaApi.model.UserDetails;
import com.ApiJava.JavaApi.model.UserRequest;
import com.ApiJava.JavaApi.model.UserRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseControllerTest {

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void createUser() throws Exception {
    // Path to be called
    String resourcePath = "/users";

    // data initialization
    UserRequest userRequest = createNewUser();
    User createdUser = mockCreatedUser();

    // Mocking repositories
    when(userRepository.save(any(User.class))).thenReturn(createdUser);

    // Call Controller
    MvcResult mvcResult = callController(HttpMethod.POST, resourcePath, userRequest);

    // getting controller response
    MockHttpServletResponse response = mvcResult.getResponse();
    User userDetails = new ObjectMapper().readValue(response.getContentAsString(), User.class);

    // assertions
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals(userRequest.getRole().getValue(), userDetails.getRole().getValue(), "users role are not the same");
    assertEquals(userRequest.getPassword(), userDetails.getPassword(), "users passwords are not the same");
    assertEquals(userRequest.getMail(), userDetails.getMail(), "users mails are not the same");
    assertEquals(userRequest.getName(), userDetails.getName(), "users names are not the same");
    assertEquals(createdUser.getId(), userDetails.getId(), "users Ids are not the same");
  }

  @Test
  void deleteUser() throws Exception {
    User userAdmin = mockCreatedUser();
    User userToDelete = mockCreatedUser();

    userAdmin.setId(1L);
    userToDelete.setId(2L);

    String resourcePath = "/users/" + userToDelete.getId();

    String token = createToken(userAdmin.getId());

    when(userRepository.findById(userToDelete.getId())).thenReturn(Optional.of(userToDelete));
    when(userRepository.findById(userAdmin.getId())).thenReturn(Optional.of(userAdmin));
    doNothing().when(userRepository).delete(userToDelete);

    when(userRepository.existsById(any(Long.class))).thenReturn(true);

    MvcResult mvcResult = callController(HttpMethod.DELETE, resourcePath, null, token);

    MockHttpServletResponse response = mvcResult.getResponse();

    assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    verify(userRepository, times(1)).deleteById(userToDelete.getId());
  }

  @Test
  void getAllUsers() throws Exception {
    String resourcePath = "/users";

    List<User> users = new ArrayList<>();

    User userAdmin = mockCreatedUser();
    User otherUser = mockCreatedUser();

    userAdmin.setId(1L);
    otherUser.setId(2L);

    users.add(userAdmin);
    users.add(otherUser);

    String token = createToken(userAdmin.getId());

    when(userRepository.findById(userAdmin.getId())).thenReturn(Optional.of(userAdmin));
    when(userRepository.findAll()).thenReturn(users);

    MvcResult mvcResult = callController(HttpMethod.GET, resourcePath, null, token);
    MockHttpServletResponse response = mvcResult.getResponse();

    List<UserDetails> userDetails = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<UserDetails>>() {});

    int index = 0;
    for (UserDetails userDetail : userDetails) {
      assertEquals(userDetails.get(index).getMail(), userDetail.getMail());
      assertEquals(userDetails.get(index).getName(), userDetail.getName());
      assertEquals(userDetails.get(index).getRole().getValue(), userDetail.getRole().getValue());
      assertEquals(userDetails.get(index).getPassword(), userDetail.getPassword());
      index++;
    }
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  void getById() throws Exception {
    User userAdmin = mockCreatedUser();
    User otherUser = mockCreatedUser();

    userAdmin.setId(1L);
    otherUser.setId(2L);

    String resourcePath = "/users/" + otherUser.getId();

    String token = createToken(userAdmin.getId());

    when(userRepository.findById(userAdmin.getId())).thenReturn(Optional.of(userAdmin));
    when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));

    MvcResult mvcResult = callController(HttpMethod.GET, resourcePath, null, token);
    MockHttpServletResponse response = mvcResult.getResponse();

    UserDetails userDetails = new ObjectMapper().readValue(response.getContentAsString(), UserDetails.class);

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(otherUser.getMail(), userDetails.getMail());
    assertEquals(otherUser.getName(), userDetails.getName());
    assertEquals(otherUser.getRole().getValue(), userDetails.getRole().getValue());
    assertEquals(otherUser.getPassword(), userDetails.getPassword());
  }

  @Test
  void updateUser() throws Exception {
    User userAdmin = mockCreatedUser();
    User userToUpdate = mockCreatedUser();

    userAdmin.setId(1L);
    userToUpdate.setId(2L);

    String resourcePath = "/users/" + userToUpdate.getId();

    String token = createToken(userAdmin.getId());

    UserRequest userRequest = createNewUser();

    when(userRepository.findById(userAdmin.getId())).thenReturn(Optional.of(userAdmin));
    when(userRepository.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
    when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

    MvcResult mvcResult = callController(HttpMethod.PUT, resourcePath, userRequest, token);
    MockHttpServletResponse response = mvcResult.getResponse();

    UserDetails userDetails = new ObjectMapper().readValue(response.getContentAsString(), UserDetails.class);

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(userRequest.getMail(), userDetails.getMail());
    assertEquals(userRequest.getName(), userDetails.getName());
    assertEquals(userRequest.getRole().getValue(), userDetails.getRole().getValue());
    assertEquals(userRequest.getPassword(), userDetails.getPassword());
  }

  private UserRequest createNewUser() {
    UserRequest newUser = new UserRequest();
    newUser.setMail("mail");
    newUser.setName("name");
    newUser.setPassword("password");
    newUser.setRole(UserRole.ADMIN);
    return newUser;
  }

  private User mockCreatedUser() {
    User user = new User();
    user.setId(1L);
    user.setMail("mail");
    user.setName("name");
    user.setPassword("password");
    user.setRole(UserRoleEnum.ADMIN);
    return user;
  }
}
