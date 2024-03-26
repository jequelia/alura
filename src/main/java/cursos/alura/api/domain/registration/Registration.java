package cursos.alura.api.domain.registration;

import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "registration")
@Entity(name = "Registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "registration_at")
    private LocalDateTime registrationAt;

    @PrePersist
    protected void onCreate() {
        registrationAt = LocalDateTime.now();
    }


}
