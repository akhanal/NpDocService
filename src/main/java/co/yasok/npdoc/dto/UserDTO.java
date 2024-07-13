package co.yasok.npdoc.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String userType;

    public UserDTO() {

    }

    // Constructor
    public UserDTO(Long id, String email, String userType) {
        this.id = id;
        this.email = email;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
