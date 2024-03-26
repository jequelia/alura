package cursos.alura.api.controller;

import cursos.alura.api.domain.users.*;
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

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailsDTO> userCreate(@RequestBody @Valid UserCreateDTO userDTO, UriComponentsBuilder uriBuilder){
        User user = userMapper.userCreateDTOtoUser(userDTO);
        repository.save(user);

        var uri = uriBuilder.path("/user/{userId}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.userToUserDetailDTO(user));

    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<UserDetailsDTO> getUserByUserName(@PathVariable String userName) {
        var user = repository.findByUserName(userName);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.userToUserDetailDTO(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long userId) {
        var user = repository.findById(userId).orElseThrow();
        return ResponseEntity.ok(userMapper.userToUserDetailDTO(user));
    }
}
