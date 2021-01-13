package com.example.bankingapp.JavaClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.UserProfile;

import java.util.ArrayList;
import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;

public class  CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<String> userNames,userEmails,userBalance;

    public CustomAdapter(Context context,ArrayList userNames, ArrayList userEmails, ArrayList userBalance) {
        this.context = context;
        this.userNames = userNames;
        this.userEmails = userEmails;
        this.userBalance = userBalance;
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
        holder.userblance.setText("\u20B9"+String.valueOf(userBalance.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UserProfile.class);
                intent.putExtra("username",userNames.get(position));
                intent.putExtra("userbalance",userBalance.get(position));
                intent.putExtra("useremail",userEmails.get(position));
                context.startActivity(intent);
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
