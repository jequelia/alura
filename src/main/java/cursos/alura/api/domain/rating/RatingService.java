package cursos.alura.api.domain.rating;

import cursos.alura.api.configuration.exception.CalculateNpsException;
import cursos.alura.api.configuration.exception.CourseNotFoundException;
import cursos.alura.api.configuration.exception.CourseOrganizationByStudentQuantityException;
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

        Rating entityRating = new Rating(ratingCreateDTO, registration);

        Rating rating = ratingRepository.save(entityRating);

        EmailSender.sendNegativeRatingEmail(rating);

    }

    public Page<RatingCourseNpsResultDTO> reportRating(Pageable page) {
        Map<Long, Long> studentsPerCourse = getStudentsPerCourse(registrationRepository.countStudentsPerCourse());

        Map<Long, Long> promotersPerCourse = getStudentsPerCourse(ratingRepository.countPromotersPerCourse());

        Map<Long, Long> detractorsPerCourse = getStudentsPerCourse(ratingRepository.countDetractorsPerCourse());

        List<RatingCourseNpsResultDTO> npsResults = getNpsResultDTOList(
                studentsPerCourse, promotersPerCourse, detractorsPerCourse);

        int pageSize = page.getPageSize();
        int currentPage = page.getPageNumber();
        int startItem = currentPage * pageSize;
        List<RatingCourseNpsResultDTO> pagedResults;

        if (npsResults.size() < startItem) {
            pagedResults = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, npsResults.size());
            pagedResults = npsResults.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedResults, PageRequest.of(currentPage, pageSize), npsResults.size());
    }

    public RatingCourseNpsResultDTO reportRatingByIdCourse(Long idCourse){
        Map<Long, Long> studentsPerCourse =  new HashMap<>();
        studentsPerCourse.put(idCourse, registrationRepository.countStudentsForCourse(idCourse));

        Map<Long, Long> promotersPerCourse =  new HashMap<>();
        promotersPerCourse.put(idCourse, ratingRepository.countPromotersForCourse(idCourse));

        Map<Long, Long> detractorsPerCourse = new HashMap<>();
        detractorsPerCourse.put(idCourse, ratingRepository.countDetractorsForCourse(idCourse));

        List<RatingCourseNpsResultDTO> npsResults = getNpsResultDTOList(
                studentsPerCourse, promotersPerCourse, detractorsPerCourse);

        return npsResults.getFirst();

    }

    private List<RatingCourseNpsResultDTO> getNpsResultDTOList(Map<Long, Long> studentsPerCourse,
                                                               Map<Long, Long> promotersPerCourse,
                                                               Map<Long, Long> detractorsPerCourse) {
        List<RatingCourseNpsResultDTO> npsResults = new ArrayList<>();

        for (Long courseId : studentsPerCourse.keySet()) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                Long studentsCount = studentsPerCourse.get(courseId);
                Long promotersCount = promotersPerCourse.getOrDefault(courseId, 0L);
                Long detractorsCount = detractorsPerCourse.getOrDefault(courseId, 0L);

                double nps = calculateNps(studentsCount, promotersCount, detractorsCount);

                boolean hasEnoughRatings = promotersCount >= 0 && detractorsCount >=0;

                RatingCourseNpsResultDTO resultDTO = new RatingCourseNpsResultDTO();
                resultDTO.setCodeCourse(course.getCode());
                resultDTO.setCourseName(course.getName());
                resultDTO.setPromoters(promotersCount.intValue());
                resultDTO.setDetractors(detractorsCount.intValue());
                resultDTO.setNps(nps);
                resultDTO.setHasEnoughRatings(hasEnoughRatings);

                npsResults.add(resultDTO);
            }
        }
        return npsResults;
    }

    private Map<Long, Long> getStudentsPerCourse(List<Object[]> studentResults) {
        Map<Long, Long> studentsPerCourse = new HashMap<>();
        try {
            for (Object[] result : studentResults) {
                Long courseId = (Long) result[0];
                Long studentCount = (Long) result[1];
                studentsPerCourse.put(courseId, studentCount);
            }
        } catch (Exception e) {
            throw new CourseOrganizationByStudentQuantityException(e.getMessage());
        }
        return studentsPerCourse;
    }


    private double calculateNps(long promotersCount, long neutralsCount, long detractorsCount) {
        long totalResponses = promotersCount + neutralsCount + detractorsCount;

        double nps = 0;
        try {
            if (totalResponses > 0) {
                double promotersPercentage = (double) promotersCount / totalResponses;
                double detractorsPercentage = (double) detractorsCount / totalResponses;

                nps = (promotersPercentage - detractorsPercentage) * 100;
            }
        } catch (Exception e) {
            throw new CalculateNpsException( e.getMessage());
        }
        return nps;
    }


}
