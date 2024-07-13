package co.yasok.npdoc.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String userType;

    // Constructor
    public UserDTO(Long id, String email, String userType) {
        this.id = id;
        this.email = email;
        this.userType = userType;
    }

    // Getters and Setters
}
