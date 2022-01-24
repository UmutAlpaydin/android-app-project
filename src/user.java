package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class user extends AppCompatActivity {
    private ListView listViewCars,listViewShortlist;
    private Button btnShortlist;
    CarDatabase carDB;
    private TabHost tabHost;
    private TextView text;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        carDB = new CarDatabase(this);
        scrollView = findViewById(R.id.scroll);

        text = findViewById(R.id.text);

        listViewCars = findViewById(R.id.listViewCars);
        btnShortlist = findViewById(R.id.btnShortlist);
        listViewShortlist = findViewById(R.id.listViewShortlist);

        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();


        // add the first tab
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("Screen-1");
        tabSpec.setContent(R.id.user1);
        tabSpec.setIndicator("SHOWROOM",null);
        tabHost.addTab(tabSpec); // finished add the first tab


        // add the second tab
        tabSpec = tabHost.newTabSpec("Screen-2");
        tabSpec.setContent(R.id.user2);
        tabSpec.setIndicator("SHORTLIST", null);
        tabHost.addTab(tabSpec); // finished add the second tab


        tabHost.setCurrentTab(0);





        Cursor cursor = carDB.getAllCars();
        ArrayList<String> cars = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_multiple_choice, cars);
        //iterate record
        while (cursor.moveToNext()) {
            String brand = cursor.getString(cursor.getColumnIndex("brand"));//acces id
            String model = cursor.getString(cursor.getColumnIndex("model"));//acces name
            String year= cursor.getString(cursor.getColumnIndex("year"));
            String color= cursor.getString(cursor.getColumnIndex("color"));
            String type= cursor.getString(cursor.getColumnIndex("type"));
            String result = brand + " " + model + " " + year+ " " +color + " " + type;
            cars.add(result);}

        listViewCars.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewCars.setAdapter(adapter);


        btnShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<String> shortlist = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, shortlist);
                String[] userString;
                SparseBooleanArray sp = listViewCars.getCheckedItemPositions();
                for (int i = 0; i < listViewCars.getAdapter().getCount(); i++) {
                    if (sp.get(i)) {
                        String s = ((CheckedTextView) listViewCars.getChildAt(i)).getText().toString();
                        shortlist.add(s);
                    }


                }
                listViewShortlist.setAdapter(adapter);
            }



        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu); // call the menu_xml file

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        menuItems(item);
        return false;
    }
    private void menuItems(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menu_SignOut) {
            Intent intent = new Intent(user.this, MainActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_About) {
            Toast.makeText(getApplicationContext(),"This application developed by Umut Alpaydin & Gurkan Saki",Toast.LENGTH_SHORT).show();

        }
    }


}