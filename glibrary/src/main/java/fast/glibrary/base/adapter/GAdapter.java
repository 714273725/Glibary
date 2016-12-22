package fast.glibrary.base.adapter;

import android.view.View;

import java.util.List;

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

    public GAdapter(int defaultLayoutId) {
        super(defaultLayoutId);
    }

    public GAdapter(List<T> data) {
        super(data);
    }

    @Override
    public GViewHolder getHolder(View view) {
        return new GViewHolder(view);
    }

    @Override
    public boolean bindView(GViewHolder holder, T t, int pos) {
        if (mDataBinder != null) {
            mDataBinder.bindData(getDefaultLayoutId(), t, holder, pos, getShowList());
            return true;
        }
        return super.bindView(holder, t, pos);
    }

    @Override
    public boolean bindFooter(GViewHolder holder, int footerId) {
        if (mDataBinder != null) {
            mDataBinder.bindData(getDefaultLayoutId(), null, holder, -1, getShowList());
            return true;
        }
        return super.bindFooter(holder, footerId);
    }

    @Override
    public boolean bindHeader(GViewHolder holder, int headerId) {
        if (mDataBinder != null) {
            mDataBinder.bindData(getDefaultLayoutId(), null, holder, -1, getShowList());
            return true;
        }
        return super.bindHeader(holder, headerId);
    }
}
