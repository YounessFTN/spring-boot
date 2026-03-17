package com.example.back;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.back.entity.Author;
import com.example.back.entity.Blog;
import com.example.back.entity.Book;
import com.example.back.entity.Category;
import com.example.back.entity.User;
import com.example.back.repository.AuthorRepository;
import com.example.back.repository.BlogRepository;
import com.example.back.repository.BookRepository;
import com.example.back.repository.CategoryRepository;
import com.example.back.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, BlogRepository blogRepository,
                           AuthorRepository authorRepository, BookRepository bookRepository,
                           CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            seedUsers();
        }
        if (authorRepository.count() == 0) {
            seedBooks();
        }
    }

    private void seedUsers() {
        User user1 = userRepository.save(new User("Youness", "youness@example.com", "+212612345678", passwordEncoder.encode("password123")));
        User user2 = userRepository.save(new User("Alice Martin", "alice.martin@example.com", "+33612345678", passwordEncoder.encode("password123")));
        User user3 = userRepository.save(new User("Bob Johnson", "bob.johnson@example.com", "+33687654321", passwordEncoder.encode("password123")));
        User user4 = userRepository.save(new User("Sarah Williams", "sarah.williams@example.com", "+33698765432", passwordEncoder.encode("password123")));
        User user5 = userRepository.save(new User("David Brown", "david.brown@example.com", "+33712345678", passwordEncoder.encode("password123")));

        System.out.println("✅ 5 utilisateurs seedés");

        Blog blog1 = new Blog("Mon premier blog", "Ceci est le contenu de mon premier blog. J'espère que vous l'apprécierez !", user1);
        blog1.setCreatedAt(LocalDateTime.now().minusDays(14));

        Blog blog2 = new Blog("Voyage en France", "Récit de mon voyage à Paris et les endroits magnifiques que j'ai visités.", user2);
        blog2.setCreatedAt(LocalDateTime.now().minusDays(10));

        Blog blog3 = new Blog("Technologie moderne", "Discussion sur les dernières avancées en IA et leur impact sur notre société.", user1);
        blog3.setCreatedAt(LocalDateTime.now().minusDays(7));

        Blog blog4 = new Blog("Cuisine italienne", "Mes recettes préférées de pâtes et de pizzas faites maison.", user3);
        blog4.setCreatedAt(LocalDateTime.now().minusDays(5));

        Blog blog5 = new Blog("Fitness et santé", "Conseils pour maintenir une vie saine et active au quotidien.", user4);
        blog5.setCreatedAt(LocalDateTime.now().minusDays(3));

        Blog blog6 = new Blog("Photographie nature", "Astuces pour prendre de belles photos de paysages naturels.", user5);
        blog6.setCreatedAt(LocalDateTime.now().minusDays(2));

        Blog blog7 = new Blog("Développement web", "Tutoriel sur les frameworks JavaScript modernes comme React et Vue.js.", user1);
        blog7.setCreatedAt(LocalDateTime.now().minusHours(6));

        blogRepository.save(blog1);
        blogRepository.save(blog2);
        blogRepository.save(blog3);
        blogRepository.save(blog4);
        blogRepository.save(blog5);
        blogRepository.save(blog6);
        blogRepository.save(blog7);

        System.out.println("✅ 7 blogs seedés");
    }

    private void seedBooks() {
        Category fiction     = categoryRepository.save(new Category("Fiction"));
        Category science     = categoryRepository.save(new Category("Science"));
        Category history     = categoryRepository.save(new Category("Histoire"));
        Category technology  = categoryRepository.save(new Category("Technologie"));
        Category philosophy  = categoryRepository.save(new Category("Philosophie"));

        System.out.println("✅ 5 catégories seedées");

        Author tolkien = authorRepository.save(new Author("J.R.R. Tolkien"));
        Author asimov  = authorRepository.save(new Author("Isaac Asimov"));
        Author hawking = authorRepository.save(new Author("Stephen Hawking"));
        Author orwell  = authorRepository.save(new Author("George Orwell"));

        System.out.println("✅ 4 auteurs seedés");

        Book book1 = new Book("Le Seigneur des Anneaux", tolkien);
        book1.setCategories(Set.of(fiction));
        bookRepository.save(book1);

        Book book2 = new Book("Le Hobbit", tolkien);
        book2.setCategories(Set.of(fiction, history));
        bookRepository.save(book2);

        Book book3 = new Book("Fondation", asimov);
        book3.setCategories(Set.of(fiction, science));
        bookRepository.save(book3);

        Book book4 = new Book("Les Caves d'Acier", asimov);
        book4.setCategories(Set.of(fiction, science));
        bookRepository.save(book4);

        Book book5 = new Book("Une brève histoire du temps", hawking);
        book5.setCategories(Set.of(science));
        bookRepository.save(book5);

        Book book6 = new Book("1984", orwell);
        book6.setCategories(Set.of(fiction, philosophy));
        bookRepository.save(book6);

        Book book7 = new Book("La Ferme des Animaux", orwell);
        book7.setCategories(Set.of(fiction, philosophy));
        bookRepository.save(book7);

        System.out.println("✅ 7 livres seedés");
    }
}