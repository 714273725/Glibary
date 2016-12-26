package fast.glibrary.uiKit;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import fast.glibrary.base.BaseDialogOperateListener;
import fast.glibrary.base.popwindow.BasePopWindow;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/26 14:48
 * 修改人：Administrator
 * 修改时间：2016/12/26 14:48
 * 修改备注：
 */
public class GPopupWindow extends BasePopWindow {
    public GPopupWindow(Context context, @LayoutRes int layout) {
        super(context, layout);
    }

    /**
     * 指定显示在某个view下方，不指定偏移和宽高
     *
     * @param view
     * @return
     */
    public GPopupWindow show(View view) {
        return show(view, 0, 0);
    }

    /**
     * 指定显示在某个view下方，仅指定偏移量
     *
     * @param view
     * @param xoffset
     * @param yoffset
     * @return
     */
    public GPopupWindow show(View view, int xoffset, int yoffset) {
        return show(view, xoffset, yoffset, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 指定显示在某个view下方，指定偏移量和宽高
     *
     * @param view
     * @param xoffset
     * @param yoffset
     * @param width
     * @param height
     * @return
     */
    public GPopupWindow show(View view, int xoffset, int yoffset, int width, int height) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getLayout(), width,
                    height, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, xoffset, yoffset);
        return this;
    }

    /**
     * 指定显示在某个view下方，仅指定宽高
     *
     * @param width
     * @param height
     * @param view
     * @return
     */
    public GPopupWindow show(int width, int height, View view) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getLayout(), width,
                    height, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, 0, 0);
        return this;
    }

    public GPopupWindow setOperateListener(BaseDialogOperateListener listener, int[] ids) {
        this.mOperateListener = listener;
        for (int i = 0; i < ids.length; i++) {
            View view = getLayout().findViewById(ids[i]);
            view.setOnClickListener(view1 -> {
                if (mOperateListener != null) {
                    mOperateListener.onItemClick(view1);
                }
                popupWindow.dismiss();
            });
        }
        return this;
    }

    /**
     * 指定显示在某个view下方，指定偏移量，充满父布局
     * @param view
     * @param xoffset
     * @param yoffset
     * @return
     */
    public GPopupWindow showFullScreen(View view, int xoffset, int yoffset) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getLayout(), ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, xoffset, yoffset);
        return this;
    }

    /**
     * 不指定显示在某个view下方，指定宽高
     * @param parent
     * @param gravity
     * @param width
     * @param height
     * @return
     */
    public GPopupWindow showLocation(View parent, int gravity, int width, int height) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getLayout(), width,
                    height, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, gravity, 0, 0);
        return this;
    }

    /**
     * 不指定显示在某个view下方，不指定宽高
     * @param parent
     * @param gravity
     * @return
     */
    public GPopupWindow showLocation(View parent, int gravity) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getLayout(), ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, gravity, 0, 0);
        return this;
    }
    /**
     * 不指定显示在某个view下方，指定宽高充满父布局
     * @param parent
     * @param gravity
     * @return
     */
    public GPopupWindow showFullScreenLocation(View parent, int gravity) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getLayout(), ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, gravity, 0, 0);
        return this;
    }
}
