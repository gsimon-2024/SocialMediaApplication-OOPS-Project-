package com.example.testtwitter.service;

import com.example.testtwitter.Entity.Post;
import com.example.testtwitter.Repo.PostRepository;
import com.example.testtwitter.Repo.UserRepository;
import com.example.testtwitter.Entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.testtwitter.JsonConverter.convertToJson;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public User getUserDetails(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public List<Post> getUserFeed(Long userId) {
        return postRepository.findByUserIdOrderByDateDesc(userId);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
   public String login(String email, String password) {

       User user = userRepository.findByEmail(email);
       if (user != null) {
           if (user.getPassword().equals(password)) {
               return "Login Successful";
           } else {
               return convertToJson("Username/Password Incorrect");
           }
       } else {
           return convertToJson("User does not exist");
       }
    }



    public String signup(String email, String name, String password) {
        if (userRepository.existsByEmail(email)) {
            return convertToJson("Forbidden, Account already exists");
        } else {
            User newUser = new User(email, name, password);
            userRepository.save(newUser);
            return "Account Creation Successful";
        }
    }
}
