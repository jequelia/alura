package cursos.alura.api.domain.registration;

import cursos.alura.api.configuration.exception.UserRegistrationInCourseException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseRepository;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Autowired
    private RegistrationService registrationService;

    @Test
    void testRegistrationUserInCourseUserAlreadyRegistered() {

        User user = new User();
        user.setUsername("joana");

        Course course = new Course();
        course.setName("programming");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(courseRepository.findByIdAndStatus(anyLong(), anyBoolean())).thenReturn(Optional.of(course));
        when(registrationRepository.existsByUserIdAndCourseId(1L,1L)).thenReturn(true);

        assertThrows(UserRegistrationInCourseException.class, () ->
                registrationService.registrationUserInCourse(
                        new RegistrationCreateDTO(1L, 1L)
                ));


    }
}