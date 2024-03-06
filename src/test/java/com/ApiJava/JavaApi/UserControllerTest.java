package com.ApiJava.JavaApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ApiJava.JavaApi.Controller.UserController;
import com.ApiJava.JavaApi.Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseControllerTest {

  @Test
  void createUser() throws Exception {
    // Path to be called
    String resourcePath = "/users";

    // data initialization
    User userRequest = createNewUser();
    User createdUser = mockCreatedUser();

    // Mocking repositories
    when(userRepository.save(any(User.class))).thenReturn(createdUser);

    // Call Controller
    MvcResult mvcResult = callController(HttpMethod.POST, resourcePath, userRequest);

    // getting controller response
    MockHttpServletResponse response = mvcResult.getResponse();
    User userDetails = new ObjectMapper().readValue(response.getContentAsString(), User.class);

    // assertions
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals(userRequest.getRole(), userDetails.getRole(), "users descriptions are not the same");
    assertEquals(userRequest.getPassword(), userDetails.getPassword(), "users labels are not the same");
    assertEquals(userRequest.getMail(), userDetails.getMail(), "users status are not the same");
    assertEquals(userRequest.getName(), userDetails.getName(), "users types are not the same");
    assertEquals(createdUser.getId(), userDetails.getId(), "users Ids are not the same");
  }

//  @Test
//  void updateEvidence() throws Exception {
//    // Path to be called
//    String resourcePath = "/evidences/1";
//
//    // Data initialization
//    EvidenceRequest evidenceRequest = createEvidenceRequest();
//    Evidence updatedEvidence = mockUpdatedEvidence(evidenceRequest);
//
//    // Mocking repositories
//    when(evidenceRepository.findById(any(Long.class))).thenReturn(Optional.of(updatedEvidence));
//    when(evidenceRepository.save(any(Evidence.class))).thenReturn(updatedEvidence);
//
//    // Call Controller
//    MvcResult mvcResult = callController(HttpMethod.PUT, resourcePath, evidenceRequest);
//
//    // Getting controller response
//    MockHttpServletResponse response = mvcResult.getResponse();
//    EvidenceDetails evidenceDetails = new ObjectMapper().readValue(response.getContentAsString(), EvidenceDetails.class);
//
//    // Assertions
//    assertEquals(HttpStatus.OK.value(), response.getStatus());
//    assertEquals(evidenceRequest.getDescription(), evidenceDetails.getDescription(), "evidences descriptions are not the same");
//    assertEquals(evidenceRequest.getLabel(), evidenceDetails.getLabel(), "evidences labels are not the same");
//    assertEquals(evidenceRequest.getStatus().getValue(), evidenceDetails.getStatus().getValue(), "evidences status are not the same");
//    assertEquals(evidenceRequest.getType().getValue(), evidenceDetails.getType().getValue(), "evidences types are not the same");
//    assertEquals(updatedEvidence.getId(), evidenceDetails.getId(), "evidences Ids are not the same");
//  }
//
//  @Test
//  void patchEvidence() throws Exception {
//    // Path to be called
//    String resourcePath = "/evidences/1";
//
//    // Data initialization
//    EvidenceRequest evidenceRequest = createEvidenceRequest();
//    Evidence patchedEvidence = mockPatchedEvidence(evidenceRequest);
//
//    // Mocking repositories
//    when(evidenceRepository.findById(any(Long.class))).thenReturn(Optional.of(patchedEvidence));
//    when(evidenceRepository.save(any(Evidence.class))).thenReturn(patchedEvidence);
//
//    // Call Controller
//    MvcResult mvcResult = callController(HttpMethod.PATCH, resourcePath, evidenceRequest);
//
//    // Getting controller response
//    MockHttpServletResponse response = mvcResult.getResponse();
//    EvidenceDetails evidenceDetails = new ObjectMapper().readValue(response.getContentAsString(), EvidenceDetails.class);
//
//    // Assertions
//    assertEquals(HttpStatus.OK.value(), response.getStatus());
//    assertEquals(evidenceRequest.getDescription(), evidenceDetails.getDescription(), "evidences descriptions are not the same");
//    assertEquals(evidenceRequest.getLabel(), evidenceDetails.getLabel(), "evidences labels are not the same");
//    assertEquals(evidenceRequest.getStatus().getValue(), evidenceDetails.getStatus().getValue(), "evidences status are not the same");
//    assertEquals(evidenceRequest.getType().getValue(), evidenceDetails.getType().getValue(), "evidences types are not the same");
//    assertEquals(patchedEvidence.getId(), evidenceDetails.getId(), "evidences Ids are not the same");
//  }
//
//  @Test
//  void getAllEvidences() throws Exception {
//    String resourcePath = "/evidences";
//    List<Evidence> evidenceList = new ArrayList<>();
//    evidenceList.add(mockCreatedEvidence());
//
//    when(evidenceRepository.findAll()).thenReturn(evidenceList);
//
//    MvcResult mvcResult = callController(HttpMethod.GET, resourcePath, null);
//    MockHttpServletResponse response = mvcResult.getResponse();
//
//    List<EvidenceDetails> evidenceDetails = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<EvidenceDetails>>() {});
//
//    int index = 0;
//    for (EvidenceDetails evidenceDetail : evidenceDetails) {
//      assertEquals(evidenceList.get(index).getDescription(), evidenceDetail.getDescription());
//      assertEquals(evidenceList.get(index).getLabel(), evidenceDetail.getLabel());
//      assertEquals(evidenceList.get(index).getStatus().getValue(), evidenceDetail.getStatus().getValue());
//      assertEquals(evidenceList.get(index).getType().getValue(), evidenceDetail.getType().getValue());
//      index++;
//    }
//    assertEquals(HttpStatus.OK.value(), response.getStatus());
//  }
//
//  @Test
//  void getEvidenceById() throws Exception {
//    // Path to be called
//    String resourcePath = "/evidences/1";
//
//    Evidence evidence = mockCreatedEvidence();
//
//    when(evidenceRepository.findById(any(Long.class))).thenReturn(Optional.of(evidence));
//
//    MvcResult mvcResult = callController(HttpMethod.GET, resourcePath, null);
//
//    MockHttpServletResponse response = mvcResult.getResponse();
//    EvidenceDetails evidenceDetails = new ObjectMapper().readValue(response.getContentAsString(), EvidenceDetails.class);
//
//    assertEquals(HttpStatus.OK.value(), response.getStatus());
//    assertEquals(evidence.getDescription(), evidenceDetails.getDescription());
//    assertEquals(evidence.getLabel(), evidenceDetails.getLabel());
//    assertEquals(evidence.getStatus().getValue(), evidenceDetails.getStatus().getValue());
//    assertEquals(evidence.getType().getValue(), evidenceDetails.getType().getValue());
//    assertEquals(evidence.getId(), evidenceDetails.getId());
//  }
//
//  @Test
//  void deleteEvidence() throws Exception {
//    // Path to be called
//    String resourcePath = "/evidences/1";
//
//    Evidence evidence = mockCreatedEvidence();
//
//    when(evidenceRepository.findById(any(Long.class))).thenReturn(Optional.of(evidence));
//    doNothing().when(evidenceRepository).delete(evidence);
//
//    MvcResult mvcResult = callController(HttpMethod.DELETE, resourcePath, null);
//
//    MockHttpServletResponse response = mvcResult.getResponse();
//
//    assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
//    verify(evidenceRepository, times(1)).delete(evidence);
//  }

  private User createNewUser() {
    User newUser = new User();
    newUser.setMail("mail");
    newUser.setName("name");
    newUser.setPassword("password");
    newUser.setRole(1);
    return newUser;
  }

  private User mockCreatedUser() {
    User user = new User();
    user.setId(1L);
    user.setMail("mail");
    user.setName("name");
    user.setPassword("password");
    user.setRole(1);
    // evidence.setCreatedDate(OffsetDateTime.now());
    // evidence.setUpdatedDate(OffsetDateTime.now());
    return user;
  }

//  private Evidence mockUpdatedEvidence(EvidenceRequest evidenceRequest) {
//    Evidence evidence = new Evidence();
//    evidence.setDescription(evidenceRequest.getDescription());
//    evidence.setLabel(evidenceRequest.getLabel());
//    evidence.setStatus(EvidenceStatusEnum.fromValue(evidenceRequest.getStatus().getValue()));
//    evidence.setType(EvidenceTypeEnum.fromValue(evidenceRequest.getType().getValue()));
//    // evidence.setUpdatedDate(OffsetDateTime.now());
//    return evidence;
//  }
//
//  private Evidence mockPatchedEvidence(EvidenceRequest evidenceRequest) {
//    Evidence evidence = new Evidence();
//    if (evidenceRequest.getLabel() != null) {
//      evidence.setLabel(evidenceRequest.getLabel());
//    }
//    if (evidenceRequest.getDescription() != null) {
//      evidence.setDescription(evidenceRequest.getDescription());
//    }
//    if (evidenceRequest.getType() != null) {
//      evidence.setType(EvidenceTypeEnum.fromValue(evidenceRequest.getType().getValue()));
//    }
//    if (evidenceRequest.getStatus() != null) {
//      evidence.setStatus(EvidenceStatusEnum.fromValue(evidenceRequest.getStatus().getValue()));
//    }
//    return evidence;
//  }
}
