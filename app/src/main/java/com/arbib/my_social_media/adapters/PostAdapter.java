package com.arbib.my_social_media.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.listeners.OnPostItemsListener;
import com.arbib.my_social_media.model.Post;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private OnPostItemsListener postItemsListener;
    private Context context;

    public PostAdapter(ArrayList<Post> posts, OnPostItemsListener postItemsListener, Context context) {
        this.posts = posts;
        this.postItemsListener = postItemsListener;
        this.context = context;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false),
                postItemsListener, context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.txtUsername.setText(post.getUser().getFirstName() + " " + post.getUser().getLastName());
//        if (post.getImage() == null) holder.postPic.setVisibility(View.GONE);
//        else holder.postPic.setImageBitmap(null); // TODO: convert string to bitmap
        holder.txtCommentsCount.setText("20 comments");
        holder.txtContent.setText(post.getContent());
        holder.txtDate.setText(post.getCreate_at());
        holder.txtLikesCount.setText("14 likes");
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername, txtDate, txtContent,
                txtLikesCount, txtCommentsCount,
                txtReactType;
        LinearLayout viewLikes, viewComments;
        ImageView imgUserPic, postPic, imgReact;

        @SuppressLint("ClickableViewAccessibility")
        public ViewHolder(@NonNull View itemView, final OnPostItemsListener postItemsListener, Context context) {
            super(itemView);
            inflate();


            viewLikes.setOnLongClickListener(v -> {
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                showReactDialog(location[0],location[1]);
                return true;
            });


        }


        private void inflate() {
            txtUsername = itemView.findViewById(R.id.post_username);
            txtDate = itemView.findViewById(R.id.post_date);
            txtContent = itemView.findViewById(R.id.post_content);
            txtLikesCount = itemView.findViewById(R.id.post_likes);
            txtCommentsCount = itemView.findViewById(R.id.post_comments_count);
            txtReactType = itemView.findViewById(R.id.post_reacted_type);
            viewLikes = itemView.findViewById(R.id.view_like);
            viewComments = itemView.findViewById(R.id.view_comment);
            imgUserPic = itemView.findViewById(R.id.post_user_pec);
            postPic = itemView.findViewById(R.id.post_pec);
            imgReact = itemView.findViewById(R.id.post_react);
            imgReact.setBackgroundResource(R.drawable.react_love_grey);
        }

        private void showReactDialog(int x, int y) {
            Dialog dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.dialog_reacts);
            Window window = dialog.getWindow();
            window.getAttributes().flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.getAttributes().gravity = Gravity.TOP | Gravity.LEFT;
            window.getAttributes().x = x;
            window.getAttributes().y = y - 330;
            dialog.show();

            ImageView dg_love, dg_haha, dg_wow, dg_sad, dg_angry;

            dg_love = dialog.findViewById(R.id.dg_love);
            dg_haha = dialog.findViewById(R.id.dg_haha);
            dg_wow = dialog.findViewById(R.id.dg_wow);
            dg_sad = dialog.findViewById(R.id.dg_sad);
            dg_angry = dialog.findViewById(R.id.dg_angry);

            dg_love.setOnClickListener(v -> {
                imgReact.setBackgroundResource(R.drawable.love);
                txtReactType.setText("Love");
                txtReactType.setTextColor(Color.parseColor("#E50606"));
                dialog.cancel();
            });

            dg_haha.setOnClickListener(v -> {
                imgReact.setBackgroundResource(R.drawable.haha);
                txtReactType.setText("Haha");
                txtReactType.setTextColor(Color.parseColor("#F7A135"));
                dialog.cancel();
            });

            dg_wow.setOnClickListener(v -> {
                imgReact.setBackgroundResource(R.drawable.wow);
                txtReactType.setText("Wow");
                txtReactType.setTextColor(Color.parseColor("#F7A135"));
                dialog.cancel();
            });

            dg_sad.setOnClickListener(v -> {
                imgReact.setBackgroundResource(R.drawable.sad);
                txtReactType.setText("Sad");
                txtReactType.setTextColor(Color.parseColor("#F7A135"));
                dialog.cancel();
            });

            dg_angry.setOnClickListener(v -> {
                imgReact.setBackgroundResource(R.drawable.angry);
                txtReactType.setText("Angry");
                txtReactType.setTextColor(Color.parseColor("#FF7503"));
                dialog.cancel();
            });
        }
    }



}
