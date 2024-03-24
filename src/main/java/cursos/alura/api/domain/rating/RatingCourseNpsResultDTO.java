package cursos.alura.api.domain.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingCourseNpsResultDTO {
    private String codeCourse;
    private String courseName;
    private int promoters;
    private int neutrals;
    private int detractors;
    private boolean hasEnoughRatings;
    private double nps;

}
