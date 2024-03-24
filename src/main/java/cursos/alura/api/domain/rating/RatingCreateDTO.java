package cursos.alura.api.domain.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingCreateDTO(
        @NotNull
        Long idCourse,
        @NotNull
        Long idUser,
        @NotNull
        @Min(0) @Max(10)
        Integer rating,
        @NotNull
        @Min(0) @Max(10)
        Integer ratingNps,
        String feedback) {
}
