package com.ApiJava.JavaApi.Controller;

import com.ApiJava.JavaApi.Model.Booking;
import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Repository.BookingRepository;
import com.ApiJava.JavaApi.Service.BookingServiceImplement;
import java.awt.print.Book;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
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
@RequestMapping("/bookings")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class BookingController {
  private final BookingServiceImplement bookingServiceImplement;

  @GetMapping
  public List<Booking> read(@RequestHeader String token)
  {
    return bookingServiceImplement.get(token);
  }

  @GetMapping(value = "/{id}")
  public Booking read(@PathVariable("id") Long id, @RequestHeader String token) {
    return bookingServiceImplement.getById(id, token);
  }

  @PostMapping
  public ResponseEntity<Booking> create(@RequestBody Booking newBooking, @RequestHeader String token) {
    Booking savedBooking = bookingServiceImplement.post(newBooking, token);
    return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Booking> update(@PathVariable("id") Long id, @RequestBody Booking booking, @RequestHeader String token) {
    Booking updatedBooking = bookingServiceImplement.update(id, booking, token);
    return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestHeader String token) {
    bookingServiceImplement.delete(id, token);
    return new ResponseEntity<>("Hotel deleted", HttpStatus.OK);
  }
}
