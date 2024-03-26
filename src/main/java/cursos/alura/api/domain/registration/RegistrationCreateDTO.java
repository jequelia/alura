package cursos.alura.api.domain.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RegistrationCreateDTO(
        @Schema(description = "The id of the user", example = "1")
        @NotNull(message = "The id of the user is required")
        Long idUser,
        @Schema(description = "The id of the course", example = "1")
        @NotNull(message = "The id of the course is required")
        Long idCourse) {
}
