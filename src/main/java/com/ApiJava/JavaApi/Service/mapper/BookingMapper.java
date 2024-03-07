package com.ApiJava.JavaApi.Service.mapper;

import com.ApiJava.JavaApi.Model.Booking;
import com.ApiJava.JavaApi.model.BookingDetails;
import com.ApiJava.JavaApi.model.BookingRequest;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface BookingMapper {

  Booking toEntity(BookingRequest bookingApi);

  BookingDetails toResource(Booking booking);

  List<BookingDetails> toResource(List<Booking> bookings);

}
