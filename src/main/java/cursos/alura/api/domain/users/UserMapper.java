package cursos.alura.api.domain.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User userCreateDTOtoUser(UserCreateDTO dto);

    UserDetailsDTO userToUserDetailDTO(User user);

}