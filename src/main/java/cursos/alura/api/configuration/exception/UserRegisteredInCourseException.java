package cursos.alura.api.configuration.exception;

public class UserRegisteredInCourseException extends RuntimeException {
    public UserRegisteredInCourseException(String message) {
        super(message);
    }
}
