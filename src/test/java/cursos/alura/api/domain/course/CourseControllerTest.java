package cursos.alura.api.domain.course;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CourseCreateDTO> jsonCourseCreateDTO;

    @Autowired
    private JacksonTester<CourseDetailDTO> jsonCourseDetailDTO;

    @Autowired
    private JacksonTester<Course> jsonCourse;

    @MockBean
    private CourseRepository repository;

    @MockBean
    private CourseMapper courseMapper;

    @MockBean
    private CourseService service;

    @Test
    @DisplayName("Should return BAD_REQUEST course no created")
    @WithMockUser
    void testErrorCourseCreate() throws Exception {
        var response = mockMvc.perform(post("/course"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return course details when course is successfully created")
    void testSuccessfulUserCreation() throws Exception {

        var courseDetailDTO = new  CourseDetailDTO(
                1L,
                "programming",
                "prog",
                null,
                "test",
                true,
                null,
                null
        );

        when(service.createCourse(any())).thenReturn(courseDetailDTO);
        when(service.getCourseById(any())).thenReturn(courseDetailDTO);

        var response = mockMvc
                .perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCourseCreateDTO.write(
                                new CourseCreateDTO(
                                        "programming",
                                        "prog",
                                        "joana",
                                        "test")
                        ).getJson())
                ).andReturn().getResponse();

        var jsonReturn = jsonCourseDetailDTO.write(courseDetailDTO).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonReturn);
    }
}