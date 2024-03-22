package cursos.alura.api.domain.course;


import cursos.alura.api.domain.users.User;
import cursos.alura.api.domain.users.UserCreateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity(name = "Course")
@Table(name = "course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, length = 10, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deactivated_at")
    private ZonedDateTime deactivatedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = ZonedDateTime.now();
        status = true;

    }


    public Course( CourseCreateDTO courseDTO){
        this.name = courseDTO.name();
        this.code = courseDTO.code();
        this.description = courseDTO.description();
    }



    public void setInstructor(User userInstructor) {
        this.instructor = userInstructor;
    }

    public void setDeactivatedAt() {
        this.deactivatedAt = ZonedDateTime.now();
        this.status = false;
    }
}
