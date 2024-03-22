package cursos.alura.api.controller;

import cursos.alura.api.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping("/{idUser}/{idCourse}")
    @Transactional
    public ResponseEntity registrationUserInCourse(@PathVariable Long idUser, @PathVariable Long idCourse){
        service.registrationUserInCourse(idUser, idCourse);
        return ResponseEntity.noContent().build();

    }
}
