package com.example.back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.back.entity.Blog;
import com.example.back.entity.User;
import com.example.back.repository.BlogRepository;
import com.example.back.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    public DataInitializer(UserRepository userRepository, BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des utilisateurs existent déjà
        if (userRepository.count() == 0 && blogRepository.count() == 0) {
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

            // Créer des blogs de test
            Blog blog1 = new Blog("Mon premier blog", "Ceci est le contenu de mon premier blog. J'espère que vous l'apprécierez !", user1);
            Blog blog2 = new Blog("Voyage en France", "Récit de mon voyage à Paris et les endroits magnifiques que j'ai visités.", user2);
            Blog blog3 = new Blog("Technologie moderne", "Discussion sur les dernières avancées en intelligence artificielle et leur impact sur notre société.", user1);
            Blog blog4 = new Blog("Cuisine italienne", "Mes recettes préférées de pâtes et de pizzas faites maison.", user3);
            Blog blog5 = new Blog("Fitness et santé", "Conseils pour maintenir une vie saine et active au quotidien.", user4);
            Blog blog6 = new Blog("Photographie nature", "Astuces pour prendre de belles photos de paysages naturels.", user5);
            Blog blog7 = new Blog("Développement web", "Tutoriel sur les frameworks JavaScript modernes comme React et Vue.js.", user1);

            blogRepository.save(blog1);
            blogRepository.save(blog2);
            blogRepository.save(blog3);
            blogRepository.save(blog4);
            blogRepository.save(blog5);
            blogRepository.save(blog6);
            blogRepository.save(blog7);

            System.out.println("✅ Base de données seedée avec 7 blogs");
        } else {
            System.out.println("ℹ️  La base de données contient déjà des données");
            System.out.println("Nombre d'utilisateurs: " + userRepository.count());
            System.out.println("Nombre de blogs: " + blogRepository.count());
        }
    }
}