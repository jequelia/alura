package cursos.alura.api.domain.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateDTO(
        @Schema(description = "The name of the user", required = true, example = "Maria")
        @NotBlank
        String name,
        @Schema(description = "The username of the user, must be lowercase alphabetic characters", required = true, example = "maria_code")
        @NotBlank
        @Pattern(regexp = "^[a-z]+$")
        String userName,
        @Schema(description = "The email of the user", required = true, example = "maria@example.com")
        @NotBlank
        @Email
        String email,
        @Schema(description = "The password of the user", required = true, example = "123456")
        @NotBlank
        String password,
        @Schema(description = "The role of the user: ESTUDANTE, INSTRUTOR, ADMIN", required = true, example = "ESTUDANTE")
        @NotNull
        Role role ){

}
