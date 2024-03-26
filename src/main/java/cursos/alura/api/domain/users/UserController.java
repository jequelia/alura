package cursos.alura.api.domain.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "User", description = "Operations related to users")
public class UserController {

    private final UserService userService;


    @PostMapping
    @Transactional
    @Operation(summary = "Create a user", description = "Create a user")
    public ResponseEntity<UserDetailsDTO> userCreate(@RequestBody @Valid UserCreateDTO userDTO, UriComponentsBuilder uriBuilder){
        UserDetailsDTO user = userService.createUser(userDTO);

        var uri = uriBuilder.path("/user/{userId}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Get user by username",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserDetailsDTO> getUserByUsername(@PathVariable String username) {
        var user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "Get user by id",security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long userId) {
        var user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }
}
