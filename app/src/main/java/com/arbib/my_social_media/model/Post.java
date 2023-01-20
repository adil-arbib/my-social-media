package com.arbib.my_social_media.model;

public class Post {

    private Long id;
    private String content;
    private String image;
    private String create_at;
    private String update_At;
    private User user;

    public Post(Long id, String content,
                String image, String create_at,
                String update_At, User user) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.create_at = create_at;
        this.update_At = update_At;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(String update_At) {
        this.update_At = update_At;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", create_at='" + create_at + '\'' +
                ", update_At='" + update_At + '\'' +
                ", user=" + user +
                '}';
    }
}
