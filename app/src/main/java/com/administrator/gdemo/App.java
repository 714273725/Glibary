package com.administrator.gdemo;

import android.app.Application;

import fast.glibrary.base.adapter.BaseAdapter;
import fast.glibrary.network.NetWorkDispatcher;
import fast.glibrary.network.Param;
import fast.glibrary.tools.GLibraryHelper;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/21 13:51
 * 修改人：Administrator
 * 修改时间：2016/12/21 13:51
 * 修改备注：
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        GLibraryHelper.init(this, new NetWorkDispatcher.DispatcherConfig() {
            @Override
            public Param getDefaultHeader(int postId) {
                return null;
            }

            @Override
            public Param getDefaultParams(int postId) {
                return null;
            }
        });

    }
}
