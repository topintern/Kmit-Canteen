package com.example.kmitcanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kmitcanteen.Database.Database;
import com.example.kmitcanteen.Model.Food;
import com.example.kmitcanteen.Model.Order;
import com.example.kmitcanteen.ViewHolder.CartAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import info.hoang8f.widget.FButton;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    public ImageButton btnCart;
    ElegantNumberButton numberButton;
    private List<Order> listData = new ArrayList<>();
    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        // Init view
        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);

         numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
             @Override
             public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {



             }
         });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice()


                ));


                Toast.makeText(FoodDetail.this,numberButton.getNumber()+" "+currentFood.getName()+" Added to Cart" , Toast.LENGTH_LONG).show();
            }
        });

        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        //Get Food Id From Internet
        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()){
                getDetailFood(foodId);

        }

    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);

                //Set Image
                Picasso.get().load(currentFood.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());

                food_name.setText(currentFood.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
      finish();
    }
}











