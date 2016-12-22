package com.administrator.gdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;

import fast.glibrary.annotation.SaveState;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.adapter.AdapterBinder;
import fast.glibrary.base.adapter.BaseAdapter;
import fast.glibrary.base.adapter.DataFilter;
import fast.glibrary.tools.L;
import fast.glibrary.tools.ScreenTools;
import fast.glibrary.uiKit.GViewHolder;


public class MainActivity extends BaseActivity {
    @SaveState(value = SaveState.NORMAL_OBJECT)
    String[] strings = {"S"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(i + "");
        }
        BaseAdapter<String, Gold> ads = new BaseAdapter(R.layout.viewtype_body_orderlist) {
            @Override
            public Gold getHolder(View view) {
                return new Gold(view);
            }
        };
        ads.setNewData(strings);
        ads.addHeader(R.layout.header);
        RecyclerView view = (RecyclerView) findViewById(R.id.rvList);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(ads);
        ads.setBindDataListener((viewTpe, data, holder, pos, dataList) -> {});
        ads.setDataFilter(data -> Stream.of(data).filter(value -> !value.endsWith("3")).collect(Collectors.toList()));
        L.e(ScreenTools.getScreenHeightPx() + "");
        L.e(ScreenTools.getScreenWidthPx() + "");

    }
}
