package cursos.alura.api.configuration.exception;

public class UserNotInstructorException extends RuntimeException {
    public UserNotInstructorException(String message) {
        super(message);

    }
}
