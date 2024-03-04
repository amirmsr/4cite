package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Utils.JwtToken;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HotelServiceImplement {

  private final HotelRepository hotelRepository;

  @Autowired
  private JwtToken jwtTokenClass;

  public List<Hotel> get(Pageable pageable) {
    return hotelRepository.findAll(pageable).getContent();
  }

  public Hotel getById(Long id) {
    return hotelRepository.findById(id).orElse(null);
  }

  public Hotel post(Hotel newHotel, String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole() == 2) {
      return hotelRepository.save(newHotel);
    }

    throw new BadRequestException("You are not allowed to create a hotel");
  }

  public void delete(Long id, String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole() == 2) {
      hotelRepository.deleteById(id);
    }

    throw new BadRequestException("You are not allowed to delete a hotel");
  }

  public Hotel update(Long id, Hotel hotel, String token) throws BadRequestException {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole() == 2) {
      Hotel updatedHotel = hotelRepository.findById(id).orElseThrow(() -> new BadRequestException("Hotel not found"));
      updatedHotel.setName(hotel.getName());
      updatedHotel.setDescription(hotel.getDescription());
      updatedHotel.setLocation(hotel.getLocation());
      updatedHotel.setPicture_list(hotel.getPicture_list());

      return hotelRepository.save(hotel);
    }

    throw new BadRequestException("You are not allowed to update a hotel");
  }
}
