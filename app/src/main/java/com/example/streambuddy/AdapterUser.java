package com.example.streambuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    Context context;
    FirebaseAuth firebaseAuth;
    String userID;

    public AdapterUser(Context context, List<UserModel> list){
        this.context = context;
        this.list = list;
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
    }

    List<UserModel> list;

    @NonNull
    @Override

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent, false);
        return new MyHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String anotherid = list.get(position).getUid();
        String userImage = list.get(position).getImage();
        String username = list.get(position).getName();
        String useremail = list.get(position).getEmail();
        holder.name.setText(username);
        holder.email.setText(useremail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        CircleImageView profiletv;
        TextView name, email;

        public MyHolder(View itemView){
            super(itemView);
            profiletv = itemView.findViewById(R.id.imagep);
            name = itemView.findViewById(R.id.namep);
            email = itemView.findViewById(R.id.emailp);
        }
    }
}