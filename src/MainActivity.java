package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin,btnSignUp;
    private EditText editUsername, editPassword;
    private Spinner spinner;
    public String password;

    UserDatabase userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        spinner = (Spinner) findViewById(R.id.spinner);

        userDB = new UserDatabase(this);

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertype, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = editUsername.getText().toString();
                String p = editPassword.getText().toString();
                String item = spinner.getSelectedItem().toString();
                if(editUsername.getText().toString().equals("admin") && editPassword.getText().toString().equals("admin") && item.equals("admin") ){
                    Intent intent =  new Intent(MainActivity.this, admin.class);
                    startActivity(intent);
                }
                else if (userDB.checkUser(n,p) && item.equals("user")){
                    Intent intent =  new Intent(MainActivity.this, user.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });



    }

}