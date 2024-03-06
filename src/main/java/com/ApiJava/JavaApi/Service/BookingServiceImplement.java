package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.Booking;
import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.BookingRepository;
import com.ApiJava.JavaApi.Utils.JwtToken;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingServiceImplement {

  private final BookingRepository bookingRepository;

  @Autowired
  private JwtToken jwtTokenClass;

  public List<Booking> get(String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent()) {
      return bookingRepository.findAllByUserId(user.get().getId());
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à voir les réservations");
  }

  public Booking getById(Long id, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent() && user.get().getId().equals(bookingRepository.findById(id).get().getUserId())) {
      return bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation non trouvée"));
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à voir la réservation");
  }

  public Booking post(Booking newBooking, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);

    if (user.isPresent()) {
      newBooking.setUserId(user.get());
      return bookingRepository.save(newBooking);
    }

    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à créer une réservation");
  }

  public void delete(Long id, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);
    if (user.isPresent() && (user.get().getRole() == 1 || user.get().getId().equals(bookingRepository.findById(id).get().getUserId()))) {
      bookingRepository.deleteById(id);
    } else {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à supprimer la réservation");
    }
  }

  public Booking update(Long id, Booking booking, String token) {
    Optional<User> user = jwtTokenClass.getUserFrom(token);
    if (user.isPresent() && (user.get().getRole() == 1 || user.get().getId().equals(bookingRepository.findById(id).get().getUserId()))) {
      Booking updatedBooking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation non trouvée"));
      updatedBooking.setCheckIn(booking.getCheckIn());
      updatedBooking.setCheckOut(booking.getCheckOut());
      return bookingRepository.save(updatedBooking);
    } else {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à mettre à jour la réservation");
    }
  }
}