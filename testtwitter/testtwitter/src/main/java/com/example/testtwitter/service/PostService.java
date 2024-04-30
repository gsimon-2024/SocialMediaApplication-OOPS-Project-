package com.example.testtwitter.service;

import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.Post;
import com.example.testtwitter.Repo.CommentRepository;
import com.example.testtwitter.Repo.PostRepository;
import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.testtwitter.JsonConverter.convertToJson;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

   public String createPost(String postBody, Long userId) {
       // Check if the user exists
       Optional<User> optionalUser = userRepository.findById(userId);
       if (optionalUser.isPresent()) {
           User user = optionalUser.get();
           // Create the post
           Post post = new Post(postBody, LocalDateTime.now(), user);
           postRepository.save(post);
           return "Post created successfully";
       } else {

           return convertToJson("User not found");
       }
   }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getAllPostsAndComments(Long postId) {
        if (postId != null) {
            return List.of(postRepository.findByIdWithComments(postId).orElse(null));
        } else {
            return postRepository.findAllWithComments();
        }
    }
    public List<Post> getAllPostsWithComments() {
        List<Post> allPosts = postRepository.findAll();
        for (Post post : allPosts) {
            List<Comment> comments = commentRepository.findByPostId(post.getId());
            post.setComments(comments);
        }
        return allPosts;
    }


    public String editPost(Long postId, String postBody) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setPostBody(postBody);
            postRepository.save(post);
            return "Post edited successfully";
        } else {
            return convertToJson("Post does not exist");
        }
    }

    public String deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return "Post deleted";
        } else {
            return convertToJson("Post does not exist");
        }
    }
}
