package com.mahfuznow.androidarchitecture.mvc.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mahfuznow.androidarchitecture.R;
import com.mahfuznow.androidarchitecture.model.Country;
import com.mahfuznow.androidarchitecture.mvc.controller.MVCController;

import java.util.ArrayList;
import java.util.List;

public class MVCActivity extends AppCompatActivity {

    List<String> listValues;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    ProgressBar progressBar;
    MVCController controller;

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

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progress_bar);

        //Initializing the controller so that it starts processing the business logic
        // and send data to this activity
        controller = new MVCController(this);

        listValues = new ArrayList<>();
        //arrayAdapter works like a middle man between the arraylist and the listview
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.textView, listValues);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MVCActivity.this,"You clicked on " + listValues.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    //this method will be called by the controller when data retrieved successfully.
    // Controller will pass the necessary data which need to be
    public void setValues(List<Country> countries) {
        listValues.clear();
        for (Country country : countries) {
            listValues.add(country.CountryName);
            progressBar.setVisibility(View.INVISIBLE);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    //this method will be called by the controller when there is an error to retrieve date.
    public void onError() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MVCActivity.this,"Failed to load data",Toast.LENGTH_SHORT).show();
    }

    //for back arrow functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}