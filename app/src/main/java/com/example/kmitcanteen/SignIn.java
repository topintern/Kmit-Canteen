package com.example.kmitcanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kmitcanteen.Common.Common;
import com.example.kmitcanteen.Model.Rollno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText edtroll,edtpass;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtpass=(MaterialEditText)findViewById(R.id.edtpass);
        edtroll=(MaterialEditText)findViewById(R.id.edtroll);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_roll=database.getReference("Rollno");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();
                table_roll.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get user
                        if(dataSnapshot.child(edtroll.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Rollno rollno = dataSnapshot.child(edtroll.getText().toString()).getValue(Rollno.class);
                            //rollno.setRollno(edtroll.getText().toString());
                            if (rollno.getPassword().equals(edtpass.getText().toString())) {
                                //Toast.makeText(SignIn.this, "sign in successfully !", Toast.LENGTH_SHORT).show();
                                Intent homeIntent= new Intent(SignIn.this,Home.class);
                                Common.currentuser=rollno;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "sign in failed !", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this,"User does not exist",Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
