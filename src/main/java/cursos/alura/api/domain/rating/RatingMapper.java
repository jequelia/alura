package cursos.alura.api.domain.rating;

import cursos.alura.api.domain.registration.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {

    @Mapping(target = "registration.id", source = "registrationData.id")
    Rating ratingCreateDTOtoRating(RatingCreateDTO dto, Registration registrationData);

}