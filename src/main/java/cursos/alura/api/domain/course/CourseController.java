package cursos.alura.api.domain.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PostMapping
    @Transactional
    public ResponseEntity<CourseDetailDTO> userCreate(@RequestBody @Valid CourseCreateDTO courseCreateDTO, UriComponentsBuilder uriBuilder){

        CourseDetailDTO course = service.createCourse(courseCreateDTO);
        var uri = uriBuilder.path("/course/{courseId}").buildAndExpand(course.id()).toUri();

        return ResponseEntity.created(uri).body(course);

    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailDTO> getCourseById(@PathVariable Long courseId) {
        var course = service.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{courseId}")
    @Transactional
    public ResponseEntity<Void> deactivateCourse(@PathVariable Long courseId) {
        service.deactivateCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<CourseDetailDTO>> getListCourse(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao, @PathVariable Boolean status) {
       Page<CourseDetailDTO> page = service.findAllCourse(paginacao, status);
        if(page.getTotalElements() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }
}
