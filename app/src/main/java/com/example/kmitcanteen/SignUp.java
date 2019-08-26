package com.example.kmitcanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kmitcanteen.Model.Rollno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

     MaterialEditText edtrollno,edtname,edtpass;
     Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtname=(MaterialEditText)findViewById(R.id.edtname);
        edtpass=(MaterialEditText)findViewById(R.id.edtpass);
        edtrollno=(MaterialEditText)findViewById(R.id.edtroll);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_roll=database.getReference("Rollno");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_roll.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtrollno.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Roll no. already registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            Rollno rollno=new Rollno(edtname.getText().toString(),edtpass.getText().toString());
                            table_roll.child(edtrollno.getText().toString()).setValue(rollno);
                            Toast.makeText(SignUp.this, "SignUp successful !", Toast.LENGTH_SHORT).show();
                            finish();
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
