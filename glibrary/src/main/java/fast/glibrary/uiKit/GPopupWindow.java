package fast.glibrary.uiKit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import fast.glibrary.R;
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
    PopupWindow.OnDismissListener dismissListener;
    RelativeLayout container;
    boolean hasShadow = true;

    public GPopupWindow(Context context, @LayoutRes int layout) {
        super(context, layout);
    }

    public GPopupWindow(Context context, @LayoutRes int layout, boolean shadow) {
        super(context, layout);
        this.hasShadow = shadow;
    }

    /**
     * 填充一层暗的背景,可以指定显示内容的位置和宽高
     *
     * @param view    popupWindow 要依附的视图
     * @param gravity 显示内容的位置{@link Gravity}
     * @param width   显示内容的宽
     * @param height  显示内容的高
     * @return
     */
    public GPopupWindow showLocation(View view, int gravity, int width, int height) {
        if (container == null) {
            container = new RelativeLayout(getContext());
            container.setOnClickListener(v -> popupWindow.dismiss());
        }
        container.removeAllViews();
        if(hasShadow){
            container.setBackgroundResource(R.color.popbg);
        }else {
            container.setBackgroundResource(R.color.transparent);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,
                height);
        switch (gravity) {
            /*case Gravity.BOTTOM:
            case Gravity.LEFT:
            case Gravity.RIGHT:
            case Gravity.TOP:
            case Gravity.CENTER:
            case Gravity.CENTER_VERTICAL:
            case Gravity.CENTER_HORIZONTAL:
            case Gravity.BOTTOM:*/
            case Gravity.BOTTOM:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                break;
            default:
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                break;
        }
        getLayout().setLayoutParams(params);
        container.addView(getLayout());
        getLayout().setOnClickListener(view1 -> {
            return;
        });
        if (popupWindow == null) {
            popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(
                    android.R.color.transparent));
        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return this;
    }

    /**
     * 填充一层暗的背景,可以指定显示内容的位置，宽高为包含内容
     *
     * @param view    popupWindow 要依附的视图
     * @param gravity 显示内容的位置{@link Gravity}
     * @return
     */
    public GPopupWindow showLocation(View view, int gravity) {
        return showLocation(view, gravity, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 指定显示在某个view下方，不指定偏移和宽高
     *
     * @param view popupWindow 要依附的视图
     * @return
     */
    public GPopupWindow show(View view) {
        return show(view, 0, 0);
    }

    /**
     * 指定显示在某个view下方，仅指定偏移量，宽高为包含内容
     *
     * @param view    popupWindow 要依附的视图
     * @param xoffset x轴偏移量
     * @param yoffset y轴偏移量
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
        if(hasShadow){

        }else {

        }
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, xoffset, yoffset);
        popupWindow.setOnDismissListener(() -> {
            if (dismissListener != null) {
                dismissListener.onDismiss();
            }
        });
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
        return show(view, 0, 0, width, height);
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

    public void addOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.dismissListener = listener;
    }

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
