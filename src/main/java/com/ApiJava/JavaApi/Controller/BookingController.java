package com.ApiJava.JavaApi.Controller;

import com.ApiJava.JavaApi.Service.BookingService;
import com.ApiJava.JavaApi.BookingsApi;
import com.ApiJava.JavaApi.model.BookingDetails;
import com.ApiJava.JavaApi.model.BookingRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingController implements BookingsApi {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @Override
  public ResponseEntity<BookingDetails> createBooking(String token, BookingRequest bookingRequest) {
    BookingDetails savedBooking = bookingService.post(bookingRequest, token);
    return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> deleteBooking(
      Integer id,
      String token
  ) {
    bookingService.delete(Long.valueOf(id), token);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  public ResponseEntity<List<BookingDetails>> getAllBookings(String token) {
    List<BookingDetails> bookingDetails = bookingService.get(token);
    return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BookingDetails> getBookingById(
      Integer id,
      String token
  ) {
    BookingDetails bookingDetails = bookingService.getById(Long.valueOf(id), token);
    return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BookingDetails> updateBooking(
      Integer id,
      String token,
      BookingRequest bookingRequest) {
    BookingDetails updatedBooking = bookingService.update(Long.valueOf(id), bookingRequest, token);
    return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
  }
}
