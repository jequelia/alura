package cursos.alura.api.domain.registration;


public record RegistrationDetailDTO(Long id, Long courseId, String nameCourse, String codeCourse, Long userId, String nameStudent, String userNameStudent) {

    public RegistrationDetailDTO(Registration registration) {


        this(registration.getId(),
                registration.getCourse().getId(),
                registration.getCourse().getName(),
                registration.getCourse().getCode(),
                registration.getUser().getId(),
                registration.getUser().getName(),
                registration.getUser().getUserName());
    }
}
