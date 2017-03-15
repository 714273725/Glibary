package fast.glibrary.base;
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

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fast.glibrary.R;
import fast.glibrary.manager.PageManager;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.TabIcon;

/**
 * Created by Administrator on 2017/2/28.
 * Description:
 * Function:
 */

public abstract class BaseNoSlideTabActivity<T extends BaseActivity> extends BaseActivity {
    public TabLayout tab;
    public Toolbar toolbar;
    public PageManager mPageManager;
    public String[] mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面的模式，tab在下方还是在上方
        int mode = getIntent().getIntExtra(Mode.Mode, Mode.Tab_Top);
        switch (mode) {
            case Mode.Tab_Top:
                setContentView(R.layout.activity_no_slide_tab);
                break;
            default:
                setContentView(R.layout.activity_no_slide_tab_below);
                break;
        }
        mTitles = getTitles();
        mPageManager = new PageManager(R.id.fl_container, getSupportFragmentManager(), mTitles);

        setUpView();
        selectPage(0);
    }

    public void selectPage(int index) {
        tab.getTabAt(index).select();
        mPageManager.add(getFragmentClz(index), getTitles()[index], getBundle(index));
    }


    private void setUpView() {
        tab = (TabLayout) findViewById(R.id.tab);
        for (int i = 0; i < getCount(); i++) {
            setTabView(tab, addTab(i));
        }
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    GViewHolder h = new GViewHolder(tab.getCustomView());
                    h.getView(R.id.tabIcon).setSelected(true);
                    h.getView(R.id.tabString).setSelected(true);
                }
                mPageManager.add(getFragmentClz(tab.getPosition())
                        , mTitles[tab.getPosition()], getBundle(tab.getPosition()));
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

    /**
     * 返回页数
     *
     * @return
     */
    public abstract int getCount();

    @NonNull
    public abstract TabIcon addTab(int pos);

    @Nullable
    public abstract Bundle getBundle(int pos);

    @NonNull
    public abstract Class getFragmentClz(int pos);

    @NonNull
    public abstract String[] getTitles();

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
}
