package fast.glibrary.base.adapter;

import android.view.ViewGroup;

import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/27 17:21
 * 修改人：Administrator
 * 修改时间：2016/12/27 17:21
 * 修改备注：
 */
public interface GPagerBinder<T> {
    void bindData(ViewGroup container, GViewHolder holder, T t, int position);
}
