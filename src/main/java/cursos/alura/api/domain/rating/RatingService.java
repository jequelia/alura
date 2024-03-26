package cursos.alura.api.domain.rating;

import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.CourseOrganizationByStudentQuantityException;
import cursos.alura.api.configuration.exception.RatingException;
import cursos.alura.api.configuration.exception.UserRegistrationInCourseException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseRepository;
import cursos.alura.api.domain.registration.Registration;
import cursos.alura.api.domain.registration.RegistrationRepository;
import cursos.alura.api.domain.rating.notification.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RegistrationRepository registrationRepository;
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final RatingMapper ratingMapper;

    public void saveRating(RatingCreateDTO ratingCreateDTO) {

        if (!registrationRepository.existsByUserIdAndCourseId(ratingCreateDTO.idUser(), ratingCreateDTO.idCourse())) {
            throw new UserRegistrationInCourseException("Registration for this user in the course was not found.");
        }

        Registration registration = registrationRepository.findRegistrationByUserIdAndCourseId(ratingCreateDTO.idUser(),ratingCreateDTO.idCourse())
                .orElseThrow(() -> new UserRegistrationInCourseException("Registration not found."));

        ratingRepository.findRatingByRegistrationIdAndCourseId(registration.getId(), ratingCreateDTO.idCourse())
                .ifPresent(rating -> {
                    throw new RatingException("Rating already exists.");
                });

        Rating entityRating = ratingMapper.ratingCreateDTOtoRating(ratingCreateDTO, registration);

        Rating rating = ratingRepository.save(entityRating);

        EmailSender.sendNegativeRatingEmail(rating);

    }

    public Page<RatingCourseNpsResultDTO> reportRating(Pageable page) {

        Page<Course> coursesWithMoreThanFourRecords =
                registrationRepository.findByCoursesWithMoreThanFourRecords(page);

        if(coursesWithMoreThanFourRecords.isEmpty()){
            throw new CourseOrganizationByStudentQuantityException("There are no courses with more than four records.");
        }

        return coursesWithMoreThanFourRecords.map(this::calculateNps);
    }

    private RatingCourseNpsResultDTO calculateNps(Course item) {

        List<Long> ratingForCourseById = ratingRepository.ratingForCourseById(item.getId());

        var respondents = ratingForCourseById.size();

        RatingCourseNpsResultDTO ratingCourseNpsResultDTO = new RatingCourseNpsResultDTO();
        ratingCourseNpsResultDTO.setCodeCourse(item.getCode());
        ratingCourseNpsResultDTO.setCourseName(item.getName());
        ratingCourseNpsResultDTO.setHasEnoughRatings(respondents >= 4);


        var promoters = ratingForCourseById.stream().filter(rating -> rating >= 9).count();
        var detractors = ratingForCourseById.stream().filter(rating -> rating < 6).count();

        Double nps = ((double )promoters - detractors) / respondents * 100;

        ratingCourseNpsResultDTO.setNps(String.format("%.2f%%", nps));

        ratingCourseNpsResultDTO.setDetractors(detractors);
        ratingCourseNpsResultDTO.setPromoters(promoters);

        return ratingCourseNpsResultDTO;
    }


    public RatingCourseNpsResultDTO reportRatingByIdCourse(Long idCourse){

        return calculateNps(courseRepository.findById(idCourse)
                .orElseThrow(() -> new CourseNotFoundException("Course not found.")));

    }


}
