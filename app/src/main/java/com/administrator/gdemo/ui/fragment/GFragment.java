package com.administrator.gdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.gdemo.R;

import java.util.ArrayList;
import java.util.List;

import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.tools.L;
import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 15:41
 * 修改人：Administrator
 * 修改时间：2016/12/30 15:41
 * 修改备注：
 */
public class GFragment extends Fragment {
    List<String> list = new ArrayList<>();

    {
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewtype_body_orderlist, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GAdapter<String> adapter = new GAdapter<String>(R.layout.item, list){
            @Override
            public boolean bindView(GViewHolder holder, String s, int pos) {
                holder.setText(R.id.tvTitle,s);
                return true;
            }
        };
        recyclerView.setAdapter(adapter);
        return view;
    }
}
