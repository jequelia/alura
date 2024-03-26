package cursos.alura.api.configuration.exception;

import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("User provided was not found in the database.");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;

    }

    @ExceptionHandler(CourseNotFoundException.class)
    ProblemDetail handleCourseNotFoundException(CourseNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("Course provided was not found in the database.");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserNotInstructorException.class)
    ProblemDetail handleUserNotInstructorException(UserNotInstructorException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("Need to be an instructor.");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CalculateNpsException.class)
    ProblemDetail handleCalculateNpsException(CalculateNpsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error calculate NPS");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CourseOrganizationByStudentQuantityException.class)
    ProblemDetail handleCourseOrganizationByStudentQuantityException(CourseOrganizationByStudentQuantityException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error when organizing course by number of students");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(SendEmailException.class)
    ProblemDetail handleSendEmailException(SendEmailException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error send email");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserRegistrationInCourseException.class)
    ProblemDetail handleUserRegisteredInCourseException(UserRegistrationInCourseException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error while querying the registration");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }
    @ExceptionHandler(RatingException.class)
    ProblemDetail handleRatingException(RatingException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error while trying to register the rating.");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error when trying to create a user");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }
    @ExceptionHandler(CourserExistException.class)
    ProblemDetail handleCourserExistException(CourserExistException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error when trying to create a course");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("TimeStamp", Instant.now());
        return new ResponseEntity<>(problemDetail, headers, status);
    }

}