package cursos.alura.api.domain.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("""
            SELECT r.ratingNps
            FROM Rating r
            WHERE r.registration.course.id
             = :courseId
            
            """)
    List<Long> ratingForCourseById(@Param("courseId") Long courseId);

    @Query("""
            SELECT r
            FROM Rating r
            WHERE r.registration.course.id = :idCourse AND r.registration.id = :idRegistration
            """)
    Optional<Rating> findByRegistrationIdAndCourseId( @Param("idRegistration")Long idRegistration,
                                                      @Param("idCourse") Long idCourse);
}
