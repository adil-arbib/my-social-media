package com.arbib.my_social_media.listeners;

import com.arbib.my_social_media.model.User;

public interface OnUserClickListener{

    void onUserClick(User user);

    void onFollowClick(User user, int pos);

}
