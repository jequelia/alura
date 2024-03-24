package cursos.alura.api.domain.course;

import java.time.ZonedDateTime;

public record CourseDetailDTO(Long id, String name, String code, String instructor, String description, Boolean status, ZonedDateTime createdAt, ZonedDateTime deactivatedAt) {

    public CourseDetailDTO(Course course) {
        this(course.getId(), course.getName(), course.getCode(), course.getInstructor().getUserName(), course.getDescription(),course.getStatus(), course.getCreatedAt(), course.getDeactivatedAt());
    }
}
