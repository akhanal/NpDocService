package co.yasok.npdoc.config;

import co.yasok.npdoc.entity.User;
import co.yasok.npdoc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if there are any users already in the database
        if (userRepository.count() == 0) {
            // Add some initial users
            User user1 = new User("Ankit Khanal", "ankit@yasok.co", "password", "PATIENT");
            User user2 = new User("Hari Sharma", "hari@yasok.co", "password", "PATIENT");
            User user3 = new User("Jhuma Silwal", "jhuma@yasok.co", "password", "DOCTOR");
            User user4 = new User("Tulsiram Bhattarai", "tulsi@yasok.co", "password", "DOCTOR");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
        }
    }
}
