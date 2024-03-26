package cursos.alura.api.domain.course;

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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "Course", description = "Operations related to courses")
public class CourseController {

    private final CourseService service;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    @Transactional
    @Operation(summary = "Create a course", description = "Create a course",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CourseDetailDTO> userCreate(@RequestBody @Valid CourseCreateDTO courseCreateDTO, UriComponentsBuilder uriBuilder){

        CourseDetailDTO course = service.createCourse(courseCreateDTO);
        var uri = uriBuilder.path("/course/{courseId}").buildAndExpand(course.id()).toUri();

        return ResponseEntity.created(uri).body(course);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{courseId}")
    @Operation(summary = "Get course by id", description = "Get course by id",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CourseDetailDTO> getCourseById(@PathVariable Long courseId) {
        var course = service.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(course);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{courseId}")
    @Transactional
    @Operation(summary = "Deactivate course", description = "Deactivate course",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deactivateCourse(@PathVariable Long courseId) {
        service.deactivateCourse(courseId);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/status/{status}")
    @Operation(summary = "Get list of courses", description = "Get list of courses",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Page<CourseDetailDTO>> getListCourse(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao, @PathVariable Boolean status) {
       Page<CourseDetailDTO> page = service.findAllCourse(paginacao, status);
        if(page.getTotalElements() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }
}
