package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

public class admin extends AppCompatActivity {
    private TabHost tabHost;
    CarDatabase carDB;
    UserDatabase userDB;
    private EditText editBrand,editModel,editYear,editColor,editType;
    private ListView listView,listViewUser;
    SQLiteDatabase database;
    CarDatabase car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        carDB =  new CarDatabase(this);
        userDB = new UserDatabase(this);


        editBrand = findViewById(R.id.editBrand);
        editModel = findViewById(R.id.editModel);
        editYear = findViewById(R.id.editYear);
        editColor = findViewById(R.id.editColor);
        editType = findViewById(R.id.editType);

        listView =  findViewById(R.id.listView);
        listViewUser =  findViewById(R.id.listViewUser);



        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        car = new CarDatabase(this);

        // add the first tab
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("Screen-1");
        tabSpec.setContent(R.id.admin1);
        tabSpec.setIndicator("ADD",null);
        tabHost.addTab(tabSpec); // finished add the first tab


        // add the second tab
        tabSpec = tabHost.newTabSpec("Screen-2");
        tabSpec.setContent(R.id.admin2);
        tabSpec.setIndicator("DISPLAY", null);
        tabHost.addTab(tabSpec); // finished add the second tab

        // add the third tab
        tabSpec = tabHost.newTabSpec("Screen-3");
        tabSpec.setContent(R.id.admin3);
        tabSpec.setIndicator("DELETE", null);
        tabHost.addTab(tabSpec); // finished adding the third tab


        tabHost.setCurrentTab(0);




        Cursor cursor = userDB.getAllUsers();
        ArrayList<String> users = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_multiple_choice, users);
        //iterate record
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));//acces id
            String lastname = cursor.getString(cursor.getColumnIndex("lastname"));//acces name
            String email= cursor.getString(cursor.getColumnIndex("email"));
            String password= cursor.getString(cursor.getColumnIndex("password"));
            String result = name + " " + lastname + " " + email+ " " +password;
            users.add(result);}

        listViewUser.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewUser.setAdapter(adapter);

    }





    public void InsertCars(View v){
        String brand = editBrand.getText().toString().trim();
        String model = editModel.getText().toString().trim();
        String year = editYear.getText().toString().trim();
        String color = editColor.getText().toString().trim();
        String type = editType.getText().toString().trim();

        carDB.addCars(brand,model,Integer.parseInt(year),color,type);

      /*  editBrand.setText("");
        editModel.setText("");
        editYear.setText("");
        editColor.setText("");
        editType.setText("");*/

    }
    public void ShowCars(View v){
        Cursor cursor = carDB.getAllCars();
        ArrayList<String> cars = new ArrayList<>();
         ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cars);
        //iterate record
        while (cursor.moveToNext()) {
            String brand = cursor.getString(cursor.getColumnIndex("brand"));//acces id
            String model = cursor.getString(cursor.getColumnIndex("model"));//acces name
            String year= cursor.getString(cursor.getColumnIndex("year"));
            String color= cursor.getString(cursor.getColumnIndex("color"));
            String type= cursor.getString(cursor.getColumnIndex("type"));
            String result = brand + " " + model + " " + year+ " " +color + " " + type;
            cars.add(result);}

        listView.setAdapter(adapter1);
        carDB.close();




    }

    public void DeleteCar(View v) {

    /*    String[] carStr;
        SparseBooleanArray sp = listView.getCheckedItemPositions();
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            if (sp.get(i)) {
                String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();

                carStr = s.split(" ");
                String carName = carStr[0].trim();*/
                new AlertDialog.Builder(this)
                        .setTitle("Approve")  // confirmation screen
                        .setMessage("Do you want to do Perform this Action?") // show message
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(admin.this, "yes", Toast.LENGTH_SHORT).show();
                               car.remove(editBrand.getText().toString(),(editModel.getText().toString()));
                            }}) // if confirmed, it will delete when id and name match
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        }).show();


            }
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
            Intent intent = new Intent(admin.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_About) {
            Toast.makeText(getApplicationContext(),"This application developed by Umut Alpaydin & Gurkan Saki",Toast.LENGTH_SHORT).show();

        }
    }




    public void DeleteUser(View v) {

        String[] userString;
        SparseBooleanArray sp = listViewUser.getCheckedItemPositions();
        for (int i = 0; i < listViewUser.getAdapter().getCount(); i++) {
            if (sp.get(i)) {
                String s = ((CheckedTextView) listViewUser.getChildAt(i)).getText().toString();

                userString = s.split(" ");
                String userName = userString[1].trim();
                new AlertDialog.Builder(this)
                        .setTitle("Approve")  // confirmation screen
                        .setMessage("Do you want to do Perform this Action?") // show message
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(admin.this, "yes", Toast.LENGTH_SHORT).show();
                                userDB.deleteUser(userName);
                            }}) // if confirmed, it will delete when id and name match
                        .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        }).show();


            }

        }


    }


}