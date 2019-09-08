package com.example.kmitcanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kmitcanteen.Common.Common;
import com.example.kmitcanteen.Interface.ItemClickListener;
import com.example.kmitcanteen.Model.Food;
import com.example.kmitcanteen.Model.Request;
import com.example.kmitcanteen.ViewHolder.FoodViewHolder;
import com.example.kmitcanteen.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Init Firebase
        database  = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() == null)
            loadOrders(Common.currentuser.getRollno());
        else
            loadOrders(getIntent().getStringExtra("Rollno"));

        loadOrders(Common.currentuser.getRollno());


    }



    private void loadOrders(String rollno) {
        FirebaseRecyclerOptions<Request> options;
        options = new FirebaseRecyclerOptions.Builder<Request>() .setQuery(requests.orderByChild("rollno").equalTo(rollno),Request.class).build();
       adapter=new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position, @NonNull Request model) {
               viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
               viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
               viewHolder.txtOrderReady.setText(model.getReady());
               viewHolder.txtOrderRollno.setText(model.getRollno());
               Log.d("TAG", "" + adapter.getItemCount());
               viewHolder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClik) {

                   }
               });

           }

           @NonNull
           @Override
           public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View itemView = LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.order_layout, parent, false);

               return new OrderViewHolder(itemView);
           }
       };
        adapter.startListening();
        // Set Adapter
        Log.d("TAG"   , ""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);



    }
    private String convertCodeToStatus(String status)
    {
        if (status.equals("0"))
            return "Order Placed!";
        else if (status.equals("1"))
        {
            return "Please wait! your Food is being prepared!";

        }
        else
            return "Your food is Ready";
    }
}
