package com.ApiJava.JavaApi.Controller;

import com.ApiJava.JavaApi.Service.HotelService;
import com.ApiJava.JavaApi.HotelsApi;
import com.ApiJava.JavaApi.model.HotelDetails;
import com.ApiJava.JavaApi.model.HotelRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController implements HotelsApi {

  private final HotelService hotelService;

  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @Override
  public ResponseEntity<HotelDetails> createHotel(
      String token,
      HotelRequest hotelRequest
  ) {
    HotelDetails savedHotel = hotelService.post(hotelRequest, token);
    return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> deleteHotel(
      Integer id,
      String token
  ) {
    hotelService.delete(Long.valueOf(id), token);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  public ResponseEntity<List<HotelDetails>> getAllHotels(

  ) {
    List<HotelDetails> hotelDetails = hotelService.get();
    return new ResponseEntity<>(hotelDetails, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<HotelDetails> getHotelById(
      Integer id
  ) {
    HotelDetails hotelDetails = hotelService.getById(Long.valueOf(id));
    return new ResponseEntity<>(hotelDetails, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<HotelDetails> updateHotel(
      Integer id,
      String token,
      HotelRequest hotelRequest
  ) {
    HotelDetails updatedHotel = hotelService.update(Long.valueOf(id), hotelRequest, token);
    return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
  }
}
