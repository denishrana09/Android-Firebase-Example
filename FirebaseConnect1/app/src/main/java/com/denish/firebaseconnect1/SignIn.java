package com.denish.firebaseconnect1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    Button SignIn;
    EditText pass,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignIn = findViewById(R.id.btnSignIn);
        phone = findViewById(R.id.edtPhone);
        pass = findViewById(R.id.edtPassword);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait");
                mDialog.show();



                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDialog.dismiss();
                        if(dataSnapshot.child(phone.getText().toString()).exists() && phone.getText().toString().length()!=0) {
                            User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(pass.getText().toString())) {
                                Toast.makeText(SignIn.this, "Sign In Successfully , ", Toast.LENGTH_SHORT).show();
                                //finish();
                            }
                            else {
                                Toast.makeText(SignIn.this, "Sign In Unsuccessfull", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User Not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
