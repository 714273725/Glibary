package fast.glibrary.base;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fast.glibrary.R;
import fast.glibrary.base.adapter.BasePagerAdapter;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.TabIcon;

/**
 * 项目名称：GDemo
 * 类描述：快速实现一个tab+viewPager内容的页面
 * viewPager的内容是一个比较简单的layout
 * <p>
 * 创建人：gejianye
 * 创建时间：2016/12/30 11:00
 * 修改人：Administrator
 * 修改时间：2016/12/30 11:00
 * 修改备注：
 */
public abstract class BaseTabActivity<T> extends BaseActivity {

    ViewPager.OnPageChangeListener mPageChangeListener;
    public TabLayout tab;
    public Toolbar toolbar;
    public ViewPager pager;
    public BasePagerAdapter<T> adapter;

    /**
     * {@link Mode#Mode intent中通过这个key指定tab的位置模式}
     * {@link Mode#Tab_Top tab在viewpager上方}
     * {@link Mode#Tab_below tab在viewpager下方}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面的模式，tab在下方还是在上方
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

    /**
     * 初始化tab 与 viewPager
     * {@link BaseTabActivity#addTab(int)}
     * addTab(int) 返回 tab的属性
     */
    private void setUpView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        for (int i = 0; i < getCount(); i++) {
            setTabView(tab, addTab(i));
        }
        adapter = new BasePagerAdapter<T>() {
            @Override
            public void bindData(ViewGroup container, GViewHolder holder, T s, int position) {
                BaseTabActivity.this.bindData(container, holder, s, position);
            }

            @Override
            public int pagerLayout(int pos) {
                return getLayout(pos);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return addTab(position).getTabTitle();
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
                    h.getView(R.id.tabString).setSelected(true);
                }
                if (tab.getPosition() != pager.getCurrentItem()) {
                    pager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    GViewHolder h = new GViewHolder(tab.getCustomView());
                    h.getView(R.id.tabIcon).setSelected(false);
                    h.getView(R.id.tabString).setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setTabBackground(R.color.white);
        adapter.setData(setData());
        pager.setOffscreenPageLimit(getCount());
    }


    /**
     * 判断采用默认的tab还是自定义的tab
     *
     * @param tab
     * @param icon tab的属性
     */
    public void setTabView(TabLayout tab, TabIcon icon) {
        if (icon.getTabSelectedIcon() == 0) {
            tab.addTab(tab.newTab().setText(icon.getTabTitle()));
        } else {
            setUpCustomTab(tab, icon);
        }
    }

    /**
     * 设置自定义的tab
     *
     * @param tab
     * @param icon
     */
    private void setUpCustomTab(TabLayout tab, TabIcon icon) {
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
            TextView textView = holder.getView(R.id.tabString);
            setUpCustomTabText(textView);
            tab.addTab(tab.newTab().setCustomView(v));
        }
    }

    /**
     * 设置自定义的tab的文字颜色
     *
     * @param textView
     */
    private void setUpCustomTabText(TextView textView) {
        int[] colors = setIndicatorColor();
        if (colors != null) {
            int select = 0;
            int unSelect = 0;
            try {
                select = getResources().getColor(colors[0]);
                unSelect = getResources().getColor(colors[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (select != 0 && unSelect != 0) {
                ColorStateList drawble = new ColorStateList(
                        new int[][]{{android.R.attr.state_selected}, {0}},
                        new int[]{unSelect, select});
                textView.setTextColor(drawble);
            }
        }
    }

    public void addPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
    }

    /**
     * 设置tab的背景色
     *
     * @param colorRes
     */
    public void setTabBackground(int colorRes) {
        tab.setBackgroundResource(colorRes);
    }

    /**
     * [0] 未选中的字体颜色res
     * [1] 选中的字体颜色的res
     *
     * @return
     */
    public int[] setIndicatorColor() {
        return null;
    }


    /**
     * 提供每一页的视图和数据用于设置页面
     *
     * @param container
     * @param holder
     * @param data
     * @param position
     */
    public abstract void bindData(ViewGroup container, GViewHolder holder, T data, int position);

    /**
     * 返回每一页的布局id
     *
     * @param pos
     * @return
     */
    public abstract int getLayout(int pos);

    /**
     * 返回页数
     *
     * @return
     */
    public abstract int getCount();

    public abstract List<T> setData();

    public abstract TabIcon addTab(int pos);


}
