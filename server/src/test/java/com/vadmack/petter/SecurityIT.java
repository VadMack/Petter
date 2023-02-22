package com.vadmack.petter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vadmack.petter.mail.MailService;
import com.vadmack.petter.security.dto.request.AuthRequest;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SecurityIT {

  private final String USERNAME = "user1";
  private final String PASSWORD = "user1";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private MailService mailService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private User savedUser;


  @BeforeAll
  void setUp() {
    User user = new User();
    user.setUsername(USERNAME);
    user.setPassword(passwordEncoder.encode(PASSWORD));
    user.setEnabled(true);
    savedUser = userRepository.save(user);
  }

  @Test
  void test() throws Exception {
    String token = auth(new AuthRequest(USERNAME, PASSWORD));
    getSecuredResource(token);
  }

  private String auth(AuthRequest authRequest) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(authRequest);

    MockHttpServletResponse response = mvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(requestJson)
    )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();


    return response.getHeader(HttpHeaders.AUTHORIZATION);
  }

  private void getSecuredResource(String token) throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.get("/api/users/favorites")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isOk());
  }


  @AfterAll
  void clenUp() {
    userRepository.delete(savedUser);
  }
}