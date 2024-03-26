package cursos.alura.api.domain.course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    @Mapping(target = "instructor.username", source = "dto.instructorUsername")
    Course courseCreateDTOtoCourse(CourseCreateDTO dto);

    @Mapping(target = "instructor", source = "course.instructor.username")
    CourseDetailDTO courseToCourseDetailDTO(Course course);

}