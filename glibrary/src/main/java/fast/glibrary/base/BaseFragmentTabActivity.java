package fast.glibrary.base;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import fast.glibrary.R;
import fast.glibrary.base.adapter.BaseFragmentPagerAdapter;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.TabIcon;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 11:00
 * 修改人：Administrator
 * 修改时间：2016/12/30 11:00
 * 修改备注：
 */
public abstract class BaseFragmentTabActivity extends BaseActivity {


    ViewPager.OnPageChangeListener mPageChangeListener;
    TabLayout tab;
    Toolbar toolbar;
    ViewPager pager;
    public BaseFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mode = getIntent().getIntExtra(Mode.Mode, Mode.Tab_Top);
        switch (mode) {
            case Mode.Tab_Top:
                setContentView(R.layout.activity_base_tab);
                break;
            default:
                setContentView(R.layout.activity_base_tab_below);
                break;
        }
        setUpView();
    }

    private void setUpView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        for (int i = 0; i < getFragmentCount(); i++) {
            setTabView(tab, addTab(i));
        }
        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getFragment(int position) {
                return getFragmentEntity(position);
            }

            @Override
            public int getCount() {
                return getFragmentCount();
            }
        };
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPageChangeListener != null) {
                    mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (tab.getSelectedTabPosition() != position) {
                    tab.getTabAt(position).select();
                }
                if (mPageChangeListener != null) {
                    mPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mPageChangeListener != null) {
                    mPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    GViewHolder h = new GViewHolder(tab.getCustomView());
                    h.getView(R.id.tabIcon).setSelected(true);
                }
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    GViewHolder h = new GViewHolder(tab.getCustomView());
                    h.getView(R.id.tabIcon).setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setTabBackground(R.color.white);
        pager.setOffscreenPageLimit(getFragmentCount());
    }

    public void setTabView(TabLayout tab, TabIcon icon) {
        if (icon.getTabSelectedIcon() == 0) {
            tab.addTab(tab.newTab().setText(icon.getTabTitle()));
        } else {
            Drawable select = null;
            Drawable unSelect = null;
            try {
                select = getResources().getDrawable(icon.getTabSelectedIcon());
                unSelect = getResources().getDrawable(icon.getTabUnselectedIcon());
            } catch (Exception e) {

            }
            if (select == null || unSelect == null) {
                tab.addTab(tab.newTab().setText(icon.getTabTitle()));
            } else {
                StateListDrawable drawble = new StateListDrawable();
                int stateSelected = android.R.attr.state_selected;
                drawble.addState(new int[]{stateSelected}, select);
                drawble.addState(new int[]{}, unSelect);
                View v = LayoutInflater.from(this).inflate(R.layout.tab, null);
                GViewHolder holder = new GViewHolder(v);
                ImageView iv = holder.getView(R.id.tabIcon);
                iv.setImageDrawable(drawble);
                holder.setText(R.id.tabString, icon.getTabTitle());
                if (TextUtils.isEmpty(icon.getTabTitle())) {
                    holder.setVisibility(R.id.tabString, View.GONE);
                } else {
                    holder.setVisibility(R.id.tabString, View.VISIBLE);
                }
                tab.addTab(tab.newTab().setCustomView(v));
            }
        }
    }

    public void addPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
    }

    public void setTabBackground(int colorRes) {
        tab.setBackgroundResource(colorRes);
    }

    public abstract Fragment getFragmentEntity(int pos);

    public abstract int getFragmentCount();

    public abstract TabIcon addTab(int pos);
}
