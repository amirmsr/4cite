package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Utils.JwtToken;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HotelServiceImplement {
  private final HotelRepository hotelRepository;

  @Autowired
  private JwtToken jwtTokenClass;

  public List<Hotel> get() {
    return hotelRepository.findAll();
  }

  public Hotel post(Hotel newHotel) {
    return hotelRepository.save(newHotel);
  }

  public Hotel getById(Long id) {
    return hotelRepository.findById(id).orElse(null);
  }

  public void delete(Long id) {
    hotelRepository.deleteById(id);
  }

  public Hotel update(Hotel hotel) {
    return hotelRepository.save(hotel);
  }
}
