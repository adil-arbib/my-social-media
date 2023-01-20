package com.arbib.my_social_media.dto;

public class PostDto {
    private Long id;
    private String content;
    private Long user_id;


    public PostDto(Long id, String content, Long user_id) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
    }

    public PostDto(String content, Long user_id) {
        this.content = content;
        this.user_id = user_id;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
