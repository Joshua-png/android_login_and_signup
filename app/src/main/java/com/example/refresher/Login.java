package com.example.refresher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    Button callSignUp, signIn;
    TextInputLayout username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removes the bottom bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.callSignUp);
        signIn = findViewById(R.id.login_signIn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validatePassword(){
        String val = Objects.requireNonNull(password.getEditText()).getText().toString();

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }  else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserName() {
        String val = Objects.requireNonNull(username.getEditText()).getText().toString();


        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if(!validateUserName() | !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    private void isUser() {
        String luname = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
        String lpass = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("userName").equalTo(luname);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(luname).child("password").getValue(String.class);

                    if(passwordFromDB.equals(lpass)){
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String usernameFromDB = snapshot.child(luname).child("userName").getValue(String.class);
                        //String emailFromDB = snapshot.child(luname).child("email").getValue(String.class);

                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                        intent.putExtra("username", usernameFromDB);
                        //intent.putExtra("email",emailFromDB);

                        startActivity(intent);

                    }else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else{
                    username.setError("Username doesnot Exit");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}