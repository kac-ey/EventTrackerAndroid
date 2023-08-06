package com.zybooks.eventtracker;

import static com.zybooks.eventtracker.EventDb.TABLENAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListEvent extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_SEND_SMS = 0;
    EventDb eventDb;
    RecyclerView recyclerView;
    MyAdapter adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        // Display toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check if permission not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){

            }
            else {
                // Ask for required permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SEND_SMS);
            }
        }

        Boolean checkEvent = eventDb.checkEvent();
        if (checkEvent == true) {
            displayData();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void displayData() {
        db = eventDb.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME + "", null);
        ArrayList<EventModel> eventModelArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            String date = cursor.getString(2);
            String start = cursor.getString(3);
            String end = cursor.getString(4);
            eventModelArrayList.add(new EventModel(title, date, start, end));
        }
        cursor.close();
        adapter = new MyAdapter(this, R.layout.recyclerview, eventModelArrayList, db);
        recyclerView.setAdapter(adapter);
    }


    public void addEvent(View view) {
        Intent intent = new Intent(this, AddEvent.class);
        startActivity(intent);
    }

    // Include menu with logout button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        return true;
    }

    // Handle menu options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            // Return to login screen
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Result of permission request passed
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // Check requestCode
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_SEND_SMS) {// Check length of grantResults > 0 and == PERMISSION_GRANTED
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You will receive text message updates on your events now!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You will not receive text message updates on your events.", Toast.LENGTH_LONG).show();
            }
        }
    }
}