package com.administrator.gdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fast.glibrary.annotation.SaveState;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.adapter.BasePagerAdapter;
import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.base.adapter.GPagerAdapter;
import fast.glibrary.base.adapter.GPagerBinder;
import fast.glibrary.tools.L;
import fast.glibrary.tools.ScreenTools;
import fast.glibrary.uiKit.GPopupWindow;
import fast.glibrary.uiKit.GViewHolder;


public class MainActivity extends BaseActivity {
    @SaveState(value = SaveState.NORMAL_OBJECT)
    String[] strings = {String.valueOf(R.drawable.a1),
            String.valueOf(R.drawable.a2),
            String.valueOf(R.drawable.a3),
            String.valueOf(R.drawable.a4),
            String.valueOf(R.drawable.a5)};
    GAdapter<String> adapter = new GAdapter<String>(R.layout.item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.vpContent);
        GPagerAdapter<String> adapter = new GPagerAdapter<>(R.layout.pager_adapter);
        pager.setAdapter(adapter);
        adapter.setBinder((container, holder, s, position) -> {
            holder.getViewHolder(R.id.siv).
                    setImageUri((new Uri.Builder()).scheme("res").path(s).build());
        });
        adapter.setData(Arrays.asList(strings));
    }
}
