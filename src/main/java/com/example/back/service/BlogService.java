package com.example.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.back.entity.Blog;
import com.example.back.entity.User;
import com.example.back.repository.BlogRepository;
import com.example.back.repository.UserRepository;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getBlogsByUserId(Long userId) {
        return blogRepository.findByUserId(userId);
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog createBlog(Long userId, Blog blog) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            blog.setUser(user.get());
            return blogRepository.save(blog);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Blog updateBlog(Long id, Blog blogDetails) {
        return blogRepository.findById(id).map(blog -> {
            blog.setTitle(blogDetails.getTitle());
            blog.setContent(blogDetails.getContent());
            return blogRepository.save(blog);
        }).orElse(null);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}