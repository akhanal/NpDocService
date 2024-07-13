package co.yasok.npdoc.controller;

import co.yasok.npdoc.dto.UserDTO;
import co.yasok.npdoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam String email, @RequestParam String password, @RequestParam String userType) {
        UserDTO user = userService.registerUser(email, password, userType);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String email, @RequestParam String password) {
        Optional<UserDTO> user = userService.loginUser(email, password);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsersByType(@RequestParam String userType) {
        List<UserDTO> users = userService.getUsersByType(userType);
        return ResponseEntity.ok(users);
    }
}
