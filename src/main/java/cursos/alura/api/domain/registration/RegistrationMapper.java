package cursos.alura.api.domain.registration;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrationMapper {

    @Mapping(target = "courseId", source = "registration.course.id")
    @Mapping(target = "nameCourse", source = "registration.course.name")
    @Mapping(target = "codeCourse", source = "registration.course.code")
    @Mapping(target = "userId", source = "registration.user.id")
    @Mapping(target = "nameStudent", source = "registration.user.name")
    @Mapping(target = "userNameStudent", source = "registration.user.userName")
    RegistrationDetailDTO registrationToRegistrationDetailDTO(Registration registration);

}