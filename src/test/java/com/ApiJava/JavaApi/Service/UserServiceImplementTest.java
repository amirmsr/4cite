package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;



class UserServiceImplementTest {

//    @Mock
//    private UserRepository userRepository;
//    private UserServiceImplement underTest;
//    private AutoCloseable autoCloseable;
//
//    @BeforeEach
//    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        underTest = new UserServiceImplement(userRepository);
//    }
//
//
//
//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }
//
//    @Test
//    void canGet() {
//        underTest.get();
//        verify(userRepository).findAll();
//    }
//
//
//
//    @Test
//    void canPost() throws BadRequestException {
//
//        //given
//        User newUser = new User();
//        newUser.setName("John Doe");
//        newUser.setMail("john.doe@example.com");
//        newUser.setPassword("password");
//        newUser.setRole(1);
//
//        //when
//        underTest.post(newUser);
//
//        //then
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User captureUser = userArgumentCaptor.getValue();
//
//        assertThat(captureUser).isEqualTo(newUser);
//
//
//    }
//
//    @Test
//    void willErrorWhenEmailExist() {
//
//        //given
//        User newUser = new User();
//        newUser.setName("John Doe");
//        newUser.setMail("john.doe@example");
//        newUser.setPassword("password");
//        newUser.setRole(1);
//        given(userRepository.existsByMail(newUser.getMail())).willReturn(true);
//
//        //when
//        //then
//        assertThatThrownBy(() -> underTest.post(newUser)).isInstanceOf(BadRequestException.class).hasMessageContaining("email existe deja" + newUser.getMail());
//    }
//
//    @Test
//    void willErrorWhenIdNotFound() {
//        long id = 3;
//
//        given(userRepository.findById(id)).willReturn(Optional.empty());
//        //when
//        //then
//        assertThatThrownBy(() -> underTest.getById(id)).isInstanceOf(BadRequestException.class).hasMessageContaining("id non trouvé " + id);
//    }



    /*@Test
    void canUpdateById() throws BadRequestException{

        //given
        long id = 1;
        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setName("John Doe");
        existingUser.setMail("amir@amira");
        existingUser.setPsw("testA2");
        existingUser.setRole(1);

        User updatedUser = new User();
        updatedUser.setName("John Doesss");
        updatedUser.setMail("amir@amirass");
        updatedUser.setPsw("testA2sss");
        updatedUser.setRole(1);

        given(userRepository.findById(id)).willReturn(Optional.of(existingUser));
        given(userRepository.save(any(User.class))).willReturn(updatedUser);


        //when
        User result = underTest.updateById(id, updatedUser);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();


        assertThat(capturedUser).isEqualTo(updatedUser);
        assertThat(result).isEqualTo(updatedUser);
    }*/



//    @Test
//    void canDeleteById() throws BadRequestException {
//        //given
//        long id = 1;
//
//        given(userRepository.existsById(id)).willReturn(true);
//
//        //when
//        underTest.deleteById(id);
//
//        //then
//        verify(userRepository).existsById(id);
//        verify(userRepository).deleteById(id);
//    }


}