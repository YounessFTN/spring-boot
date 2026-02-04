package com.example.back;

import com.example.back.entity.User;
import com.example.back.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des utilisateurs existent déjà
        if (userRepository.count() == 0) {
            // Créer et sauvegarder des utilisateurs de test
            User user1 = new User("Youness", "youness@example.com", "+212612345678");
            User user2 = new User("Alice Martin", "alice.martin@example.com", "+33612345678");
            User user3 = new User("Bob Johnson", "bob.johnson@example.com", "+33687654321");
            User user4 = new User("Sarah Williams", "sarah.williams@example.com", "+33698765432");
            User user5 = new User("David Brown", "david.brown@example.com", "+33712345678");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);

            System.out.println("✅ Base de données seedée avec 5 utilisateurs");
        } else {
            System.out.println("ℹ️  La base de données contient déjà des données");
        }
    }
}