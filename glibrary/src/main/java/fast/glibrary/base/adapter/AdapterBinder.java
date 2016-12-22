package fast.glibrary.base.adapter;

import java.util.List;

import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 13:02
 * 修改人：Administrator
 * 修改时间：2016/12/22 13:02
 * 修改备注：
 */
public interface AdapterBinder<K,T extends GViewHolder> {
    void bindData(int viewTpe, K data, T holder, int pos, List<K> dataList);
}
