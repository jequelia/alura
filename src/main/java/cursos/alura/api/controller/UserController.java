package cursos.alura.api.controller;

import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserCreateDTO;
import cursos.alura.api.domain.users.UserDetailsDTO;
import cursos.alura.api.domain.users.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public void userCreate(@RequestBody @Valid UserCreateDTO userDTO){
        repository.save(new User(userDTO));

    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDetailsDTO> getUserByUserName(@PathVariable String userName) {
        var UserDetailsDTO = repository.findByUserName(userName);
        return ResponseEntity.ok(new UserDetailsDTO(UserDetailsDTO));
    }
}
