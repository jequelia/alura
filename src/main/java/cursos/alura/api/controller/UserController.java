package cursos.alura.api.controller;

import cursos.alura.api.domain.registration.RegistrationDetailDTO;
import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserCreateDTO;
import cursos.alura.api.domain.users.UserDetailsDTO;
import cursos.alura.api.domain.users.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailsDTO> userCreate(@RequestBody @Valid UserCreateDTO userDTO, UriComponentsBuilder uriBuilder){
        User user = new User(userDTO);
        repository.save(user);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailsDTO(user));

    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<UserDetailsDTO> getUserByUserName(@PathVariable String userName) {
        var userDetails = repository.findByUserName(userName);
        if (userDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserDetailsDTO(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long userId) {
        var UserDetailsDTO = repository.findById(userId).orElseThrow();
        return ResponseEntity.ok(new UserDetailsDTO(UserDetailsDTO));
    }
}
