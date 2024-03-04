package com.ApiJava.JavaApi.Controller;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Service.HotelServiceImplement;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class HotelController {

  private HotelServiceImplement hotelServiceImplement;

  @GetMapping
  public List<Hotel> read(@RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "10") int limit) {
    Pageable pageable = PageRequest.of(0, limit, Sort.by(sortBy));
    return hotelServiceImplement.get(pageable);
  }

  @GetMapping(value = "/{id}")
  public Hotel read(@PathVariable("id") Long id) {
    return hotelServiceImplement.getById(id);
  }

  @PostMapping
  public ResponseEntity<Hotel> create(@RequestBody Hotel newHotel, @RequestHeader String token) throws BadRequestException {
    Hotel savedHotel = hotelServiceImplement.post(newHotel, token);
    return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Hotel> update(@PathVariable("id") Long id, @RequestBody Hotel hotel, @RequestHeader String token) throws BadRequestException {
    Hotel updatedHotel = hotelServiceImplement.update(id, hotel, token);
    return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestHeader String token) throws BadRequestException {
    hotelServiceImplement.delete(id, token);
    return new ResponseEntity<>("Hotel deleted", HttpStatus.OK);
  }
}
