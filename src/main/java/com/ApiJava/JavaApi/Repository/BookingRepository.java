package com.ApiJava.JavaApi.Repository;

import com.ApiJava.JavaApi.Model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findAllByUserId(Long userId);
}
