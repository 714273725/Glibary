package com.administrator.gdemo.ui;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fast.game.library.view.FS;
import fast.game.library.view.HSurfaceView;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.tools.L;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/29 15:33
 * 修改人：Administrator
 * 修改时间：2016/12/29 15:33
 * 修改备注：
 */
public class WelcomeActivity extends BaseActivity{
    String[] path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new HSurfaceView(this));
    }
}
