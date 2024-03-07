package com.ApiJava.JavaApi.Repository;

import com.ApiJava.JavaApi.Model.Booking;
import com.ApiJava.JavaApi.Model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  List<Booking> findAllByUser(User user);
}
