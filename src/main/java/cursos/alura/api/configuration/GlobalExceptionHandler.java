package cursos.alura.api.configuration;

import cursos.alura.api.configuration.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("User provided was not found in the database.");
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;

    }

    @ExceptionHandler(CourseNotFoundException.class)
    ProblemDetail handleCourseNotFoundException(CourseNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("Course provided was not found in the database.");
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserNotInstructorException.class)
    ProblemDetail handleUserNotInstructorException(UserNotInstructorException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setDetail("Need to be an instructor.");
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CalculateNpsException.class)
    ProblemDetail handleCalculateNpsException(CalculateNpsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error calculate NPS");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CourseOrganizationByStudentQuantityException.class)
    ProblemDetail handleCourseOrganizationByStudentQuantityException(CourseOrganizationByStudentQuantityException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error when organizing course by number of students");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(SendEmailException.class)
    ProblemDetail handleSendEmailException(SendEmailException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error send email");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserRegisteredInCourseException.class)
    ProblemDetail handleUserRegisteredInCourseException(UserRegisteredInCourseException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle("Error when trying to enroll the user in the course");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handlerError400(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setTitle(e.getMessage());
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        problemDetail.setProperty("TimeStamp", Instant.now());
        return problemDetail;
    }
}