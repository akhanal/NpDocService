package co.yasok.npdoc.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="doctor", uniqueConstraints = {@UniqueConstraint(name="UX_DOCTOR_NMC", columnNames = {"nmcNumber"})})
public class Doctor extends User {

    @Column(nullable = false, unique = true)
    private String nmcNumber;

    private String specialty;

    private String bio;

    public Doctor() {
        // Default constructor
        super();
        super.setUserType(UserType.DOCTOR);
    }

    public Doctor(String fullName, String email, String password, String nmcNumber, String specialty, String bio) {
        super(fullName, email, password);
        super.setUserType(UserType.DOCTOR);
        this.specialty = specialty;
        this.bio = bio;
        this.nmcNumber = nmcNumber;
    }

    // Getters and setters
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

    public String getNmcNumber() {
        return nmcNumber;
    }

    public void setNmcNumber(String nmcNumber) {
        this.nmcNumber = nmcNumber;
    }
}
