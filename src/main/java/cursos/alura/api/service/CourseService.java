package cursos.alura.api.service;

import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.UserNotInstructorException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseCreateDTO;
import cursos.alura.api.domain.course.CourseListDTO;
import cursos.alura.api.domain.course.CourseRepository;
import cursos.alura.api.domain.users.Role;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void createCourse(CourseCreateDTO courseDTO)  {
        User userInstructor = userRepository.findByUserName(courseDTO.instructorUserName());

        if ( userInstructor == null || !Role.INSTRUTOR.equals(userInstructor.getRole())) {
            throw new UserNotInstructorException("Only instructors can create courses.");
        }

        Course course = new Course(courseDTO);
        course.setInstructor(userInstructor);
        courseRepository.save(course);
    }

    public void deactivateCourse(Long courseId) {

        var course = courseRepository.getReferenceById(courseId);
        course.setDeactivatedAt();
    }

    public Page findAllCourse(Pageable paginacao, Boolean status) {
        return courseRepository.findAllByStatus(paginacao, status).map(CourseListDTO::new);
    }
}