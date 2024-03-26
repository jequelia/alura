package cursos.alura.api.domain.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ESTUDANTE("student"),
    INSTRUTOR("instructor"),
    ADMIN("admin");


    private final String description;
}
