package com.administrator.gdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import fast.glibrary.annotation.SaveState;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.adapter.AdapterBinder;
import fast.glibrary.base.adapter.BaseAdapter;
import fast.glibrary.base.adapter.DataFilter;
import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.base.adapter.GBinder;
import fast.glibrary.tools.L;
import fast.glibrary.tools.ScreenTools;
import fast.glibrary.uiKit.GPopupWindow;
import fast.glibrary.uiKit.GViewHolder;


public class MainActivity extends BaseActivity {
    @SaveState(value = SaveState.NORMAL_OBJECT)
    String[] strings = {"S"};
    GAdapter<String> adapter = new GAdapter<String>(R.layout.item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(i + "");
        }
        adapter.setDataBinder((viewTpe, data, holder1, pos, dataList) -> holder1.setImageUrlString(R.id.sdv, "http://abc.2008php.com/2015_Website_appreciate/2015-09-18/20150918111807.jpg"));
        GAdapter<String> ads = new GAdapter<String>(R.layout.viewtype_body_orderlist, strings) {
            @Override
            public void onCreatingHolder(GViewHolder holder, int viewType) {
                holder.getRecyclerView(R.id.rvList).setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                holder.getRecyclerView(R.id.rvList).setAdapter(adapter);
            }
        };
        //ads.setNewData(strings);
        //ads.addHeader(R.layout.header);
        RecyclerView view = (RecyclerView) findViewById(R.id.rvList);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(ads);
        ads.setDataBinder((viewTpe, data, holder, pos, dataList) -> adapter.setNewData(strings));
        ads.setDataFilter(data -> Stream.of(data).filter(value -> !value.endsWith("3")).collect(Collectors.toList()));
        L.e(ScreenTools.getScreenHeightPx() + "");
        L.e(ScreenTools.getScreenWidthPx() + "");
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.siv);
        simpleDraweeView.setImageURI(Uri.parse("http://abc.2008php.com/2015_Website_appreciate/2015-09-18/20150918111807.jpg"));
        simpleDraweeView.setOnClickListener(view1 -> new GPopupWindow(MainActivity.this, R.layout.dialog_pay_success)
                .showLocation(getWindow().getDecorView(),
                        Gravity.BOTTOM,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        //new GPopupWindow(this, R.layout.dialog_pay_success).show(simpleDraweeView);
    }
}
