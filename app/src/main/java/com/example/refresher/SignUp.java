package com.example.refresher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    TextInputLayout fullName, userName, email, phoneNumber, password;
    Button registerButton, loginButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removes the bottom bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Input fields
        fullName = findViewById(R.id.regFullname);
        userName = findViewById(R.id.regUsername);
        email = findViewById(R.id.regEmail);
        phoneNumber = findViewById(R.id.regPhone);
        password = findViewById(R.id.regPassword);


        //Buttons
        registerButton = findViewById(R.id.regButton);
        loginButton = findViewById(R.id.regSignIn);
        //regSignInButton = findViewById(R.id.regSignIn);


    }

    private Boolean validateName(){
        String val = Objects.requireNonNull(fullName.getEditText()).getText().toString();

        if(val.isEmpty()){
            fullName.setError("Field cannot be empty");
            return false;
        }else{
            fullName.setError(null);
            return true;
        }
    }

    private Boolean validateUserName() {
        String val = Objects.requireNonNull(userName.getEditText()).getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(noWhiteSpace)){
            userName.setError("Whites spaces are not allowed");
            return false;
        } else{
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = Objects.requireNonNull(email.getEditText()).getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            email.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            email.setError("Invalid email Address");
            return false;
        } else{
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePhone(){
        String val = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString();

        if(val.isEmpty()){
            phoneNumber.setError("Field cannot be empty");
            return false;
        }else{
            phoneNumber.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = Objects.requireNonNull(password.getEditText()).getText().toString();
        //String passwordPattern = "^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])"+ "(?=\\s+$)" + ".{4,}" + "$";

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }  else{
            password.setError(null);
            return true;
        }
    }

    public void registerUser(View view){
        if(!validateName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassword()){
            return;
        }

        String fname = Objects.requireNonNull(fullName.getEditText()).getText().toString();
        String uname = Objects.requireNonNull(userName.getEditText()).getText().toString();
        String Remail = Objects.requireNonNull(email.getEditText()).getText().toString();
        String pnumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString();
        String pass = Objects.requireNonNull(password.getEditText()).getText().toString();

        UserHelperClass helperClass = new UserHelperClass(fname,uname,Remail,pnumber,pass);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        reference.child(uname).setValue(helperClass);
        Toast.makeText(SignUp.this, "Insert Successful, Go back to the Login Page", Toast.LENGTH_SHORT).show();

        fullName.getEditText().setText("");
        userName.getEditText().setText("");
        email.getEditText().setText("");
        phoneNumber.getEditText().setText("");
        password.getEditText().setText("");

    }

    public void goBack(View view){
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();
    }

}