package cursos.alura.api.domain.course;

import cursos.alura.api.domain.users.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CourseCreateDTO(@NotBlank
                              String name,
                              @NotBlank
                              @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)?$")
                              String code,
                              @NotBlank
                              String instructorUserName,
                              String description
                              ) {
}
