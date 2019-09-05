package com.example.kmitcanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kmitcanteen.Common.Common;
import com.example.kmitcanteen.Interface.ItemClickListener;
import com.example.kmitcanteen.Model.Category;
import com.example.kmitcanteen.Model.Food;
import com.example.kmitcanteen.ViewHolder.FoodViewHolder;
import com.example.kmitcanteen.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity      {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    FloatingActionButton fab;

    String categoryId = "";
   FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);



        //Firebase Init
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Opening cart...",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

       /* final ElegantNumberButton numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,numberButton.getNumber()+" Items Added To Cart",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });*/




        //Get Intent Here
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {

            loadListFood(categoryId);
        }

        }




    private void loadListFood(String categoryId) {
        FirebaseRecyclerOptions<Food> options;
        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(foodList.orderByChild("MenuId").equalTo(categoryId),Food.class).build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i, @NonNull Food model) {
                foodViewHolder.food_name.setText(model.getName());
                foodViewHolder.food_price.setText(model.getPrice());
                Log.d("TAG", "" + adapter.getItemCount());
                Picasso.get().load(model.getImage())
                        .into(foodViewHolder.food_image);

                final Food local = model;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClik) {
                        Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();

                        //Start New Activity
                        /*Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey()); //Send food Id to new activity
                        startActivity(foodDetail);*/
                    }
                });

            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.food_item, parent, false);

                return new FoodViewHolder(itemView);
            }



        };


        adapter.startListening();
        // Set Adapter
        Log.d("TAG"   , ""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }




    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
 class viewer extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_item);

    }

}
