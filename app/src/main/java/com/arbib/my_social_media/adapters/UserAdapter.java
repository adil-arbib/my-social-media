package com.arbib.my_social_media.adapters;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.listeners.OnUserClickListener;
import com.arbib.my_social_media.model.User;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> users;
    private final OnUserClickListener userClickListener;

    public UserAdapter(ArrayList<User> users, OnUserClickListener userClickListener) {
        this.users = users;
        this.userClickListener = userClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<User> users) {
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view, userClickListener, users);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getFirstName()+" "+user.getLastName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView username;
        Button btn;
        public ViewHolder(@NonNull View itemView, final OnUserClickListener listener, final ArrayList<User> users) {
            super(itemView);
            img = itemView.findViewById(R.id.img_profile);
            username = itemView.findViewById(R.id.txt_username);
            btn = itemView.findViewById(R.id.btnFollow);
            btn.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    User user = users.get(pos);
                    if(user.isFollowing()) {
                        btn.setBackgroundColor(btn.getContext().getResources().getColor(R.color.secondary));
                        btn.setText("Follow");
                        btn.setTextColor(btn.getContext().getResources().getColor(R.color.white));
                    }else {
                        btn.setBackgroundColor(btn.getContext().getResources().getColor(R.color.medium_grey));
                        btn.setText("Following");
                    }
                    user.setFollowing(!user.isFollowing());
                    listener.onFollowClick(user, pos);
                }

            });
        }
    }
}
