package cursos.alura.api.domain.course;

import cursos.alura.api.domain.users.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CourseCreateDTO(
        @Schema(description = "The name of the course", example = "Banco de Dados")
        @NotBlank
        String name,
        @Schema(description = "The code of the course", example = "banco-de-dados")
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)?$")
        String code,
        @Schema(description = "The username of the instructor", example = "Watlas")
        @NotBlank
        String instructorUserName,
        @Schema(description = "The description of the course")
        String description
) {
}
