package co.yasok.npdoc.dto;

public class DoctorDTO extends UserDTO {
    public DoctorDTO() {
        super();
    }
    public DoctorDTO(Long id, String fullName, String email, String userType, String nmcNumber, String specialty, String bio) {
        super(id, fullName, email, userType);
        this.nmcNumber = nmcNumber;
        this.specialty = specialty;
        this.bio = bio;
    }

    private String nmcNumber;

    private String specialty;

    private String bio;

    public String getNmcNumber() {
        return nmcNumber;
    }

    public void setNmcNumber(String nmcNumber) {
        this.nmcNumber = nmcNumber;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
