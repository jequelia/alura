package cursos.alura.api.controller;

import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserDTO;
import cursos.alura.api.domain.users.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public void userCreate(@RequestBody @Valid UserDTO userDTO){
        repository.save(new User(userDTO));

    }
}
