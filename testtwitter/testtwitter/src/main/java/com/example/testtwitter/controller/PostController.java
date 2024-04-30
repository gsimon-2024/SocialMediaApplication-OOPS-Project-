/*package com.example.testtwitter.controller;

import com.example.testtwitter.DTO.PostResponse;
import com.example.testtwitter.Entity.Post;
import com.example.testtwitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/")
    public ResponseEntity<String> createPost(@RequestParam String postBody, @RequestParam Long userId) {
        String result = postService.createPost(postBody, userId);
        return ResponseEntity.ok(result);
    }

   /* @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }*/
  /* @GetMapping("/")
   public ResponseEntity<PostResponse> getAllPosts() {
       List<Post> allPosts = postService.getAllPosts();
       PostResponse response = new PostResponse(allPosts);
       if (allPosts.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
       } else {
           return ResponseEntity.ok(response);
       }
   }


    @GetMapping("/getPostWithComments")
    public ResponseEntity<?> getPostWithComments(@RequestBody Long postId) {
        Post post = (Post) postService.getAllPostsAndComments(postId);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

    @PatchMapping("/")
    public ResponseEntity<String> editPost(@RequestParam Long postId, @RequestParam String postBody) {
        String result = postService.editPost(postId, postBody);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deletePost(@RequestParam Long postId) {
        String result = postService.deletePost(postId);
        return ResponseEntity.ok(result);
    }
}*/
package com.example.testtwitter.controller;

import com.example.testtwitter.DTO.CreatePostRequest;
import com.example.testtwitter.DTO.EditPostRequest;
import com.example.testtwitter.DTO.PostResponse;
import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.Post;
import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Repo.UserRepository;
import com.example.testtwitter.service.CommentService;
import com.example.testtwitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.testtwitter.JsonConverter.convertToJsonResponse;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest request) {
        String result = postService.createPost(request.getPostBody(), request.getUserID());
        return ResponseEntity.ok(result);
    }


    @GetMapping("/post/")
    public ResponseEntity<PostResponse> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        PostResponse response = new PostResponse(allPosts);
        if (allPosts.isEmpty()) {
          //  return convertToJsonResponse(HttpStatus.NOT_FOUND,"Post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/post")
        public ResponseEntity<?> getPost(@RequestParam Long postID) {
              Post post = postService.getPost(postID);
        if (post != null) {
            List<Comment> comments = commentService.getCommentsByPostId(postID);
            post.setComments(comments);

            // Construct post map
            Map<String, Object> postMap = new LinkedHashMap<>();
            postMap.put("postID", post.getId());
            postMap.put("postBody", post.getPostBody());
            if (post.getPostBody() == null || post.getPostBody().isEmpty()) {

                return convertToJsonResponse(HttpStatus.NOT_FOUND,"Post does not exist");
            }
            postMap.put("date", post.getDate());

            // Construct comments list
            List<Map<String, Object>> postComments = new ArrayList<>();
            for (Comment comment : comments) {
                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("commentID", comment.getId());
                commentMap.put("commentBody", comment.getCommentBody());

                // Construct comment creator object
                Map<String, Object> commentCreator = new LinkedHashMap<>();

                User user = userRepository.findById(comment.getUserID()).orElse(null);
                if (user != null) {
                    commentCreator.put("userID", comment.getUserID());
                    commentCreator.put("name", user.getName());

                }
                else{
                    commentCreator.put("userID", post.getUser().getId());
                    commentCreator.put("name", post.getUser().getName());
                }
                commentMap.put("commentCreator", commentCreator);

                postComments.add(commentMap);
            }

            postMap.put("comments", postComments);

            // Construct final response map
           Map<String, Object> response = new LinkedHashMap<>();
           response.put("post", postMap);
//return ResponseEntity.ok(postMap);
            return ResponseEntity.ok(response);
        } else {

            return convertToJsonResponse(HttpStatus.NOT_FOUND,"Post does not exist");

        }
    }

    @PatchMapping("/post")
    public ResponseEntity<String> editPost(@RequestBody EditPostRequest request) {
        String result = postService.editPost(request.getPostID(), request.getPostBody());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/post")
       public ResponseEntity<String> deletePost(@RequestParam Long postID) {
        // Long postID = requestBody.get("postID");
        if (postID == null) {
             return convertToJsonResponse(HttpStatus.NOT_FOUND,"Post does not exist");
        }
        String result = postService.deletePost(postID);
        return ResponseEntity.ok(result);
    }
}

