package cursos.alura.api.domain.notification;

import cursos.alura.api.configuration.exception.SendEmailException;
import cursos.alura.api.configuration.producer.Producer;
import cursos.alura.api.domain.rating.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final Producer producer;

    public void sendNegativeRatingEmail(Rating rating) {
        try {
            if(rating != null){
                if(rating.getRating() < 6){

                    Notification notification = new Notification(rating.getRegistration().getCourse().getInstructor().getEmail(),
                            rating.getRegistration().getCourse().getName(),
                            "Evaluation: " + rating.getRating() + " | Feedback: " + rating.getFeedback(),
                            "Negative course evaluation: " + rating.getRegistration().getCourse().getName() + " | " + rating.getRegistration().getCourse().getCode());
                    producer.sendMessage(notification);
                }
            }
        } catch (Exception e) {
            throw new SendEmailException( e.getMessage());
        }

    }


}
