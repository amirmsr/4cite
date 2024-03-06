package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Utils.JwtToken;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class HotelServiceImplement {

  private final HotelRepository hotelRepository;

  @Autowired
  private JwtToken jwtTokenClass;

  public List<Hotel> get(String token, int limit, String sortBy, String order) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent()) {
      return hotelRepository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.fromString(order), sortBy))).getContent();
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à voir les hôtels");
  }

  public Hotel getById(Long id, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent()) {
      return hotelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé"));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à voir l'hôtel");
  }

  public Hotel post(Hotel newHotel, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole() == 1 || user.get().getRole() == 2)) {
      newHotel.setOwner(user.get());
      return hotelRepository.save(newHotel);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à créer un hôtel");
  }

  public void delete(Long id, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole() == 1) {
      hotelRepository.deleteById(id);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à supprimer l'hôtel");
  }

  public Hotel update(Long id, Hotel hotel, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole() == 1) {
      Hotel updatedHotel = hotelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé"));
      updatedHotel.setName(hotel.getName());
      updatedHotel.setLocation(hotel.getLocation());
      updatedHotel.setDescription(hotel.getDescription());
      updatedHotel.setPicture(hotel.getPicture());
      return hotelRepository.save(updatedHotel);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à mettre à jour l'hôtel");
  }
}