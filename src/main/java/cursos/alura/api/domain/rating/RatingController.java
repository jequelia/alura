package cursos.alura.api.domain.rating;

import cursos.alura.api.domain.rating.RatingCourseNpsResultDTO;
import cursos.alura.api.domain.rating.RatingCreateDTO;
import cursos.alura.api.domain.rating.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService service;

    @PostMapping
    @Transactional
    @Operation(summary = "Save rating", description = "Save rating")
    public ResponseEntity<Void> saveRating(@RequestBody @Valid RatingCreateDTO ratingCreateDTO){
        service.saveRating(ratingCreateDTO);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/report")
    @Operation(summary = "Report rating", description = "Report rating")
    public ResponseEntity<Page<RatingCourseNpsResultDTO>> reportRating(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao) {
        Page<RatingCourseNpsResultDTO> page = service.reportRating(paginacao);
        if(page.getTotalElements() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/report/{idCourse}")
    @Operation(summary = "Report rating by course", description = "Report rating by course")
    public ResponseEntity<RatingCourseNpsResultDTO> reportRatingByIdCourse(@PathVariable Long idCourse) {
        RatingCourseNpsResultDTO ratingCourseNpsResultDTO = service.reportRatingByIdCourse(idCourse);

        if (ratingCourseNpsResultDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ratingCourseNpsResultDTO);
    }

}
