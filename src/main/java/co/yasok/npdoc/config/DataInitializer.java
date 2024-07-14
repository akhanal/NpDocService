package co.yasok.npdoc.config;

import co.yasok.npdoc.entity.Doctor;
import co.yasok.npdoc.entity.User;
import co.yasok.npdoc.repo.DoctorRepository;
import co.yasok.npdoc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if there are any users already in the database
        if (userRepository.count() == 0) {
            // Add some initial users
            User user1 = new User("Ankit Khanal", "ankit@yasok.co", "password");
            User user2 = new User("Hari Sharma", "hari@yasok.co", "password");
            userRepository.save(user1);
            userRepository.save(user2);

            Doctor user3 = new Doctor("Jhuma Silwal", "jhuma@yasok.co", "password","231",null,null);
            Doctor user4 = new Doctor("Tulsiram Bhattarai", "tulsi@yasok.co", "password", "434", null, null);


            doctorRepository.save(user3);
            doctorRepository.save(user4);
        }
    }
}
