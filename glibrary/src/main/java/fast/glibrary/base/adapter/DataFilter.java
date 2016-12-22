package fast.glibrary.base.adapter;

import java.util.List;

import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 14:58
 * 修改人：Administrator
 * 修改时间：2016/12/22 14:58
 * 修改备注：
 */
public interface DataFilter<T,G extends GViewHolder> {
    List<T> filter(List<T> data);
}
