package co.yasok.npdoc.controller;

import co.yasok.npdoc.dto.DoctorDTO;
import co.yasok.npdoc.dto.LoginRequestDTO;
import co.yasok.npdoc.dto.UserDTO;
import co.yasok.npdoc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam(required = true) String fullName, @RequestParam(required = true) String email, @RequestParam(required = true) String password) {
        UserDTO user = userService.registerUser(fullName, email, password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<UserDTO> user = userService.loginUser(email, password);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<DoctorDTO> users = userService.getDoctors();
        return ResponseEntity.ok(users);
    }
}
