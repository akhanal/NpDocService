package co.yasok.npdoc.service;

import co.yasok.npdoc.dto.DoctorDTO;
import co.yasok.npdoc.dto.UserDTO;
import co.yasok.npdoc.entity.Doctor;
import co.yasok.npdoc.entity.User;
import co.yasok.npdoc.entity.UserType;
import co.yasok.npdoc.repo.DoctorRepository;
import co.yasok.npdoc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public UserService(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public UserDTO registerUser(String fullName, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(), savedUser.getUserType().name());
    }

    public Optional<UserDTO> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.map(u -> new UserDTO(u.getId(), u.getFullName(), u.getEmail(), u.getUserType().name()));
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findByUserType(UserType.PATIENT);
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getFullName(), user.getEmail(), user.getUserType().name()))
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctor-> new DoctorDTO(doctor.getId(), doctor.getFullName(), doctor.getEmail(), doctor.getUserType().name(),doctor.getNmcNumber(), doctor.getSpecialty(), doctor.getBio()))
                .collect(Collectors.toList());
    }
}
