package fast.glibrary.weight;
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

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.Footer.LoadingView;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import fast.glibrary.R;
import fast.glibrary.tools.ScreenTools;
import fast.glibrary.uiKit.GViewHolder;

/**
 * Created by Administrator on 2017/2/26.
 * Description:
 * Function:
 */

public class FastRefresher<T> extends RelativeLayout {
    TwinklingRefreshLayout mChild;
    RecyclerView mList;
    GViewHolder mHeadHolder;
    TextView mEmptyView;

    public FastRefresher(Context context) {
        this(context, null);
    }

    public FastRefresher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastRefresher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (mChild == null) {
            mChild = new TwinklingRefreshLayout(context);
        }
        if (mList == null) {
            mList = new RecyclerView(context);
            mList.setBackgroundResource(R.color.white);
        }
        if (mEmptyView == null) {
            mEmptyView = new TextView(context);
        }
        //this(context,attrs,defStyleAttr,0);
    }

    @TargetApi(23)
    public FastRefresher(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (mChild == null) {
            mChild = new TwinklingRefreshLayout(context);
        }
        if (mList == null) {
            mList = new RecyclerView(context);
            mList.setBackgroundResource(R.color.white);
        }
        if (mEmptyView == null) {
            mEmptyView = new TextView(context);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mChild != null) {

            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            if (mList != null) {
                LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mList.setOverScrollMode(OVER_SCROLL_NEVER);
                setHeaderView(getDefaultHeader());
                mChild.addView(mList, lparams);
            }
            if (mEmptyView != null) {
                mEmptyView.setGravity(Gravity.CENTER);
                mEmptyView.setText(getContext().getResources().getString(R.string.empty_list_data));
                mChild.addView(mEmptyView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                hideEmptyView();
            }
            addView(mChild, params);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        if (mList != null) {
            mList.setLayoutManager(manager);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mList.getLayoutManager() == null) {
            mList.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        if (mList != null) {
            mList.setAdapter(adapter);
        }
    }

    public void setOnRefreshListener(RefreshListenerAdapter listener) {
        if (mChild != null) {
            mChild.setOnRefreshListener(listener);
        }
    }

    public void endRefreshing() {
        if (mChild != null) {
            mChild.finishRefreshing();
        }
    }

    public void endLoading() {
        if (mChild != null) {
            mChild.finishLoadmore();
        }
    }

    public void setHeaderView(IHeaderView view) {
        if (mChild != null) {
            mChild.setHeaderView(view);
            mHeadHolder = new GViewHolder(view.getView());
        }
    }

    public void setBottomView(IBottomView view) {
        if (mChild != null) {
            mChild.setBottomView(view);
        }
    }

    private IHeaderView getDefaultHeader() {
        mHeadHolder = new GViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.iheader_refresh, null));
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.mipmap.anrrow);
        headerView.setReleaseRefreshStr("放开立即刷新");
        return headerView;
    }

    private IBottomView getDefaultFooter() {
        LoadingView headerView = new LoadingView(getContext());
        return headerView;
    }

    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }
    }

    public void setEmptyText(String tips) {
        if (mEmptyView != null && !TextUtils.isEmpty(tips)) {
            mEmptyView.setText(tips);
        }
    }
}
