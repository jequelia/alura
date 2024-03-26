package cursos.alura.api.domain.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByStatus(Pageable paginacao, Boolean status);

    Optional<Course> findByIdAndStatus(Long courseId, boolean status);

    Optional<Course> findByCode(String code);
}
