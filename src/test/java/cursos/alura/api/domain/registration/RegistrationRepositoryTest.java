package cursos.alura.api.domain.registration;

import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.users.Role;
import cursos.alura.api.domain.users.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RegistrationRepositoryTest {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should return true if exists registration by user id and course id")
    void testExistsByUserIdAndCourseId() {

        var user = createUser("User Test", "estud", "estud@gmail.com", "123456", Role.ESTUDANTE);
        var userInstructor = createUser("User Test", "instruct", "instruct@gmail.com", "123456", Role.INSTRUTOR);
        var course = createCourse("Course Test", "course", userInstructor, "Description Test");
        var reg = createRegistration(course, user);


       var result =  registrationRepository.existsByUserIdAndCourseId(user.getId(), course.getId());
       assertTrue(result);
    }
    @Test
    @DisplayName("Should return false if  no exists registration by user id and course id")
    void testNoExistsByUserIdAndCourseId() {

        var user = createUser("User Test", "estud", "estud@gmail.com", "123456", Role.ESTUDANTE);
        var userInstructor = createUser("User Test", "instruct", "instruct@gmail.com", "123456", Role.INSTRUTOR);
        var course = createCourse("Course Test", "course", userInstructor, "Description Test");
        var reg = createRegistration(course, user);
        var newStudent = createUser("User new", "newestud", "newestud@gmail.com", "123456", Role.ESTUDANTE);


       var result =  registrationRepository.existsByUserIdAndCourseId(newStudent.getId(), course.getId());
       assertFalse(result);
    }
    @Test
    @DisplayName("find by courses with more than four records")
    void testNoCoursesWithMoreThanFourRegistrations() {

        var user = createUser("joao", "joao", "joao@gmail.com", "123456", Role.ESTUDANTE);
        var userInstructor = createUser("maria", "maria", "mariat@gmail.com", "123456", Role.INSTRUTOR);
        var course = createCourse("programacao", "prog", userInstructor, "Description Test");
        createRegistration(course, user);

        var user2 = createUser("ana", "ana", "ana@gmail.com", "123456", Role.ESTUDANTE);
        var userInstructor2 = createUser("ester", "ester", "ester@gmail.com", "123456", Role.INSTRUTOR);
        var course2 = createCourse("dados", "dados", userInstructor2, "Description Test");
        createRegistration(course2, user2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
       var result =  registrationRepository.findByCoursesWithMoreThanFourRecords( pageable ).get();
        Assertions.assertThat(result).isEmpty();
    }





    private Registration createRegistration(Course course, User user) {
        var registration = new Registration();
        registration.setCourse(course);
        registration.setUser(user);
        entityManager.persist(registration);
        return  registration;
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
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        entityManager.persist(user);
        return user;
    }

}