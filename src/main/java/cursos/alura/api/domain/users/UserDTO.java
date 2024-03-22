package cursos.alura.api.domain.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.ZonedDateTime;
import java.util.TimeZone;

public record UserDTO(
        @NotBlank
        String name,
        @NotBlank
        @Pattern(regexp = "^[a-z]+$")
        String userName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotNull
        Role role) {

}
