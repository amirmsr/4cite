package com.ApiJava.JavaApi.Repository;

import com.ApiJava.JavaApi.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
