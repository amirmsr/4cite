package com.ApiJava.JavaApi.Service.mapper;

import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.model.UserDetails;
import com.ApiJava.JavaApi.model.UserRequest;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserMapper {

  User toEntity(UserRequest userRequest);

  UserDetails toResource(User user);

  List<UserDetails> toResource(List<User> users);
}
