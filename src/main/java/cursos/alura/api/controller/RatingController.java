package cursos.alura.api.controller;

import cursos.alura.api.domain.course.CourseListDTO;
import cursos.alura.api.domain.rating.RatingCourseNpsResultDTO;
import cursos.alura.api.domain.rating.RatingCreateDTO;
import cursos.alura.api.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rating")
public class RatingController {

    @Autowired
    private RatingService service;

    @PostMapping
    @Transactional
    public ResponseEntity saveRating(@RequestBody @Valid RatingCreateDTO ratingCreateDTO){
        service.saveRating(ratingCreateDTO);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/report")
    public ResponseEntity<Page<RatingCourseNpsResultDTO>> reportRating(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao) {
        Page<RatingCourseNpsResultDTO> page = service.reportRating(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/report/{idCourse}")
    public ResponseEntity<RatingCourseNpsResultDTO> reportRatingByIdCourse(@PathVariable Long idCourse) {
        RatingCourseNpsResultDTO ratingCourseNpsResultDTO = service.reportRatingByIdCourse(idCourse);
        return ResponseEntity.ok(ratingCourseNpsResultDTO);
    }

}
