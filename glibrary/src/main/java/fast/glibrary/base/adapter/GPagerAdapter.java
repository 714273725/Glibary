package fast.glibrary.base.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/27 17:04
 * 修改人：Administrator
 * 修改时间：2016/12/27 17:04
 * 修改备注：
 */
public class GPagerAdapter<T> extends BasePagerAdapter<T> {
    GPagerBinder<T> binder;
    public void setBinder(GPagerBinder<T> binder) {
        this.binder = binder;
    }
    public GPagerAdapter(int defaultLayoutId) {
        super(defaultLayoutId);
    }
    public GPagerAdapter(int defaultLayoutId, List<T> data) {
        super(defaultLayoutId, data);
    }

    @Override
    public void bindData(ViewGroup container, GViewHolder holder, T t, int position) {
        if (binder != null) {
            binder.bindData(container, holder, t, position);
        }
    }

    @Override
    public int pagerLayout(int pos) {
        return defaultLayoutId;
    }
}
