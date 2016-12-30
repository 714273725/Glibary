package fast.glibrary.base.adapter;

import android.support.v4.view.PagerAdapter;
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
 * 创建时间：2016/12/27 17:02
 * 修改人：Administrator
 * 修改时间：2016/12/27 17:02
 * 修改备注：
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {


    List<T> data;
    SparseArray<GViewHolder> holders;

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public BasePagerAdapter() {
        holders = new SparseArray<>();
    }

    public BasePagerAdapter(List<T> data) {
        holders = new SparseArray<>();
        this.data = data;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(pagerLayout(position), container, false);
        GViewHolder holder = new GViewHolder(view);
        container.addView(view);
        bindData(container, holder, data.get(position), position);
        holders.put(position, holder);
        return view;
    }

    public abstract void bindData(ViewGroup container, GViewHolder holder, T t, int position);

    public abstract int pagerLayout(int pos);



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
