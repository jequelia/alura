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
    private long promoters;
    private long neutrals;
    private long detractors;
    private boolean hasEnoughRatings;
    private String nps;

}
