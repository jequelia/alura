package cursos.alura.api.domain.rating;

public record RatingCreateDTO(Long idCourse, Long idUser, Integer rating, Integer ratingNps, String feedback) {
}
