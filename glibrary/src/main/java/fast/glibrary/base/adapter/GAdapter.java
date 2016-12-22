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
}
