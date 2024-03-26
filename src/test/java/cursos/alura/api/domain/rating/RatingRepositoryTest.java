package cursos.alura.api.domain.rating;

import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.registration.Registration;
import cursos.alura.api.domain.users.Role;
import cursos.alura.api.domain.users.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should return true if rating exists for given user id and course id")
    void testExistsByUserIdAndCourseId() {

        var student = createUser("jeque", "jeque", "jeque@gmail.com", "123456", Role.ESTUDANTE);
        var userInstructor = createUser("luana", "luana", "luana@gmail.com", "123456", Role.INSTRUTOR);
        var course = createCourse("Course Test", "test", userInstructor, "Description Test");
        var registration = createRegistration(course, student);
        createRating(registration, "Feedback", 5, 10);

        var result =  ratingRepository.findRatingByRegistrationIdAndCourseId(registration.getId(), course.getId());
        assertTrue(result.isPresent());
    }

    private Registration createRegistration(Course course, User user) {
        var registration = new Registration();
        registration.setCourse(course);
        registration.setUser(user);
        entityManager.persist(registration);
        return  registration;
    }

    private Rating createRating(Registration registration, String feedback, Integer rating, Integer ratingNps) {
        var ratingObj = new Rating();
        ratingObj.setRegistration(registration);
        ratingObj.setFeedback(feedback);
        ratingObj.setRating(rating);
        ratingObj.setRatingNps(ratingNps);
        entityManager.persist(ratingObj);
        return ratingObj;
    }


    private Course createCourse(String name, String code, User instructorUser, String description) {
        var course = new Course();
        course.setName(name);
        course.setCode(code);
        course.setDescription(description);
        course.setInstructor(instructorUser);
        entityManager.persist(course);
        return course;
    }

    private User createUser(String name, String userName, String email, String password, Role role) {
        var user = new User();
        user.setName(name);
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        entityManager.persist(user);
        return user;
    }

}