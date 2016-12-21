package com.administrator.gdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fast.glibrary.annotation.SaveState;

public class MainActivity extends AppCompatActivity {
    @SaveState
    String[] strings = {"S"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
