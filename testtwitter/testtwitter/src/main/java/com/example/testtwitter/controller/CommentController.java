/*package com.example.testtwitter.controller;

import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<String> createComment(@RequestParam String commentBody, @RequestParam Long postId) {
        String result = commentService.createComment(commentBody, postId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCommentDetails(@RequestBody Long commentId) {
        Comment comment = commentService.getCommentDetails(commentId);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

    @PatchMapping("/")
    public ResponseEntity<String> editComment(@RequestParam Long commentId, @RequestParam String commentBody) {
        String result = commentService.editComment(commentId, commentBody);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) {
        String result = commentService.deleteComment(commentId);
        return ResponseEntity.ok(result);
    }
}
*/
package com.example.testtwitter.controller;

import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Repo.UserRepository;
import com.example.testtwitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.testtwitter.JsonConverter.convertToJsonResponse;

@RestController

public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest request) {
        String result = commentService.createComment(request.getCommentBody(), request.getPostID(), request.getUserID());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comment")
    //public ResponseEntity<?> getCommentDetails(@RequestBody GetCommentRequest request) {
    public ResponseEntity<?> getCommentDetails(@RequestParam Long CommentID) {

        Comment comment = commentService.getCommentDetails(CommentID);

        if (comment != null) {
            Map<String, Object> commentMap = new LinkedHashMap<>();
            commentMap.put("commentID", comment.getId());
            commentMap.put("commentBody", comment.getCommentBody());

            // Construct comment creator object
            Map<String, Object> commentCreator = new LinkedHashMap<>();
            commentCreator.put("userID", comment.getUserID());
            User user = userRepository.findById(comment.getUserID()).orElse(null);
            commentCreator.put("name", user.getName());
            commentMap.put("commentCreator", commentCreator);

            return ResponseEntity.ok(commentMap);
        } else {

            return convertToJsonResponse(HttpStatus.NOT_FOUND,"Comment does not exist");
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<String> editComment(@RequestBody EditCommentRequest request) {
        String result = commentService.editComment(request.getCommentID(), request.getCommentBody());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/comment")
      public ResponseEntity<String> deleteComment(@RequestParam Long commentID) {
          String result = commentService.deleteComment(commentID);
        return ResponseEntity.ok(result);
    }

    // Inner classes for request bodies
    public static class CreateCommentRequest {
        private String commentBody;
        private Long postID;
        private Long userID;
        // Getters and setters
        public String getCommentBody() {
            return commentBody;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public Long getPostID() {
            return postID;
        }

        public void setPostId(Long postId) {
            this.postID = postId;
        }
        public Long getUserID() {
            return userID;
        }

        public void setUserID(Long userID) {
            this.userID = userID;
        }
    }

    public static class GetCommentRequest {
        private Long commentID;

        // Getter and setter
        public Long getCommentID() {
            return commentID;
        }

        public void setCommentID(Long commentId) {
            this.commentID = commentId;
        }
    }

    public static class EditCommentRequest {
        private Long commentID;
        private String commentBody;

        // Getters and setters
        public Long getCommentID() {
            return commentID;
        }

        public void setCommentID(Long commentId) {
            this.commentID = commentId;
        }

        public String getCommentBody() {
            return commentBody;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }
    }

    public static class DeleteCommentRequest {
        private Long commentID;

        // Getter and setter
        public Long getCommentID() {
            return commentID;
        }

        public void setCommentID(Long commentId) {
            this.commentID = commentId;
        }
    }
}
