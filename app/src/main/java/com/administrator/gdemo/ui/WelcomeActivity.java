package com.administrator.gdemo.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.administrator.gdemo.MainActivity;
import com.administrator.gdemo.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fast.game.library.view.FS;
import fast.game.library.view.GSurfaceView;
import fast.game.library.view.HSurfaceView;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.BaseTabActivity;
import fast.glibrary.network.Param;
import fast.glibrary.tools.L;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.TabIcon;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/29 15:33
 * 修改人：Administrator
 * 修改时间：2016/12/29 15:33
 * 修改备注：
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics outMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        GSurfaceView.SCREEN_WIDTH = outMetrics.widthPixels;
        GSurfaceView.SCREEN_HEIGHT = outMetrics.heightPixels;
        //setContentView(new GSurfaceView(getThis()));
        new Handler().postDelayed(() -> {
            start(MainActivity.class);
            finish();
        }, 2000);
        //setContentView(R.layout.activity_main);
    }

    @Override
    public Param getDefaultParams() {
        return null;
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
