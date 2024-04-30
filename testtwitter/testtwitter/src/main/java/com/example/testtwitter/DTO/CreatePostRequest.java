package com.example.testtwitter.DTO;


public class CreatePostRequest {
    private String postBody;
    private Long userID;

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserId(Long userId) {
        this.userID = userId;
    }

    public static class EditPostRequest {
    }
}
