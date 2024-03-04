package com.ApiJava.JavaApi.Controller;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Service.HotelServiceImplement;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class HotelController {

  private HotelServiceImplement hotelServiceImplement;

  @GetMapping
  public List<Hotel> read() {
    return hotelServiceImplement.get();
  }

  @GetMapping(value = "/{id}")
  public Hotel read(Long id) {
    return hotelServiceImplement.getById(id);
  }

  @PostMapping
  public ResponseEntity<Hotel> create(@RequestBody Hotel newHotel) {
    Hotel savedHotel = hotelServiceImplement.post(newHotel);
    return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Hotel> update(@RequestBody Hotel hotel) {
    Hotel updatedHotel = hotelServiceImplement.update(hotel);
    return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> delete(Long id) {
    hotelServiceImplement.delete(id);
    return new ResponseEntity<>("Hotel deleted", HttpStatus.OK);
  }
}
