package com.example.testtwitter.service;


import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.Post;


import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Repo.CommentRepository;
import com.example.testtwitter.Repo.PostRepository;
import com.example.testtwitter.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.testtwitter.JsonConverter.convertToJson;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public String createComment(String commentBody, Long postId,Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        if(user == null) {
            return convertToJson("User not found");
        }
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Comment comment = new Comment(commentBody, post,userID);
            commentRepository.save(comment);
            return "Comment created successfully";
        } else {
            return convertToJson("Post does not exist");
        }
    }

    public Comment getCommentDetails(Long commentId) {

        //return commentRepository.findById(commentId).orElse(null);
        return commentRepository.findCommentDetailsById(commentId).orElse(null);
    }

    public String editComment(Long commentId, String commentBody) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setCommentBody(commentBody);
            commentRepository.save(comment);
            return "Comment edited successfully";
        } else {
            return convertToJson("Comment does not exist");
        }
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    public String deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return "Comment deleted";
        } else {
            return convertToJson("Comment does not exist");
        }
    }
}
