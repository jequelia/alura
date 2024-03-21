package cursos.alura.api.domain.users;

import java.time.ZonedDateTime;
import java.util.TimeZone;

public record UserDTO(String name, String userName, String email, String password, Role role) {

}
