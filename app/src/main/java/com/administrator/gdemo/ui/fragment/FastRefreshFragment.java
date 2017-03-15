package com.administrator.gdemo.ui.fragment;
//			          _ooOoo_
//	           	     o8888888o
//                   88" . "88
//                   (| -_- |)
//                    O\ = /O
//                ____/`---'\____
//              .   ' \\| |// `.
//               / \\||| : |||// \
//             / _||||| -:- |||||- \
//               | | \\\ - /// | |
//             | \_| ''\---/'' | |
//            \ .-\__ `-` ___/-. /
//          ___`. .' /--.--\ `. . __
//       ."" '< `.___\_<|>_/___.' >'"".
//      | | : `- \`.;`\ _ /`;.`/ - ` : | |
//        \ \ `-. \_ __\ /__ _/ .-` / /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//
//.............................................
//               佛祖保佑             永无BUG

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.gdemo.R;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.weight.FastRefresher;

/**
 * Created by Administrator on 2017/2/26.
 * Description:
 * Function:
 */

public class FastRefreshFragment extends Fragment {
    @BindView(R.id.frList)
    FastRefresher refresher;
    List<String> list = new ArrayList<>();

    {
        for (int i = 0; i < 0; i++) {
            list.add(i + "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fast_refresh, container, false);
        ButterKnife.bind(this, view);
        GAdapter<String> adapter = new GAdapter<String>(R.layout.item, list) {
            @Override
            public boolean bindView(GViewHolder holder, String s, int pos) {
                holder.setText(R.id.tvTitle, s);
                return true;
            }
        };
        refresher.setAdapter(adapter);
        refresher.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(() -> {
                    refresher.showEmptyView();
                    refresher.endRefreshing();
                }, 1000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(() -> refresher.endLoading(), 1000);
            }
        });
        return view;
    }
}
