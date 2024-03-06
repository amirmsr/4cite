package com.ApiJava.JavaApi;

import com.ApiJava.JavaApi.Controller.BookingController;
import com.ApiJava.JavaApi.Controller.HotelController;
import com.ApiJava.JavaApi.Controller.UserController;
import com.ApiJava.JavaApi.Repository.BookingRepository;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Repository.UserRepository;
import com.ApiJava.JavaApi.Service.BookingServiceImplement;
import com.ApiJava.JavaApi.Service.HotelServiceImplement;
import com.ApiJava.JavaApi.Service.UserServiceImplement;
import com.ApiJava.JavaApi.Utils.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ContextConfiguration(classes = {BookingController.class, HotelController.class, UserController.class, BookingServiceImplement.class, HotelServiceImplement.class, UserServiceImplement.class, JwtToken.class})
public class BaseControllerTest {
  @Autowired
  protected MockMvc mvc;

  @MockBean
  protected HotelRepository hotelRepository;

  @MockBean
  protected BookingRepository bookingRepository;

  @MockBean
  protected UserRepository userRepository;

  protected MvcResult callController(HttpMethod httpMethod, String resourcePath, Object entityRequest) throws Exception {
    return mvc.perform(MockMvcRequestBuilders.request(httpMethod, resourcePath).content(asJsonString(entityRequest)).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andReturn();
  }

  public String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
