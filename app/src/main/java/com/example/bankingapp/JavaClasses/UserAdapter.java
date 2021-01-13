package com.example.bankingapp.JavaClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.MainActivity;
import com.example.bankingapp.R;
import com.example.bankingapp.SelectReceiver;
import com.example.bankingapp.UserProfile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> userNames,userEmails;

    public UserAdapter(Context context, ArrayList userNames, ArrayList userEmails) {
        this.context = context;
        this.userNames = userNames;
        this.userEmails = userEmails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(String.valueOf(userNames.get(position)));
        holder.useremail.setText(String.valueOf(userEmails.get(position)));
        holder.userblance.setText("");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("receiver",userNames.get(position));
                ((Activity)context).setResult(Activity.RESULT_OK,intent);
                ((Activity)context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return userNames.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView username,useremail,userblance;
        CircleImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.profile);
            username=itemView.findViewById(R.id.userName);
            useremail=itemView.findViewById(R.id.userEmail);
            userblance=itemView.findViewById(R.id.userBalance);
        }
    }

}
