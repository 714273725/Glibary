package com.administrator.gdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.administrator.gdemo.R;

import butterknife.BindView;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.network.Param;

/**
 * Created by Administrator on 2017/2/10.
 */

public class OverScrollActivity extends BaseActivity {
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_scroll);
    }

    @Override
    public Param getDefaultParams() {
        return null;
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
