package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Booking;
import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Model.UserRoleEnum;
import com.ApiJava.JavaApi.Repository.BookingRepository;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Service.mapper.BookingMapper;
import com.ApiJava.JavaApi.Utils.JwtUtil;
import com.ApiJava.JavaApi.model.BookingDetails;
import com.ApiJava.JavaApi.model.BookingRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  private final HotelRepository hotelRepository;

  private final BookingMapper bookingMapper;

  private final JwtUtil jwtUtilClass;

  public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository, BookingMapper bookingMapper, JwtUtil jwtUtilClass) {
    this.bookingRepository = bookingRepository;
    this.hotelRepository = hotelRepository;
    this.bookingMapper = bookingMapper;
    this.jwtUtilClass = jwtUtilClass;
  }

  public BookingDetails post(BookingRequest newBooking, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);
    Optional<Hotel> hotel = hotelRepository.findById(newBooking.getHotelId());

    if (user.isPresent()) {
      if (hotel.isPresent()) {
        Booking booking = bookingMapper.toEntity(newBooking);
        booking.setUser(user.get());
        booking.setHotel(hotel.get());
        return bookingMapper.toResource(bookingRepository.save(booking));
      } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé");
      }
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à créer une réservation");
  }

  public void delete(Long id, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);
    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getId().equals(bookingRepository.findById(id).get().getUser().getId()))) {
      bookingRepository.deleteById(id);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à supprimer la réservation");
  }

  public List<BookingDetails> get(String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && user.get().getRole().equals(UserRoleEnum.ADMIN)) {
      List<Booking> bookings = bookingRepository.findAll();
      List<BookingDetails> bookingDetails = new ArrayList<>();
      for (Booking booking : bookings) {
        bookingDetails.add(bookingMapper.toResource(booking));
      }
      return bookingDetails;
    } else if (user.isPresent()) {
      List<Booking> bookings = bookingRepository.findAllByUser(user.get());
      List<BookingDetails> bookingDetails = new ArrayList<>();
      for (Booking booking : bookings) {
        bookingDetails.add(bookingMapper.toResource(booking));
      }
      return bookingDetails;
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à voir les réservations");
  }

  public BookingDetails getById(Long id, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getId().equals(bookingRepository.findById(id).get().getUser().getId()))) {
      Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation non trouvée"));
      return bookingMapper.toResource(booking);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à voir la réservation");
  }

  public BookingDetails update(Long id, BookingRequest booking, String token) {
    Optional<User> user = jwtUtilClass.getUserFrom(token);

    if (user.isPresent() && (user.get().getRole().equals(UserRoleEnum.ADMIN) || user.get().getId().equals(bookingRepository.findById(id).get().getUser().getId()))) {
      Booking updatedBooking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation non trouvée"));
      updatedBooking.setCheckIn(booking.getCheckIn());
      updatedBooking.setCheckOut(booking.getCheckOut());
      return bookingMapper.toResource(bookingRepository.save(updatedBooking));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à mettre à jour la réservation");
  }
}