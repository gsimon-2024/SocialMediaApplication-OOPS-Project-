package com.example.testtwitter.DTO;

public class EditPostRequest {
    private Long postID;
    private String postBody;

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postId) {
        this.postID = postId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}