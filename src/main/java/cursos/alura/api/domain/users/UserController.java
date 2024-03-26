package cursos.alura.api.domain.users;

import cursos.alura.api.domain.users.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;


    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailsDTO> userCreate(@RequestBody @Valid UserCreateDTO userDTO, UriComponentsBuilder uriBuilder){
        UserDetailsDTO user = userService.createUser(userDTO);

        var uri = uriBuilder.path("/user/{userId}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDetailsDTO> getUserByUsername(@PathVariable String username) {
        var user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long userId) {
        var user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }
}
