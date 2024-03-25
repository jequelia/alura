package cursos.alura.api.domain.rating;

import cursos.alura.api.configuration.exception.CalculateNpsException;
import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.CourseOrganizationByStudentQuantityException;
import cursos.alura.api.configuration.exception.RatingException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.course.CourseRepository;
import cursos.alura.api.domain.registration.Registration;
import cursos.alura.api.domain.registration.RegistrationRepository;
import cursos.alura.api.domain.rating.notification.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class RatingService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private CourseRepository courseRepository;

    public void saveRating(RatingCreateDTO ratingCreateDTO) {

        if (!registrationRepository.existsByUserIdAndCourseId(ratingCreateDTO.idUser(), ratingCreateDTO.idCourse())) {
            throw new IllegalStateException("User is already enrolled in this course.");
        }

        Registration registration = registrationRepository.findRegistrationByUserIdAndCourseId(ratingCreateDTO.idUser(),ratingCreateDTO.idCourse())
                .orElseThrow(() -> new CourseNotFoundException("Registration not found."));

        ratingRepository.findByRegistrationIdAndCourseId(registration.getId(), ratingCreateDTO.idCourse())
                .ifPresent(rating -> {
                    throw new RatingException("Rating already exists.");
                });

        Rating entityRating = new Rating(ratingCreateDTO, registration);

        Rating rating = ratingRepository.save(entityRating);

        EmailSender.sendNegativeRatingEmail(rating);

    }

    public Page<RatingCourseNpsResultDTO> reportRating(Pageable page) {

        Page<Course> coursesWithMoreThanFourRecords =
                registrationRepository.findByCoursesWithMoreThanFourRecords(page);

        if(coursesWithMoreThanFourRecords.isEmpty()){
            throw new CourseOrganizationByStudentQuantityException("There are no courses with more than four records.");
        }

        Page<RatingCourseNpsResultDTO> report = coursesWithMoreThanFourRecords.map(item -> mapPromotersAndDetractors(item));

        return report;
    }

    private RatingCourseNpsResultDTO mapPromotersAndDetractors(Course item) {

        List<Long> ratingForCourseById = ratingRepository.ratingForCourseById(item.getId());

        var respondents = ratingForCourseById.size();

        RatingCourseNpsResultDTO ratingCourseNpsResultDTO = new RatingCourseNpsResultDTO();
        ratingCourseNpsResultDTO.setCodeCourse(item.getCode());
        ratingCourseNpsResultDTO.setCourseName(item.getName());
        ratingCourseNpsResultDTO.setHasEnoughRatings(respondents >= 4);


        var promotersList = ratingForCourseById.stream().filter(rating -> rating >= 9).toList();
        var detractorsList = ratingForCourseById.stream().filter(rating -> rating < 6).toList();
        var promoters = promotersList.size();
        var detractors = detractorsList.size();

        Double nps = ((double )promoters - detractors) / respondents * 100;

        ratingCourseNpsResultDTO.setNps(String.format("%.2f%%", nps));

        ratingCourseNpsResultDTO.setDetractors(detractors);
        ratingCourseNpsResultDTO.setPromoters(promoters);

        return ratingCourseNpsResultDTO;
    }


    public RatingCourseNpsResultDTO reportRatingByIdCourse(Long idCourse){

        return mapPromotersAndDetractors(courseRepository.findById(idCourse)
                .orElseThrow(() -> new CourseNotFoundException("Course not found.")));

    }


}
