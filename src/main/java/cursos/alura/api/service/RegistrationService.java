package cursos.alura.api.service;

import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.UserNotFoundException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseRepository;
import cursos.alura.api.domain.registration.Registration;
import cursos.alura.api.domain.registration.RegistrationRepository;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;



    public void registrationUserInCourse(Long userId, Long courseId) {

        User user = getUser(userId);
        Course course = getCourse(courseId);

        if (registrationRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new IllegalStateException("User is already enrolled in this course.");
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setCourse(course);

        registrationRepository.save(registration);
    }

    private Course getCourse(Long courseId) {
        return courseRepository.findByIdAndStatus(courseId,true)
                .orElseThrow(() -> new CourseNotFoundException("Unable to enroll in an inactive course: " + courseId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
    }
}
