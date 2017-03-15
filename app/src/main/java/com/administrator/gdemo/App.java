package com.administrator.gdemo;

import android.app.Application;

import com.administrator.gdemo.bean.LoginBean;
import com.yolanda.nohttp.rest.Request;

import org.json.JSONObject;

import fast.glibrary.base.adapter.BaseAdapter;
import fast.glibrary.network.NetAction;
import fast.glibrary.network.NetWorkDispatcher;
import fast.glibrary.network.Param;
import fast.glibrary.sp.SecurityStorage;
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
public class App extends Application {
    SecurityStorage mStorage;
    @Override
    public void onCreate() {
        super.onCreate();
        GLibraryHelper.init(this, new NetWorkDispatcher.DispatcherConfig() {

            @Override
            public Param getDefaultHeader(NetAction action) {
                return null;
            }

            @Override
            public Param getDefaultParams(NetAction action) {
                return null;
            }

            @Override
            public Class getClz(NetAction action) {
                Class clz = null;
                switch (action.mAction) {
                    case 1:
                        clz = LoginBean.class;
                        break;
                }
                return clz;
            }

            @Override
            public void handleRequest(NetAction action, Request request) {

            }

            @Override
            public boolean needDefaultHeader(NetAction action) {
                return false;
            }

            @Override
            public boolean needDefaultParams(NetAction action) {
                return false;
            }

            @Override
            public Object handleData(NetAction action, Object data) {
                return null;
            }
        });

    }
}
