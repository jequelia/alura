package cursos.alura.api.domain.course;

import cursos.alura.api.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    @Mapping(target = "instructor.userName", source = "dto.instructorUserName")
    Course courseCreateDTOtoCourse(CourseCreateDTO dto);

    @Mapping(target = "instructor", source = "course.instructor.userName")
    CourseDetailDTO courseToCourseDetailDTO(Course course);

}