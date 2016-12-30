package com.administrator.gdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.administrator.gdemo.ui.TabActivity;
import com.administrator.gdemo.ui.TabFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fast.game.library.view.GSurfaceView;
import fast.glibrary.annotation.SaveState;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.Mode;
import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.base.adapter.GBinder;
import fast.glibrary.data.DataFatroty;
import fast.glibrary.uiKit.GViewHolder;


public class MainActivity extends BaseActivity {
    @SaveState(value = SaveState.NORMAL_OBJECT)
    String[] strings = {String.valueOf(R.drawable.a1),
            String.valueOf(R.drawable.a2),
            String.valueOf(R.drawable.a3),
            String.valueOf(R.drawable.a4),
            String.valueOf(R.drawable.a5)};
    GAdapter<String> adapter = new GAdapter<String>(R.layout.item){
        @Override
        public GViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            GViewHolder holder = super.onCreateViewHolder(parent, viewType);
            holder.itemView.setOnClickListener(v -> click(v));
            return holder;
        }
    };

    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    List<String> itemList = new ArrayList<>();

    {
        itemList.add("TopTab");
        itemList.add("BottomTab");
        itemList.add("TabTopFragment");
        itemList.add("TabBottomFragment");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rvList.setLayoutManager(new LinearLayoutManager(getThis()));
        rvList.setAdapter(adapter);
        adapter.setNewData(itemList);
        adapter.setDataBinder((viewTpe, data, holder, pos, dataList) -> {
            holder.setText(R.id.tvTitle, data);
            holder.itemView.setTag(pos);
            holder.setImageUrlString(R.id.sdv, DataFatroty.getImage());
        });
    }


    private void click(View v) {
        switch ((int)v.getTag()){
            case 0:
                start(TabActivity.class);
                break;
            case 1:
                start(TabActivity.class, intent -> intent.putExtra(Mode.Mode,Mode.Tab_below));
                break;
            case 2:
                start(TabFragmentActivity.class);
                break;
            case 3:
                start(TabFragmentActivity.class, intent -> intent.putExtra(Mode.Mode,Mode.Tab_below));
                break;
        }
    }
}
