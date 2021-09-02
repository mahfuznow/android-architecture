package com.mahfuznow.androidarchitecture.mvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import com.mahfuznow.androidarchitecture.R;

public class MVPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpactivity);

        //set title and enabling back arrow button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.mvc_activity);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void OnClickMVPActivity1(View view) {
        startActivity(new Intent(this, MVPActivity1.class));
    }

    public void OnClickMVPActivity2(View view) {
        startActivity(new Intent(this, MVPActivity2.class));
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