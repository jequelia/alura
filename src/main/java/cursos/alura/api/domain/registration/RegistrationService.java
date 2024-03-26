package cursos.alura.api.domain.registration;

import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.UserNotFoundException;
import cursos.alura.api.configuration.exception.UserRegistrationInCourseException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseRepository;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    public Registration registrationUserInCourse(RegistrationCreateDTO registrationCreateDTO) {

        User user = getUser(registrationCreateDTO.idUser());
        Course course = getCourse(registrationCreateDTO.idCourse());

        if (registrationRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new UserRegistrationInCourseException("User is already enrolled in this course.");
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setCourse(course);

        registrationRepository.save(registration);

        return registration;
    }

    private Course getCourse(Long courseId) {
        return courseRepository.findByIdAndStatus(courseId,true)
                .orElseThrow(() -> new CourseNotFoundException("Unable to enroll in an inactive course: " + courseId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
    }

    public Registration getRegistrationById(Long registrationId) {

        return registrationRepository.findById(registrationId).orElse(null);

    }
}
