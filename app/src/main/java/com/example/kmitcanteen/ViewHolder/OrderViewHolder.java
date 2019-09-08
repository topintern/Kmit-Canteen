package com.example.kmitcanteen.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kmitcanteen.Interface.ItemClickListener;
import com.example.kmitcanteen.R;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderRollno, txtOrderReady;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderReady = itemView.findViewById(R.id.order_ready);
        txtOrderId = itemView.findViewById(R.id.order_name);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderRollno = itemView.findViewById(R.id.order_rollno);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)  {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}