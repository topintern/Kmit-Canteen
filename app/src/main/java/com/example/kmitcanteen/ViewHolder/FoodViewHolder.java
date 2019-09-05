package com.example.kmitcanteen.ViewHolder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kmitcanteen.Interface.ItemClickListener;
import com.example.kmitcanteen.R;
import com.google.android.material.snackbar.Snackbar;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView food_name;
        public ImageView food_image;
        public TextView food_price;
        private ItemClickListener itemClickListener;


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public FoodViewHolder(View itemView) {
            super(itemView);

            food_name = itemView.findViewById(R.id.food_name);
            food_image = itemView.findViewById(R.id.food_image);
            food_price=itemView.findViewById(R.id.food_price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }


