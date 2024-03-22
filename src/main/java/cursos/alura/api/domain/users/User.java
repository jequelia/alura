package cursos.alura.api.domain.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false,length = 100, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false, length = 20, unique = true)
    private String userName;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;

    @Column(name = "role", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    protected void onCreate() {
        dateCreated = ZonedDateTime.now();
    }

    public User( UserCreateDTO userDTO){
        this.name = userDTO.name();
        this.userName = userDTO.userName();
        this.password = userDTO.password();
        this.role = userDTO.role();
        this.email = userDTO.email();
    }
}
