package cursos.alura.api.domain.users;

import cursos.alura.api.configuration.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper userMapper;

    public UserDetailsDTO createUser(UserCreateDTO userDTO) {
        User user = userMapper.userCreateDTOtoUser(userDTO);

        if (repository.existsByUsername(user.getUsername()) || repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists.");
        }

        repository.save(user);
        return userMapper.userToUserDetailDTO(user);
    }

    public UserDetailsDTO getUserByUsername(String username) {
       User user = repository.findByUsername(username).orElse(null);
       return userMapper.userToUserDetailDTO(user);
    }

    public UserDetailsDTO getUserById(Long userId) {
        User user = repository.findById(userId).orElse(null);
        return userMapper.userToUserDetailDTO(user);

    }
}
