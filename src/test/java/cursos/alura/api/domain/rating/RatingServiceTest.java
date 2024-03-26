package cursos.alura.api.domain.rating;

import cursos.alura.api.domain.registration.Registration;
import cursos.alura.api.domain.registration.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class RatingServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;


    @Test
    public void testSaveRatingSuccessfulRating() {
        RatingCreateDTO ratingCreateDTO = new RatingCreateDTO(1L, 1L, 5, 5, "Great course!");
        Registration registration = new Registration();

        when(registrationRepository.existsByUserIdAndCourseId(anyLong(), anyLong())).thenReturn(true);
        when(registrationRepository.findRegistrationByUserIdAndCourseId(anyLong(), anyLong())).thenReturn(Optional.of(registration));
        when(ratingRepository.findRatingByRegistrationIdAndCourseId(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(ratingMapper.ratingCreateDTOtoRating(any(), any())).thenReturn(new Rating());

        assertDoesNotThrow(() -> ratingService.saveRating(ratingCreateDTO));

        verify(ratingRepository, times(1)).save(any());
    }
}