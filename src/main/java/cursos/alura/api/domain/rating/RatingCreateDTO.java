package cursos.alura.api.domain.rating;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingCreateDTO(
        @Schema(description = "The ID of the course", required = true)
        @NotNull
        Long idCourse,
        @Schema(description = "The ID of the user", required = true)
        @NotNull
        Long idUser,
        @Schema(description = "The rating given by the user", required = true, example = "8")
        @NotNull
        @Min(0) @Max(10)
        Integer rating,
        @Schema(description = "The Net Promoter Score rating given by the user", required = true, example = "9")
        @NotNull
        @Min(0) @Max(10)
        Integer ratingNps,
        @Schema(description = "The feedback provided by the user")
        String feedback) {
}
