package cursos.alura.api.domain.course;

import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.CourserExistException;
import cursos.alura.api.configuration.exception.UserNotFoundException;
import cursos.alura.api.configuration.exception.UserNotInstructorException;
import cursos.alura.api.domain.users.Role;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseDetailDTO createCourse(CourseCreateDTO courseDTO)  {
        User userInstructor = userRepository.findByUsername(courseDTO.instructorUsername()).orElseThrow();

        if (!Role.INSTRUTOR.equals(userInstructor.getRole())) {
            throw new UserNotInstructorException("Only instructors can create courses.");
        }

       courseRepository.findByCode(courseDTO.code()).ifPresent(course -> {
            throw new CourserExistException("Course code already exists.");
        });

        Course course = courseMapper.courseCreateDTOtoCourse(courseDTO);
        course.setInstructor(userInstructor);
        courseRepository.save(course);

        return courseMapper.courseToCourseDetailDTO(course);
    }

    public void deactivateCourse(Long courseId) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new UserNotFoundException("Course not found." ));

        course.setDeactivatedAt();
    }

    public Page<CourseDetailDTO> findAllCourse(Pageable paginacao, Boolean status) {
        return courseRepository.findAllByStatus(paginacao, status)
                .map(courseMapper::courseToCourseDetailDTO);
    }

    public CourseDetailDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        return courseMapper.courseToCourseDetailDTO(course) ;
    }
}