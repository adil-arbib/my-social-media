package com.arbib.my_social_media.listeners;

import com.arbib.my_social_media.model.Like;
import com.arbib.my_social_media.model.Post;
import com.arbib.my_social_media.model.User;

public interface OnPostItemsListener {


    void onUserClick(User user);

    void onLikeClick(Like like);

    void onCommentsClick(Post post);

}
