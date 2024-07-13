package co.yasok.npdoc.service;

import co.yasok.npdoc.dto.UserDTO;
import co.yasok.npdoc.entity.User;
import co.yasok.npdoc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(String email, String password, String userType) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(userType);
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getUserType());
    }

    public Optional<UserDTO> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.map(u -> new UserDTO(u.getId(), u.getEmail(), u.getUserType()));
    }

    public List<UserDTO> getUsersByType(String userType) {
        List<User> users = userRepository.findByUserType(userType);
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getUserType()))
                .collect(Collectors.toList());
    }
}
