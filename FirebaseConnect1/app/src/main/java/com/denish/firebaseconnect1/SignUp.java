package com.denish.firebaseconnect1;

import android.app.ProgressDialog;
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

public class SignUp extends AppCompatActivity {

    EditText name,phone,pass;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.edtName1);
        pass = (EditText)findViewById(R.id.edtPassword1);
        phone = (EditText)findViewById(R.id.edtPhone1);
        signup = findViewById(R.id.btnSignUp1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(phone.getText().toString()).exists() && phone.getText().toString().length()!=0){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.dismiss();
                            User user = new User(name.getText().toString(),pass.getText().toString());
                            table_user.child(phone.getText().toString()).setValue(user);

                            Toast.makeText(SignUp.this,"Sign Up successfully", Toast.LENGTH_SHORT).show();

                            finish();
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
