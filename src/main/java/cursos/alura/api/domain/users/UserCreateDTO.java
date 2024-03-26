package cursos.alura.api.domain.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateDTO(
        @Schema(description = "The name of the user", example = "Maria")
        @NotBlank(message = "The name is required")
        String name,
        @Schema(description = "The username of the user, must be lowercase alphabetic characters", example = "maria")
        @NotBlank
        @Pattern(regexp = "^[a-z]+$", message = "The username must have only lowercase alphabetic characters")
        String username,
        @Schema(description = "The email of the user", example = "maria@example.com")
        @NotBlank(message = "The email is required")
        @Email(message = "The email must be valid")
        String email,
        @Schema(description = "The password of the user", example = "123456")
        @NotBlank(message = "The password is required")
        String password,
        @Schema(description = "The role of the user: ESTUDANTE, INSTRUTOR, ADMIN", example = "ESTUDANTE")
        @NotNull(message = "The role is required")
        Role role ){

}
