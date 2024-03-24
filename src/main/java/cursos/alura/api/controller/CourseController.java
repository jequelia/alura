package cursos.alura.api.controller;

import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseCreateDTO;
import cursos.alura.api.domain.course.CourseDetailDTO;

import cursos.alura.api.domain.course.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping
    @Transactional
    public ResponseEntity userCreate(@RequestBody @Valid CourseCreateDTO courseCreateDTO, UriComponentsBuilder uriBuilder){

        Course course = service.createCourse(courseCreateDTO);
        var uri = uriBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(uri).body(new CourseDetailDTO(course));

    }

    @GetMapping("/{id}")
    public ResponseEntity getCourseById(@PathVariable Long courseId) {
        var course = service.getCourseById(courseId);

        return ResponseEntity.ok(new CourseDetailDTO(course));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deactivateCourse(@PathVariable Long id) {
        service.deactivateCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<CourseDetailDTO>> getListCourse(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao, @PathVariable Boolean status) {
       Page page = service.findAllCourse(paginacao, status);
        return ResponseEntity.ok(page);
    }
}
