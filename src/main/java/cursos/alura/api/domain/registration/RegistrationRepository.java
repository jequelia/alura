package cursos.alura.api.domain.registration;

import cursos.alura.api.domain.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId AND r.course.id = :courseId")
    Optional<Registration> findRegistrationByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query("""
            SELECT c
            FROM Course c
            WHERE (SELECT COUNT(r)
                   FROM Registration r
                   WHERE r.course = c) > 4
                        
            """)
    Page<Course> findByCoursesWithMoreThanFourRecords(Pageable pageable);




}
