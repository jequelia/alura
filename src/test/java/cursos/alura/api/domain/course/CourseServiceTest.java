package cursos.alura.api.domain.course;

import cursos.alura.api.configuration.exception.UserNotInstructorException;
import cursos.alura.api.domain.users.Role;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService service;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CourseRepository repository;

    @MockBean
    private CourseMapper courseMapper;


    @Test
    void testCreateCourseUserNotInstructor() {

        CourseCreateDTO courseDTO = new CourseCreateDTO("programming", "prog", "joana", "test");
        User nonInstructor = new User();
        nonInstructor.setUsername("joana");
        nonInstructor.setRole(Role.ESTUDANTE);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(nonInstructor));

        assertThrows(UserNotInstructorException.class, () -> service.createCourse(courseDTO));

        assertThatThrownBy(() -> service.createCourse(courseDTO))
                .isInstanceOf(UserNotInstructorException.class)
                .hasMessage("Only instructors can create courses.");
    }
    @Test
    void testCreateCourseSuccess() {

        CourseCreateDTO courseDTO = new CourseCreateDTO("programming", "prog", "joana", "test");
        CourseDetailDTO courseDetailDTO = new CourseDetailDTO(1L, "programming", "prog", null, "test",true, ZonedDateTime.now(),null);
        User istructor = new User();
        istructor.setUsername("joana");
        istructor.setRole(Role.INSTRUTOR);

        Course course = new Course();
        course.setName("programming");
        course.setDescription("prog");
        course.setInstructor(istructor);
        course.setCode("test");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(istructor));
        when(courseMapper.courseCreateDTOtoCourse(courseDTO)).thenReturn(course);
        when(courseMapper.courseToCourseDetailDTO(course)).thenReturn(courseDetailDTO);

        var result = service.createCourse(courseDTO);

       verify(repository, times(1)).save(course);

       assertThat(result).isNotNull();
    }
}