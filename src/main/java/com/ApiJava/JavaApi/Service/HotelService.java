package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Model.UserRoleEnum;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Service.mapper.HotelMapper;
import com.ApiJava.JavaApi.Utils.JwtUtil;
import com.ApiJava.JavaApi.model.HotelDetails;
import com.ApiJava.JavaApi.model.HotelRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HotelService {

  private final HotelRepository hotelRepository;

  private final HotelMapper hotelMapper;

  private JwtUtil jwtUtilClass;

  public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper, JwtUtil jwtUtilClass) {
    this.hotelRepository = hotelRepository;
    this.hotelMapper = hotelMapper;
    this.jwtUtilClass = jwtUtilClass;
  }

  public HotelDetails post(HotelRequest newHotel, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getRole().equals(UserRoleEnum.HOST))) {
      Hotel hotel = hotelMapper.toEntity(newHotel);
      hotel.setHost(user.get());
      return hotelMapper.toResource(hotelRepository.save(hotel));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à créer un hôtel");
  }

  public List<HotelDetails> get() {
    return hotelMapper.toResource(hotelRepository.findAll());
  }

  public HotelDetails getById(Long id) {
    return hotelMapper.toResource(hotelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé")));
  }

  public void delete(Long id, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || hotelRepository.findById(id).get().getHost().getId().equals(user.get().getId()))) {
      hotelRepository.deleteById(id);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à supprimer l'hôtel");
  }

  public HotelDetails update(Long id, HotelRequest hotel, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || hotelRepository.findById(id).get().getHost().getId().equals(user.get().getId()))) {
      Hotel updatedHotel = hotelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé"));

      updatedHotel.setName(hotel.getName());
      updatedHotel.setLocalisation(hotel.getLocalisation());
      updatedHotel.setDescription(hotel.getDescription());
      updatedHotel.setPicture(hotel.getPicture());

      return hotelMapper.toResource(hotelRepository.save(updatedHotel));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à mettre à jour l'hôtel");
  }
}