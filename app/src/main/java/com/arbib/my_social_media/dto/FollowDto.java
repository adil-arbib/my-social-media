package com.arbib.my_social_media.dto;

public class FollowDto {
    private Long personID;
    private Long followerID;

    public FollowDto(Long personID, Long followerID) {
        this.personID = personID;
        this.followerID = followerID;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public Long getFollowerID() {
        return followerID;
    }

    public void setFollowerID(Long followerID) {
        this.followerID = followerID;
    }
}
