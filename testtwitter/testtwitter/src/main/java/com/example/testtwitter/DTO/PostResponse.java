package com.example.testtwitter.DTO;

import com.example.testtwitter.Entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class PostResponse {
    private List<Post> posts;
    private String message;

    public PostResponse(List<Post> posts) {
        this.posts = posts;
        this.message = posts.isEmpty() ? "No posts found" : null;
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBody(ResponseEntity<?> responseEntity) {
        return (T) responseEntity.getBody();
    }
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
