package cursos.alura.api.domain.course;

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

    public Course createCourse(CourseCreateDTO courseDTO)  {
        User userInstructor = userRepository.findByUserName(courseDTO.instructorUserName());

        if ( userInstructor == null || !Role.INSTRUTOR.equals(userInstructor.getRole())) {
            throw new UserNotInstructorException("Only instructors can create courses.");
        }

        Course course = courseMapper.courseCreateDTOtoCourse(courseDTO);
        course.setInstructor(userInstructor);
        courseRepository.save(course);

        return course;
    }

    public void deactivateCourse(Long courseId) {
        var course = courseRepository.getReferenceById(courseId);
        course.setDeactivatedAt();
    }

    public Page findAllCourse(Pageable paginacao, Boolean status) {
        return courseRepository.findAllByStatus(paginacao, status)
                .map(courseMapper::courseToCourseDetailDTO);
    }

    public CourseDetailDTO getCourseById(Long courseId) {
        Course course = courseRepository.getReferenceById(courseId);
       return courseMapper.courseToCourseDetailDTO(course) ;
    }
}