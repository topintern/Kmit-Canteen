package com.example.kmitcanteen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kmitcanteen.Common.Common;
import com.example.kmitcanteen.Common.Config;
import com.example.kmitcanteen.Database.Database;
import com.example.kmitcanteen.Model.Order;
import com.example.kmitcanteen.Model.Request;
import com.example.kmitcanteen.ViewHolder.CartAdapter;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE =9999 ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

   public TextView txtTotalPrice;


  FButton btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;
static PayPalConfiguration config =new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(Config.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        /*Intent intent= new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);*/

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.size() > 0)
                    showAlertDialog();
               else
                    Toast.makeText(Cart.this, "Your cart is empty!!", Toast.LENGTH_SHORT).show();

            }
        });

        loadListFood();
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        // Kalkulasi total harga
        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }
    private void deleteCart(int position)
    {
        cart.remove(position);
        new Database(this).cleanCart();
        for (Order item:cart)
            new Database(this).addToCart(item);
        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Confirm Order?");
        alertDialog.setMessage("No Return Policy!");



        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Create new Request
                Request request=new Request(Common.currentuser.getRollno(),
                        Common.currentuser.getName(),
                        txtTotalPrice.getText().toString(),
                        cart);

                // Submit ke Firebase
                // We Will using System.CurrentMilli to Key
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //Delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Your Order has been Placed!", Toast.LENGTH_SHORT).show();
                finish();


                /* String formatAmount =txtTotalPrice.getText().toString().trim().replace("$","")
                        .replace(",","");
                float amount= Float.parseFloat(formatAmount);
                PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(formatAmount),"USD","Kmit Canteen app order"
                        ,PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent=new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent,PAYPAL_REQUEST_CODE);*/

                /*


                */
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==PAYPAL_REQUEST_CODE)
        {
            if (resultCode==RESULT_OK)
            {
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation!=null)
                {
                    try {
                        String paymentDetail=confirmation.toJSONObject().toString(4);
                        JSONObject jsonObject=new JSONObject(paymentDetail);

                        Request request=new Request(Common.currentuser.getRollno(),
                                Common.currentuser.getName(),
                                txtTotalPrice.getText().toString(),
                                "0",
                                jsonObject.getJSONObject("response").getString("state"),
                                cart);

                        // Submit ke Firebase
                        // We Will using System.CurrentMilli to Key
                        requests.child(String.valueOf(System.currentTimeMillis()))
                                .setValue(request);
                        //Delete cart
                        new Database(getBaseContext()).cleanCart();
                        Toast.makeText(Cart.this, "Your Order has been Placed!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
            else if (resultCode== Activity.RESULT_CANCELED)
            Toast.makeText(this,"Payment cancelled!",Toast.LENGTH_SHORT).show();
             else if (resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this,"Invalid Payment",Toast.LENGTH_SHORT).show();
        }
    } */
}
