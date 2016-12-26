package fast.glibrary.base.popwindow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import fast.glibrary.base.BaseDialogOperateListener;
import fast.glibrary.uiKit.GViewHolder;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/26 14:37
 * 修改人：Administrator
 * 修改时间：2016/12/26 14:37
 * 修改备注：
 */
public class BasePopWindow {
    public PopupWindow popupWindow;
    public BaseDialogOperateListener mOperateListener;
    Context mContext;
    View layout;

    public GViewHolder getHolder() {
        return holder;
    }

    GViewHolder holder;
    public View getLayout() {
        return layout;
    }



    public Context getContext() {
        return mContext;
    }



    public BasePopWindow(Context context, @LayoutRes int layout) {
        this.mContext = context;
        this.layout = LayoutInflater.from(context).inflate(layout, null);
        this.holder = new GViewHolder(this.layout);
    }
}
