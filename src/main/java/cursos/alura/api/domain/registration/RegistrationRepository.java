package cursos.alura.api.domain.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId AND r.course.id = :courseId")
    Optional<Registration> findRegistrationByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query("SELECT r.course.id, COUNT(r.user.id) FROM Registration r GROUP BY r.course.id HAVING COUNT(r.user.id) > 4")
    List<Object[]> countStudentsPerCourse();
    @Query("SELECT  COUNT(r.user.id) FROM Registration r WHERE r.course.id = :courseId")
    Long countStudentsForCourse(@Param("courseId") Long courseId);




}
