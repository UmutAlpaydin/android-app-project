package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText userName,userLastname,userEmail,userPassword;
    UserDatabase userDB;
    private Button CreateUser,BackToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.userName);
        userLastname = findViewById(R.id.userLastname);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        CreateUser = findViewById(R.id.CreateUser);
        BackToLogin = findViewById(R.id.BackToLogin);


        userDB = new UserDatabase(this);

        CreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String lastname = userLastname.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if(password.length()>=8) {
                    userDB.addUser(name, lastname,email,password);
                }
                else{
                    Toast.makeText(SignUp.this, "Password need to be at least 8 character", Toast.LENGTH_SHORT).show();
                }





                userName.setText("");
                userLastname.setText("");
                userEmail.setText("");
                userPassword.setText("");

                userDB.close();
            }
        });

        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}