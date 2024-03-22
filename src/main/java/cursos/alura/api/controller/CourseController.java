package cursos.alura.api.controller;

import cursos.alura.api.domain.course.CourseCreateDTO;
import cursos.alura.api.domain.course.CourseListDTO;

import cursos.alura.api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping
    @Transactional
    public ResponseEntity userCreate(@RequestBody @Valid CourseCreateDTO courseCreateDTO){
        service.createCourse(courseCreateDTO);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deactivateCourse(@PathVariable Long id) {
        service.deactivateCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{status}")
    public ResponseEntity<Page<CourseListDTO>> getListCourse(@PageableDefault(size = 10, sort = {"name"}) Pageable paginacao, @PathVariable Boolean status) {
       Page page = service.findAllCourse(paginacao, status);
        return ResponseEntity.ok(page);
    }
}
