package cursos.alura.api.domain.rating;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingCreateDTO(
        @Schema(description = "The ID of the course")
        @NotNull(message = "The id of the course is required")
        Long idCourse,
        @Schema(description = "The ID of the user")
        @NotNull(message = "The id of the user is required")
        Long idUser,
        @Schema(description = "The rating given by the user",  example = "8")
        @NotNull(message = "The rating is required")
        @Max(value = 10, message = "The rating must have at most 10")
        @Min(value = 0, message = "The rating must have at least 0")
        Integer rating,
        @Schema(description = "The Net Promoter Score rating given by the user", example = "9")
        @NotNull(message = "The NPS rating is required")
        @Min(value = 0, message = "The rating must have at least 0")
        @Max(value = 10, message = "The rating must have at most 10")
        Integer ratingNps,
        @Schema(description = "The feedback provided by the user")
        String feedback) {
}
