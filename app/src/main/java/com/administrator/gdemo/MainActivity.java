package com.administrator.gdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fast.glibrary.annotation.SaveState;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.tools.L;
import fast.glibrary.tools.ScreenTools;


public class MainActivity extends BaseActivity {
    @SaveState(value = SaveState.NORMAL_OBJECT)
    String[] strings = {"S"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.e(ScreenTools.getScreenHeightPx() + "");
        L.e(ScreenTools.getScreenWidthPx() + "");
    }
}
