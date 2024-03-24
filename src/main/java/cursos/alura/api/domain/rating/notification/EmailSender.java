package cursos.alura.api.domain.rating.notification;

import cursos.alura.api.configuration.exception.SendEmailException;
import cursos.alura.api.domain.course.Course;
import cursos.alura.api.domain.rating.Rating;

public class EmailSender {
    public static void send(String recipientEmail, String subject, String body) {
        System.out.println(
                "Simulating sending email to [%s]:\n".formatted(recipientEmail)
        );
        System.out.println("""
                Subject: %s
                Body: %s
                """.formatted(subject, body));
    }


    public static void sendNegativeRatingEmail(Rating rating) {
        try {
            if(rating != null){
                if(rating.getRating() < 6){
                    Course course = rating.getRegistration().getCourse();
                    String emailInstructor = course.getInstructor().getEmail();
                    String subject = "Negative course evaluation: " + course.getName() + " | " + course.getCode() ;
                    String body = "Evaluation: " + rating.getRating() + " | Feedback: " + rating.getFeedback() ;
                    send(emailInstructor,subject,body);
                }
            }
        } catch (Exception e) {
            throw new SendEmailException( e.getMessage());
        }

    }
}
