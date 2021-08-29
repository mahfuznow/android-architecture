package com.mahfuznow.androidarchitecture.mvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mahfuznow.androidarchitecture.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MVCActivity extends AppCompatActivity {

    List<String> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvcactivity);

        //set title and enabling back arrow button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.mvc_activity);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ListView listView = findViewById(R.id.listView);

        listValues = new ArrayList<>();
        setValues();

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,R.layout.row_layout,R.id.textView,listValues);
        listView.setAdapter(arrayAdapter);
    }

    //setting dummy values
    void setValues() {
        listValues.add("Bangladesh");
        listValues.add("India");
        listValues.add("Pakistan");
        listValues.add("Nepal");
        listValues.add("Bhutan");
        listValues.add("China");
        listValues.add("Japan");
        listValues.add("USA");
        listValues.add("Canada");
    }

    //functionality for back arrow button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}