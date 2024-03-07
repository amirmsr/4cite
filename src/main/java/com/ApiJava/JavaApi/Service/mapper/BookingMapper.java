package com.ApiJava.JavaApi.Service.mapper;

import com.ApiJava.JavaApi.Model.Booking;
import com.ApiJava.JavaApi.model.BookingDetails;
import com.ApiJava.JavaApi.model.BookingRequest;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface BookingMapper {

  Booking toEntity(BookingRequest bookingApi);

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "hotelId", source = "hotel.id")
  BookingDetails toResource(Booking booking);

}
