package com.example.kmitcanteen.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kmitcanteen.Database.Database;
import com.example.kmitcanteen.FoodDetail;
import com.example.kmitcanteen.FoodList;
import com.example.kmitcanteen.Home;
import com.example.kmitcanteen.Interface.ItemClickListener;
import com.example.kmitcanteen.Model.Food;
import com.example.kmitcanteen.Model.Order;
import com.example.kmitcanteen.R;
import com.example.kmitcanteen.SignIn;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.hoang8f.widget.FButton;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image;
    public TextView food_price;
    private ItemClickListener itemClickListener;
    public ImageButton button;




    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name);
        food_image = itemView.findViewById(R.id.food_image);
        food_price = itemView.findViewById(R.id.food_price);
        button = itemView.findViewById(R.id.add_btn);

        //((Activity)itemView.getContext()).recreate();
       //Intent intent=new




      









        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}





