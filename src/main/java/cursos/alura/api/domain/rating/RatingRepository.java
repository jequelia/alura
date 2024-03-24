package cursos.alura.api.domain.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r.registration.course.id, COUNT(r) FROM Rating r " +
            "WHERE r.ratingNps BETWEEN 9 AND 10 " +
            "GROUP BY r.registration.course.id")
    List<Object[]> countPromotersPerCourse();

    @Query("SELECT r.registration.course.id, COUNT(r) FROM Rating r " +
            "WHERE r.ratingNps BETWEEN 0 AND 6 " +
            "GROUP BY r.registration.course.id")
    List<Object[]> countDetractorsPerCourse();

    @Query("SELECT COUNT(r) FROM Rating r " +
            "WHERE r.registration.course.id = :courseId " +
            "AND r.ratingNps BETWEEN 9 AND 10")
    Long countPromotersForCourse(@Param("courseId") Long courseId);

    @Query("SELECT COUNT(r) FROM Rating r " +
            "WHERE r.registration.course.id = :courseId " +
            "AND r.ratingNps BETWEEN 0 AND 6")
    Long countDetractorsForCourse(@Param("courseId") Long courseId);

}
