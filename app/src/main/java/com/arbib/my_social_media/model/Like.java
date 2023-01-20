package com.arbib.my_social_media.model;

public class Like {
    private Long id;
    private String type;
    private Post post;
    private User user;

    public Like(Long id, String type, Post post, User user) {
        this.id = id;
        this.type = type;
        this.post = post;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUs() {
        return user;
    }

    public void setUs(User us) {
        this.user = us;
    }
}
