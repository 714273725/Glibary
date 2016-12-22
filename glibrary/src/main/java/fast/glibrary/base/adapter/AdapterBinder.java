package fast.glibrary.base.adapter;

import java.util.List;

import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：简单绑定Adapter的数据
 * 包括头布局和尾部局
 * 创建人：gejianye
 * 创建时间：2016/12/22 13:02
 * 修改人：Administrator
 * 修改时间：2016/12/22 13:02
 * 修改备注：
 */
public interface AdapterBinder<K, T extends GViewHolder> {
    /**
     * @param viewTpe  每种布局的layout ID ，可根据此参数判断布局类型
     * @param data     item的数据，头布局和尾部局时为空
     * @param holder   holder
     * @param pos      position
     * @param dataList 数据集合
     */
    void bindData(int viewTpe, K data, T holder, int pos, List<K> dataList);
}
