package com.mahfuznow.androidarchitecture.mvvm.view;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mahfuznow.androidarchitecture.R;
import com.mahfuznow.androidarchitecture.model.Country;
import com.mahfuznow.androidarchitecture.mvvm.viewmodel.MVVMViewModel;

import java.util.ArrayList;
import java.util.List;

/*
DIFFERENCES compared to the MVPActivity.java class::
    * using viewModel instead of presenter
    * no need to implement any interface
    * setValues() and onError() functions are being called from the observeLiveData() function
*/

public class MVVMActivity extends AppCompatActivity {

    List<String> listValues;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    ProgressBar progressBar;
    MVVMViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvmactivity);

        //set title and enabling back arrow button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.mvvm_activity);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progress_bar);

        //Here we use "ViewModelProvider" class from 'androidx.lifecycle' to reduce boilerplate codes regarding lifecycle management.
        viewModel = new ViewModelProvider(this).get(MVVMViewModel.class);

        listValues = new ArrayList<>();
        //arrayAdapter works like a middle man between the arraylist and the listview
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row_layout_2, R.id.textView, listValues);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MVVMActivity.this, "You clicked on " + listValues.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // we can observe the live data anytime according our need
        observeLiveData();
    }

    // Here we are observing those live data to get the latest data
    private void observeLiveData() {
        viewModel.getCountriesLiveData().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                setValues(countries);
            }
        });

        viewModel.getIsErrorLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    onError();
                }
            }
        });
    }

    /*
    Following is an unused method just to demonstrate how we can use lambda expression
    instead of anonymous class to simplify our code

    private void observeLiveData() {
        viewModel.getCountriesLiveData().observe(this, countries -> {
            setValues(countries);
        });

        viewModel.getIsErrorLiveData().observe(this, isError -> {
            if(isError) {
                onError();
            }
        });
    }
    */

    public void setValues(List<Country> countries) {
        listValues.clear();
        for (Country country : countries) {
            listValues.add(country.CountryName);
            progressBar.setVisibility(View.INVISIBLE);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    public void onError() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MVVMActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
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