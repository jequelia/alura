package cursos.alura.api.domain.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<UserCreateDTO> jsonUserCreateDTO;

    @Autowired
    private JacksonTester<UserDetailsDTO> jsonUserDetailsDTO;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Should return BAD_REQUEST user no created")
    void testErrorUserCreate() throws Exception {
        var response = mockMvc.perform(post("/user"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return user details when user is successfully created")
    void testSuccessfulUserCreation() throws Exception {
        var response = mockMvc
                .perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserCreateDTO.write(
                                new UserCreateDTO(
                                        "joana",
                                        "joana",
                                        "joana@gmail.com",
                                        "123456", Role.ESTUDANTE)
                        ).getJson())
                ).andReturn().getResponse();

        var jsonReturn = jsonUserDetailsDTO.write(new UserDetailsDTO(
                1L,
                "joana",
                "joana@gmail.com",
                        Role.ESTUDANTE)).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonReturn);
    }

}