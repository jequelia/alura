package cursos.alura.api.domain.registration;

import cursos.alura.api.domain.registration.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService service;

    private final RegistrationMapper mapper;

    @PostMapping("/{idUser}/{idCourse}")
    @Transactional
    public ResponseEntity<RegistrationDetailDTO> registrationUserInCourse(@RequestBody @Valid RegistrationCreateDTO registrationCreateDTO, UriComponentsBuilder uriBuilder){

        Registration registration = service.registrationUserInCourse(registrationCreateDTO);

        var uri = uriBuilder.path("/registration/{registrationId}").buildAndExpand(registration.getId()).toUri();

        return ResponseEntity.created(uri).body(mapper.registrationToRegistrationDetailDTO(registration));

    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<RegistrationDetailDTO> getRegistrationById(@PathVariable Long registrationId) {
        var registration = service.getRegistrationById(registrationId);

        if (registration == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mapper.registrationToRegistrationDetailDTO(registration));
    }
}
