package com.example.testtwitter.controller;
import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Entity.Post;
import com.example.testtwitter.service.CommentService;
import com.example.testtwitter.service.PostService;
import com.example.testtwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.testtwitter.DTO.PostResponse.getBody;
import static com.example.testtwitter.Entity.Post.getMaxPostId;
import static com.example.testtwitter.JsonConverter.convertToJsonResponse;

/*@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam Long userID) {
        com.example.testtwitter.Entity.User user = userService.getUserDetails(userID);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }

    @GetMapping("/user/{userId}/feed")
    public ResponseEntity<?> getUserFeed(@PathVariable Long userId) {
        List<Post> userFeed = userService.getUserFeed(userId);
        return ResponseEntity.ok(userFeed);
    }

    /*@GetMapping("/")
    public ResponseEntity<?> getAllPostsAndComments() {
        List<Post> allPosts = postService.getAllPostsAndComments(null);
        return ResponseEntity.ok(allPosts);
    }*/
  /*  @GetMapping("/")
    public ResponseEntity<?> getAllPostsAndComments() {
        List<Post> allPosts = postService.getAllPosts();
        for (Post post : allPosts) {
            System.out.println("Searching comments");
            List<Comment> comments = commentService.getCommentsByPostId(post.getId());
            post.setComments(comments);
        }
        return ResponseEntity.ok(allPosts);
    }*/
    /*@GetMapping("/")
    public ResponseEntity<?> getAllPostsAndComments() {
        List<Post> allPosts = postService.getAllPosts();
        Map<Post, List<Comment>> postCommentMap = new HashMap<>();

        for (Post post : allPosts) {
            System.out.println("Searching comments for post ID: " + post.getId());
            List<Comment> comments = commentService.getCommentsByPostId(post.getId());
            postCommentMap.put(post, comments);
        }

        return ResponseEntity.ok(postCommentMap);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String result = userService.login(email, password);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        String result = userService.signup(email, name, password);
        return ResponseEntity.ok(result);
    }
}*/
@RestController
//@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostController postController;

    @GetMapping("/user")
     public ResponseEntity<?> getUserDetails(@RequestParam Long userID) {
        //Long userID = request.get("userID");
        com.example.testtwitter.Entity.User user = userService.getUserDetails(userID);
        if (user != null) {
            Map<String, Object> userDetails = new LinkedHashMap<>();
            userDetails.put("name", user.getName());
            userDetails.put("userID", user.getId());
            userDetails.put("email", user.getEmail());

           return ResponseEntity.ok(userDetails);
            //return ResponseEntity.ok(user);
        } else {

            return convertToJsonResponse(HttpStatus.NOT_FOUND,"User does not exist");
        }
    }

    @GetMapping("/user/{userID}/feed")
    public ResponseEntity<?> getUserFeed(@PathVariable Long userID) {
        List<Post> userFeed = userService.getUserFeed(userID);
        return ResponseEntity.ok(userFeed);
    }


  @GetMapping("/")
  public ResponseEntity<?> getUserFeed() {

      List<Post> allPosts = postService.getAllPosts();

      List<Map<String, Object>> responsePosts = new ArrayList<>();


      for (int i = allPosts.size() - 1; i >= 0; i--) {
          Post post = allPosts.get(i);
            long postid = post.getId();

          ResponseEntity<?> postResponseEntity = postController.getPost(postid);


          Map<String, Object> postMap = getBody(postResponseEntity);
          responsePosts.add(postMap);
      }

      Map<String, Object> response = new HashMap<>();
      //response.put("", responsePosts);

      return ResponseEntity.ok(responsePosts);
  }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String result = userService.login(email, password);
         return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String name = request.get("name");
        String password = request.get("password");
        String result = userService.signup(email, name, password);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<Map<String, Object>> userDetailsList = new ArrayList<>();

        for (User user : allUsers) {
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("name", user.getName());
            userDetails.put("userID", user.getId());
            userDetails.put("email", user.getEmail());
            userDetailsList.add(userDetails);
        }

        return ResponseEntity.ok(userDetailsList);
    }
}


