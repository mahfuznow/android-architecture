package com.mahfuznow.androidarchitecture.mvp.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mahfuznow.androidarchitecture.R;
import com.mahfuznow.androidarchitecture.model.Country;
import com.mahfuznow.androidarchitecture.mvp.presenter.MVPPresenter;

import java.util.ArrayList;
import java.util.List;

/*
DIFFERENCES compared to the MVCActivity.java class::
    * implementing the MyView interface
    * using presenter instead of controller
    * setValues() and onError() functions are overridden function now
*/

public class MVPActivity1 extends AppCompatActivity implements MVPPresenter.MyView {

    List<String> listValues;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    ProgressBar progressBar;
    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpactivity1);

        //set title and enabling back arrow button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.mvp_activity_1);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progress_bar);

        //Initializing the controller so that it starts processing the business logic
        // and send data to this activity
        presenter = new MVPPresenter(this); //here 'this' is referring to MyView instance, not the Activity instance

        listValues = new ArrayList<>();
        //arrayAdapter works like a middle man between the arraylist and the listview
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.textView, listValues);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MVPActivity1.this, "You clicked on " + listValues.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //for back arrow functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //We need to implement those function which is being declared in the MyView interface in the MVPPresenter.
    @Override
    public void setValues(List<Country> countries) {
        listValues.clear();
        for (Country country : countries) {
            listValues.add(country.CountryName);
            progressBar.setVisibility(View.INVISIBLE);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MVPActivity1.this,"Failed to load data",Toast.LENGTH_SHORT).show();
    }

    public void test() {

    }
}