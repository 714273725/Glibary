package fast.glibrary.uiKit.holder;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import fast.glibrary.tools.L;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/12/20 9:57
 * 修改人：Administrator
 * 修改时间：2016/12/20 9:57
 * 修改备注：
 */
public class BaseViewHolder<T extends View> {
    public static final String TAG = "BaseViewHolder :";

    public BaseViewHolder(T mView) {
        this.mView = mView;
    }

    T mView;

    public BaseViewHolder getThis() {
        return this;
    }

    public T get() {
        return mView;
    }

    /**
     * setEnabled
     *
     * @param enable
     * @return {@link View#setEnabled(boolean)}
     */
    public BaseViewHolder setEnable(boolean enable) {
        if (checkNotNull())
            mView.setEnabled(enable);
        return this;
    }

    private boolean checkNotNull() {
        if (mView == null) {
            L.e(TAG, "view is null, skipping ");
            return false;
        }
        return true;
    }

    /**
     * setVisibility
     *
     * @param v
     * @return {@link View#setVisibility(int)}
     */
    public BaseViewHolder setVisibility(int v) {
        if (checkNotNull())
            mView.setVisibility(v);
        return this;
    }

    /**
     * setVisibility
     *
     * @param v
     * @return {@link View#setVisibility(int)}
     */
    public BaseViewHolder setVisibility(boolean v) {
        if (checkNotNull())
            mView.setVisibility(v ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * setSelected
     *
     * @param selected
     * @return {@link View#setSelected(boolean)}
     */
    public BaseViewHolder setSelect(boolean selected) {
        if (checkNotNull())
            mView.setSelected(selected);
        return this;
    }

    /**
     * 为SimpleDraweeView设置图片地址
     *
     * @param url
     * @return {@link BaseViewHolder#setImageUri(Uri)}
     */
    public BaseViewHolder setImageUrlString(String url) {
        if (TextUtils.isEmpty(url)) {
            return this;
        }
        setImageUri(Uri.parse(url));
        return this;
    }

    /**
     * 为SimpleDraweeView设置图片uri
     * {@link SimpleDraweeView#setImageURI(Uri)}
     *
     * @param uri
     * @return
     */
    public BaseViewHolder setImageUri(Uri uri) {
        if (uri == null) {
            return this;
        }
        if (checkNotNull() && mView instanceof SimpleDraweeView) {
            ((SimpleDraweeView) mView).setImageURI(uri);
        }
        return this;
    }

    /**
     * 为SimpleDraweeView设置宽高比
     *
     * @param ratio
     * @return {@link SimpleDraweeView#setAspectRatio(float)}
     */
    public BaseViewHolder setAspectRatio(float ratio) {
        if (checkNotNull() && mView instanceof SimpleDraweeView) {
            ((SimpleDraweeView) mView).setAspectRatio(ratio);
        }
        return this;
    }

    /**
     * 当控件为textView及其子类时，为其设置文字
     *
     * @param text
     * @return {@link TextView#setText(CharSequence)}
     */
    public BaseViewHolder setText(String text) {
        if (checkNotNull() && mView instanceof TextView) {
            ((TextView) mView).setText(TextUtils.isEmpty(text) ? "" : text);
        }
        return this;
    }

    /**
     * 是否从string资源中寻找适合的资源,true为从string中寻找
     *
     * @param res
     * @param format
     * @return {@link TextView#setText(int),BaseViewHolder#setText(String)}
     */
    public BaseViewHolder setText(@StringRes int res, boolean format) {
        if (checkNotNull() && mView instanceof TextView) {
            if (format) {
                ((TextView) mView).setText(res);
            } else {
                setText(String.valueOf(res));
            }
        }
        return this;
    }

    /**
     * setText
     *
     * @param id
     * @return {@link BaseViewHolder#setText(int, boolean)}
     */
    public BaseViewHolder setText(@StringRes int id) {
        setText(id, true);
        return this;
    }

    /**
     * setText
     *
     * @param id
     * @param format 是否从string资源中寻找适合的资源,true为从string中寻找
     * @return ViewHolder
     */
    public BaseViewHolder setText(Object id, boolean format) {
        if (id instanceof String) {
            setText((String) id);
        } else {
            if (id.getClass() == int.class) {
                setText((int) id, format);
            } else {
                setText(String.valueOf(id));
            }
        }
        return this;
    }

    /**
     * imageView 设置 drawable
     *
     * @param drawable {@link Drawable}
     */
    public BaseViewHolder setImageDrawable(Drawable drawable) {
        if (checkNotNull() && mView instanceof ImageView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mView.setBackground(drawable);
            } else {
                mView.setBackgroundDrawable(drawable);
            }
        }
        return this;
    }


    @TargetApi(21)
    public BaseViewHolder setImageBackgroundRes(@DrawableRes int ids, Resources.Theme theme) {
        return setImageDrawable(mView.getResources().getDrawable(ids, theme));
    }

    /**
     * imageView 设置 drawable
     *
     * @param ids
     */
    public BaseViewHolder setImageBackgroundRes(@DrawableRes int ids) {
        return setImageDrawable(mView.getResources().getDrawable(ids));
    }


    /**
     * 隐藏一个视图
     */
    public BaseViewHolder hideView() {
        if (checkNotNull())
            mView.setVisibility(View.GONE);
        return this;
    }

    /**
     * 显示一个视图
     */
    public BaseViewHolder showView() {
        if (checkNotNull())
            mView.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param listener
     * @return
     */
    public BaseViewHolder setClick(View.OnClickListener listener) {
        if (checkNotNull())
            mView.setOnClickListener(listener);
        return this;
    }
}
