package fast.glibrary.base.adapter;

import java.util.List;

import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 17:02
 * 修改人：Administrator
 * 修改时间：2016/12/22 17:02
 * 修改备注：
 */
public interface DefaultAdapterBinder<K> {
    void bindData(int viewTpe, K data, GViewHolder holder, int pos, List<K> dataList);
}
