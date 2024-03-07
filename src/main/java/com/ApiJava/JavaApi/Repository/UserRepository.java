package com.ApiJava.JavaApi.Repository;

import com.ApiJava.JavaApi.Model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByMail(String email);

  Optional<User> findByMail(String mail);
}
