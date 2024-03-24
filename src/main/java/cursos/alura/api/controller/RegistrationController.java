package cursos.alura.api.controller;

import cursos.alura.api.domain.course.CourseDetailDTO;
import cursos.alura.api.domain.registration.Registration;
import cursos.alura.api.domain.registration.RegistrationDetailDTO;
import cursos.alura.api.domain.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping("/{idUser}/{idCourse}")
    @Transactional
    public ResponseEntity registrationUserInCourse(@PathVariable Long idUser, @PathVariable Long idCourse, UriComponentsBuilder uriBuilder){

        Registration registration = service.registrationUserInCourse(idUser, idCourse);

        var uri = uriBuilder.path("/registration/{id}").buildAndExpand(registration.getId()).toUri();

        return ResponseEntity.created(uri).body(new RegistrationDetailDTO(registration));

    }

    @GetMapping("/{id}")
    public ResponseEntity getRegistrationById(@PathVariable Long registrationId) {
        var registration = service.getRegistrationById(registrationId);

        return ResponseEntity.ok(new RegistrationDetailDTO(registration));
    }
}
