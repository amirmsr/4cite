package com.ApiJava.JavaApi.Controller;


import com.ApiJava.JavaApi.Repository.BookingRepository;
import com.ApiJava.JavaApi.Repository.HotelRepository;
import com.ApiJava.JavaApi.Repository.UserRepository;
import com.ApiJava.JavaApi.Service.BookingService;
import com.ApiJava.JavaApi.Service.HotelService;
import com.ApiJava.JavaApi.Service.UserService;
import com.ApiJava.JavaApi.Service.mapper.BookingMapperImpl;
import com.ApiJava.JavaApi.Service.mapper.HotelMapperImpl;
import com.ApiJava.JavaApi.Service.mapper.UserMapperImpl;
import com.ApiJava.JavaApi.Utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ContextConfiguration(classes = {BookingController.class, HotelController.class, UserController.class, BookingService.class, HotelService.class, UserService.class, JwtUtil.class, BookingMapperImpl.class, HotelMapperImpl.class, UserMapperImpl.class, JwtUtil.class})
public class BaseControllerTest {
  @Autowired
  protected MockMvc mvc;

  @MockBean
  protected HotelRepository hotelRepository;

  @MockBean
  protected BookingRepository bookingRepository;

  @MockBean
  protected UserRepository userRepository;

  @Autowired
  protected JwtUtil jwtUtil;

  protected MvcResult callController(HttpMethod httpMethod, String resourcePath, Object entityRequest) throws Exception {
    return mvc.perform(MockMvcRequestBuilders.request(httpMethod, resourcePath)
        .content(asJsonString(entityRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andReturn();
  }

  protected MvcResult callController(HttpMethod httpMethod, String resourcePath, Object entityRequest, String token) throws Exception {
    return mvc.perform(MockMvcRequestBuilders.request(httpMethod, resourcePath)
        .header("token", token)
        .content(asJsonString(entityRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andReturn();
  }

  protected String createToken(Long userId) {
    return jwtUtil.createToken(userId);
  }

  public String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
