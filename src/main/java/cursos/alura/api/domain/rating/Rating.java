package cursos.alura.api.domain.rating;

import cursos.alura.api.domain.registration.Registration;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "rating")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "registration_id", referencedColumnName = "id")
    private Registration registration;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "rating_nps")
    private Integer ratingNps;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
