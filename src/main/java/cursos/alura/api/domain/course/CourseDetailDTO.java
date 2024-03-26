package cursos.alura.api.domain.course;

import java.time.ZonedDateTime;

public record CourseDetailDTO(Long id, String name, String code, String instructor, String description, Boolean status, ZonedDateTime createdAt, ZonedDateTime deactivatedAt) {

}
