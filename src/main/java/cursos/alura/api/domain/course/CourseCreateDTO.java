package cursos.alura.api.domain.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CourseCreateDTO(
        @Schema(description = "The name of the course", example = "Banco de Dados")
        @NotBlank(message = "The name is required")
        String name,
        @Schema(description = "The code of the course", example = "banco")
        @NotBlank(message = "The code is required")
        @Length(max = 10, message = "The code must have at most 10 characters")
        @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)?$")
        String code,
        @Schema(description = "The username of the instructor", example = "watlas")
        @NotBlank(message = "The instructor username is required")
        String instructorUsername,
        @Schema(description = "The description of the course")
        String description
) {
}
