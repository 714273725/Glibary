package com.administrator.gdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.administrator.gdemo.bean.LoginBean;
import com.administrator.gdemo.config.URL;
import com.administrator.gdemo.ui.TabActivity;
import com.administrator.gdemo.ui.TabFragmentActivity;
import com.administrator.gdemo.ui.TabHostActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fast.glibrary.annotation.SaveState;
import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.BaseResponseLister;
import fast.glibrary.base.Mode;
import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.data.DataFatroty;
import fast.glibrary.network.NetAction;
import fast.glibrary.network.NetWorkDispatcher;
import fast.glibrary.network.Param;
import fast.glibrary.tools.L;
import fast.glibrary.tools.MD5;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.weight.GRefresher;
import fast.glibrary.weight.uiKit.GRStateView;


public class MainActivity extends BaseActivity {
    @SaveState(value = SaveState.NORMAL_OBJECT)
    String[] strings = {String.valueOf(R.drawable.a1),
            String.valueOf(R.drawable.a2),
            String.valueOf(R.drawable.a3),
            String.valueOf(R.drawable.a4),
            String.valueOf(R.drawable.a5)};
    GAdapter<String> adapter = new GAdapter<String>(R.layout.item) {
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
    @BindView(R.id.srl)
    GRefresher srl;
    @BindView(R.id.siv)
    SimpleDraweeView mSimpleDraweeView;

    {
        itemList.add("TopTabViewPager");
        itemList.add("BottomTabViewPager");
        itemList.add("TabTopFragment");
        itemList.add("TabBottomFragment");
        itemList.add("TabHostBottomFragment");
      /*  itemList.add("OverScroll");
        itemList.add("TopTabViewPager");
        itemList.add("BottomTabViewPager");
        itemList.add("TabTopFragment");
        itemList.add("TabBottomFragment");
        itemList.add("OverScroll");
        itemList.add("TopTabViewPager");
        itemList.add("BottomTabViewPager");
        itemList.add("TabTopFragment");
        itemList.add("TabBottomFragment");
        itemList.add("OverScroll");
        itemList.add("TopTabViewPager");
        itemList.add("BottomTabViewPager");
        itemList.add("TabTopFragment");
        itemList.add("TabBottomFragment");
        itemList.add("OverScroll");*/

    }

    GRStateView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Uri uri = Uri.parse("res://com.administrator.gdemo/" + R.drawable.anim);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mSimpleDraweeView.setController(controller);
        rvList.setLayoutManager(new LinearLayoutManager(getThis()));
        rvList.setAdapter(adapter);
        adapter.setNewData(itemList);
        adapter.setDataBinder((viewTpe, data, holder, pos, dataList) -> {
            holder.setText(R.id.tvTitle, data);
            holder.itemView.setTag(pos);
            holder.setImageUrlString(R.id.sdv, DataFatroty.getImage());
        });
        header = new GRStateView() {
            @Override
            public View getLoadingView() {
                return LayoutInflater.from(getThis()).inflate(R.layout.header_refreshing, null);
            }

            @Override
            public View getHoldingView() {
                return LayoutInflater.from(getThis()).inflate(R.layout.header_refresh, null);
            }

            @Override
            public View getNormalView() {
                return LayoutInflater.from(getThis()).inflate(R.layout.header_refresh_tips, null);
            }

            @Override
            public int getAnimViewId() {
                return R.id.iv_GRefresher_indicator;
            }
        };
        srl.setHeaderView(header);
        srl.setFooterView(new GRStateView() {
            @Override
            public View getLoadingView() {
                return LayoutInflater.from(getThis()).inflate(R.layout.header_refreshing, null);
            }

            @Override
            public View getHoldingView() {
                return LayoutInflater.from(getThis()).inflate(R.layout.header_refresh, null);
            }

            @Override
            public View getNormalView() {
                return LayoutInflater.from(getThis()).inflate(R.layout.header_refresh_tips, null);
            }

            @Override
            public int getAnimViewId() {
                return R.id.iv_GRefresher_indicator;
            }
        });
        srl.setPullListener(new GRefresher.PullListener() {
            @Override
            public boolean canLoadMore() {
                return true;
            }

            @Override
            public void refresh() {
                //srl.postDelayed(() -> srl.endRefreshing(), 3000);
            }

            @Override
            public void loadMore() {
                //srl.postDelayed(() -> srl.endLoading(), 3000);
            }
        });

        NetWorkDispatcher.sendPost(URL.LOGIN, new NetAction(1), new Param()
                .add("formMap[mobilePhone]", "15917100459")
                .add("formMap[password]", MD5.getMessageDigest("123456".getBytes()))
                .add("formMap[userType]", "1"), new BaseResponseLister<LoginBean>() {
            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void success(int what, Response response, LoginBean jsonObject) {
                L.e(jsonObject.toString());
            }
        });

        NetWorkDispatcher.get("http://www.weather.com.cn/data/sk/101010100.html", new NetAction(2), new BaseResponseLister() {
            @Override
            public void success(int what, Response response, Object o) {
                L.e(o.toString());
            }

            @Override
            public void onFailed(int what, Response response) {

            }
        });
    }

    @Override
    public Param getDefaultParams() {
        return null;
    }


    private void click(View v) {
        switch ((int) v.getTag()) {
            case 0:
                start(TabActivity.class);
                break;
            case 1:
                start(TabActivity.class, intent -> intent.putExtra(Mode.Mode, Mode.Tab_below));
                break;
            case 2:
                start(TabFragmentActivity.class);
                break;
            case 3:
                start(TabFragmentActivity.class, intent -> intent.putExtra(Mode.Mode, Mode.Tab_below));
                break;
            case 4:
                start(TabHostActivity.class);
                break;
            case 5:
                start(TabHostActivity.class);
                break;
        }
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
