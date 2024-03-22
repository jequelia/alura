package cursos.alura.api.domain.users;

public record UserDetailsDTO(String name, String email, Role role) {

    public UserDetailsDTO(User user) {
        this(user.getName(), user.getEmail(), user.getRole());
    }
}
