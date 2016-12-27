package fast.glibrary.uiKit;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import fast.glibrary.base.adapter.GAdapter;
import fast.glibrary.uiKit.holder.BaseViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/12/20 9:54
 * 修改人：Administrator
 * 修改时间：2016/12/20 9:54
 * 修改备注：
 */
public class GViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<BaseViewHolder> mViewList;

    public GViewHolder(View itemView) {
        super(itemView);
        this.mViewList = new SparseArray<>();
    }

    public <T extends View> BaseViewHolder getViewHolder(@IdRes int id) {
        BaseViewHolder<T> holder = mViewList.get(id);
        if (holder == null) {
            holder = new BaseViewHolder<>((T) itemView.findViewById(id));
            mViewList.put(id, holder);
        }
        return holder;
    }

    public <T extends View> T getView(@IdRes int id) {
        return (T) getViewHolder(id).get();
    }

    public RecyclerView getRecyclerView(@IdRes int id) {
        return getView(id);
    }

    public GAdapter getGAdapter(@IdRes int id) {
        RecyclerView view = getView(id);
        if (view != null && view instanceof RecyclerView) {
            if (view.getAdapter() instanceof GAdapter) {
                return (GAdapter) view.getAdapter();
            }
        }
        return null;
    }

    /**
     * setEnable
     *
     * @param id
     * @param enable
     * @return {@link BaseViewHolder#setEnable(boolean)}
     */
    public GViewHolder setEnable(@IdRes int id, boolean enable) {
        getViewHolder(id).setEnable(enable);
        return this;
    }

    /**
     * setVisibility
     *
     * @param v
     * @return {@link View#setVisibility(int)}
     */
    public GViewHolder setVisibility(@IdRes int id, boolean v) {
        getViewHolder(id).setVisibility(v ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * setVisibility
     *
     * @param id
     * @param v
     * @return {@link BaseViewHolder#setVisibility(int)}
     */
    public GViewHolder setVisibility(@IdRes int id, int v) {
        getViewHolder(id).setVisibility(v);
        return this;
    }

    /**
     * setSelected
     *
     * @param id
     * @param selected
     * @return {@link BaseViewHolder#setSelect(boolean)}
     */
    public GViewHolder setSelect(@IdRes int id, boolean selected) {
        getViewHolder(id).setSelect(selected);
        return this;
    }

    /**
     * 为SimpleDraweeView设置图片地址
     *
     * @param id
     * @param url
     * @return {@link BaseViewHolder#setImageUrlString(String)}
     */
    public GViewHolder setImageUrlString(@IdRes int id, String url) {
        getViewHolder(id).setImageUrlString(url);
        return this;
    }

    /**
     * 为SimpleDraweeView设置宽高比
     *
     * @param id
     * @param ratio
     * @return {@link BaseViewHolder#setAspectRatio(float)}
     */
    public GViewHolder setAspectRatio(@IdRes int id, float ratio) {
        getViewHolder(id).setAspectRatio(ratio);
        return this;
    }

    /**
     * 给指定控件设置文字
     *
     * @param id
     * @param text
     * @return {@link BaseViewHolder#setText(String)}
     */
    public GViewHolder setText(@IdRes int id, String text) {
        getViewHolder(id).setText(text);
        return this;
    }

    /**
     * 给指定控件设置文字
     *
     * @param id
     * @param res
     * @return {@link GViewHolder#setText(int, Object, boolean)}
     */
    public GViewHolder setText(@IdRes int id, @StringRes int res) {
        setText(id, res, true);
        return this;
    }

    /**
     * @param id
     * @param format 是否从string资源中寻找适合的资源,true为从string中寻找
     * @return ViewHolder
     * {@link BaseViewHolder#setText(Object, boolean)}
     */
    public GViewHolder setText(@IdRes int id, Object data, boolean format) {
        getViewHolder(id).setText(data, format);
        return this;
    }

    /**
     * getText from TextView or EditText
     *
     * @param id
     * @return
     */
    public String getText(@IdRes int id) {
        if (getView(id) instanceof TextView) {
            return ((TextView) getView(id)).getText().toString();
        }
        return null;
    }
}
