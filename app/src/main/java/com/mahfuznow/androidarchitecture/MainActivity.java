package com.mahfuznow.androidarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mahfuznow.androidarchitecture.mvc.view.MVCActivity;
import com.mahfuznow.androidarchitecture.mvp.view.MVPActivity;
import com.mahfuznow.androidarchitecture.mvvm.MVVMActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClickMVCActivity(View view) {
        startActivity(new Intent(this, MVCActivity.class));
    }

    public void OnClickMVPActivity(View view) {
        startActivity(new Intent(this, MVPActivity.class));
    }

    public void OnClickMVVMActivity(View view) {
        startActivity(new Intent(this, MVVMActivity.class));
    }
}