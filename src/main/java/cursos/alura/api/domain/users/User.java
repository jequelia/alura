package cursos.alura.api.domain.users;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Table(name = "users")
@Entity
@Getter
@Setter
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
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;

    @Column(name = "role", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Role role;


}
