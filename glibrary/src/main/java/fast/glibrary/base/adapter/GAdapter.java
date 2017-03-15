package fast.glibrary.base.adapter;

import android.view.View;

import java.util.List;

import fast.glibrary.tools.L;
import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 12:57
 * 修改人：Administrator
 * 修改时间：2016/12/22 12:57
 * 修改备注：
 */
public class GAdapter<T> extends BaseAdapter<T, GViewHolder> {


    GBinder<T> mDataBinder;
    private static final java.lang.String TAG = "GAdapter :";

    public void setDataBinder(GBinder<T> dataBinder) {
        this.mDataBinder = dataBinder;
    }

    public GAdapter(int mDefaultLayoutId, List<T> mData) {
        super(mDefaultLayoutId, mData);
    }

    public GAdapter(int defaultLayoutId) {
        super(defaultLayoutId);
    }

    @Override
    public GViewHolder getHolder(View view) {
        return new GViewHolder(view);
    }

    @Override
    public boolean bindView(GViewHolder holder, T t, int pos) {
        try {
            if (mDataBinder != null) {
                mDataBinder.bindData(getLayout(t) == null ? getDefaultLayoutId() : getLayout(t).getViewTypeId(), t, holder, pos, getShowList());
                return true;
            }
            return super.bindView(holder, t, pos);
        } catch (Exception e) {
            e.printStackTrace();
            L.e(TAG, "error when binding view");
            return super.bindView(holder, t, pos);
        }
    }

    @Override
    public boolean bindFooter(GViewHolder holder, int footerId) {
        try {
            if (mDataBinder != null) {
                mDataBinder.bindData(footerId, null, holder, -1, getShowList());
                return true;
            }
            return super.bindFooter(holder, footerId);
        } catch (Exception e) {
            e.printStackTrace();
            L.e(TAG, "error when binding footer");
            return super.bindFooter(holder, footerId);
        }
    }

    @Override
    public boolean bindHeader(GViewHolder holder, int headerId) {
        try {
            if (mDataBinder != null) {
                mDataBinder.bindData(headerId, null, holder, -1, getShowList());
                return true;
            }
            return super.bindHeader(holder, headerId);
        } catch (Exception e) {
            e.printStackTrace();
            L.e(TAG, "error when binding head");
            return super.bindHeader(holder, headerId);
        }

    }
}
