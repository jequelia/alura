package cursos.alura.api.domain.course;


import cursos.alura.api.domain.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.ZonedDateTime;

@Entity
@Table(name = "course")
@Getter
@Setter
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

    @Setter
    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    @Column(name = "status", nullable = false)
    private Boolean status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deactivated_at")
    private ZonedDateTime deactivatedAt;

    public void setDeactivatedAt() {
        this.deactivatedAt = ZonedDateTime.now();
        this.status = false;
    }

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = true;
        }
    }
}
