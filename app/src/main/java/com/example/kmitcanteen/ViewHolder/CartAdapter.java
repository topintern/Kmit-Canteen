package com.example.kmitcanteen.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kmitcanteen.Cart;
import com.example.kmitcanteen.Common.Common;
import com.example.kmitcanteen.Database.Database;
import com.example.kmitcanteen.Interface.ItemClickListener;
import com.example.kmitcanteen.Model.Order;
import com.example.kmitcanteen.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class  CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{

    public TextView txt_cart_name, txt_price;
    public ElegantNumberButton numberButton;

    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        numberButton = itemView.findViewById(R.id.number_button);
           itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("select action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Cart cart;

    public CartAdapter(List<Order> listData, Cart cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cart);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {
       // TextDrawable drawable = TextDrawable.builder()
         //       .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        //holder.img_cart_count.setImageDrawable(drawable);
      holder.numberButton.setNumber(listData.get(position).getQuantity());
       holder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
           @Override
           public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
               Order order=listData.get(position);
               order.setQuantity(String.valueOf(newValue));
               new Database(cart).updateCart(order);

               List<Order> orders=new Database(cart).getCarts();
               int total = 0;
               for(Order item:orders)
                   total+=(Integer.parseInt(item.getPrice())) * (Integer.parseInt(item.getQuantity()));
               Locale locale = new Locale("en","US");
               NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

               cart.txtTotalPrice.setText(fmt.format(total));

           }
       });

        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice())) * (Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
