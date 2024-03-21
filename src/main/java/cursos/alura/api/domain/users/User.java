package cursos.alura.api.domain.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String userName;
    private String password;
    private ZonedDateTime dateCreated;
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    protected void onCreate() {
        dateCreated = ZonedDateTime.now();
    }

    public User( UserDTO userDTO){
        this.name = userDTO.name();
        this.userName = userDTO.userName();
        this.password = userDTO.password();
        this.role = userDTO.role();
        this.email = userDTO.email();
    }
}
