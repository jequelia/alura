package cursos.alura.api.domain.rating;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "Operations related to rating")
public class RatingController {

    private final RatingService service;
    @PostMapping
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ESTUDANTE')")
    @Operation(summary = "Save rating", description = "Save rating",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> saveRating(@RequestBody @Valid RatingCreateDTO ratingCreateDTO){
        service.saveRating(ratingCreateDTO);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/report")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Report rating", description = "Report rating",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Page<RatingCourseNpsResultDTO>> reportRating(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao) {
        Page<RatingCourseNpsResultDTO> page = service.reportRating(paginacao);
        if(page.getTotalElements() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/report/{idCourse}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Report rating by course", description = "Report rating by course",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<RatingCourseNpsResultDTO> reportRatingByIdCourse(@PathVariable Long idCourse) {
        RatingCourseNpsResultDTO ratingCourseNpsResultDTO = service.reportRatingByIdCourse(idCourse);

        if (ratingCourseNpsResultDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ratingCourseNpsResultDTO);
    }

}
