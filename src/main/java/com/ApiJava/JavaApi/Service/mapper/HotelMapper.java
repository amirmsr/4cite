package com.ApiJava.JavaApi.Service.mapper;

import com.ApiJava.JavaApi.Model.Hotel;
import com.ApiJava.JavaApi.model.HotelDetails;
import com.ApiJava.JavaApi.model.HotelRequest;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface HotelMapper {
  Hotel toEntity(HotelRequest hotelRequest);

  @Mapping(target = "hostId", source = "host.id")
  HotelDetails toResource(Hotel hotel);
}
