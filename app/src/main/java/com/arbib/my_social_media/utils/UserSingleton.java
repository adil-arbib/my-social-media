package com.arbib.my_social_media.utils;

import com.arbib.my_social_media.model.User;

public final class UserSingleton {

    private static User user;

    public static void setUser(User u) {
        user = u;
    }

    public static User getUser() {
        return user;
    }


}
