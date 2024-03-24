package cursos.alura.api.domain.rating;

import cursos.alura.api.domain.registration.Registration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Table(name = "rating")
@Entity(name = "Rating")
@Getter
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


    public Rating(RatingCreateDTO ratingCreateDTO, Registration registration){

        this.registration = registration;
        this.rating = ratingCreateDTO.rating();
        this.ratingNps = ratingCreateDTO.ratingNps();
        this.feedback = ratingCreateDTO.feedback();

    };
}
