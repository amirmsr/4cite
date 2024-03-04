package com.ApiJava.JavaApi.Repository;

import com.ApiJava.JavaApi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMail(String email);
    Optional<User> findByMail(String mail);
}
