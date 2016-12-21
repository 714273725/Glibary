package fast.glibrary.uiKit;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;

import fast.glibrary.base.BaseDialog;
import fast.glibrary.base.BaseDialogOperateListener;

/**
 * Created by Administrator on 2016/12/16.
 */

public class OperateDialog extends BaseDialog {
    /**
     * 自动给设置了id的子View绑定点击操作
     *
     * @param context
     * @param layoutRes
     * @param operateListener
     * @param ids             控件的id
     */
    public OperateDialog(Activity context, @LayoutRes int layoutRes, BaseDialogOperateListener operateListener) {
        this(context, layoutRes);
        this.listener = operateListener;
        if (layout instanceof ViewGroup)
            for (int i = 0; i < ((ViewGroup) layout).getChildCount(); i++) {
                View childrenView = ((ViewGroup) layout).getChildAt(i);
                if (childrenView.getId() != -1) {
                    childrenView.setOnClickListener(view -> {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (listener != null) {
                            listener.onItemClick(view);
                        }
                    });
                }
            }
    }

    /**
     * 自动绑定单个控件的点击事件
     *
     * @param context
     * @param layoutRes
     * @param operateListener
     * @param ids             控件的id
     */
    public OperateDialog(Activity context, @LayoutRes int layoutRes, BaseDialogOperateListener operateListener, int ids) {
        this(context, layoutRes);
        this.listener = operateListener;

        layout.findViewById(ids).setOnClickListener(v -> {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (listener != null) {
                listener.onItemClick(v);
            }
        });
    }

    /**
     * 自动绑定多个控件的点击事件，需要指定id
     *
     * @param context
     * @param layoutRes       对话框布局
     * @param operateListener
     * @param id              控件的ids
     */
    public OperateDialog(Activity context, @LayoutRes int layoutRes, BaseDialogOperateListener operateListener, int[] id) {
        this(context, layoutRes);
        this.listener = operateListener;
        Integer[] ids = new Integer[id.length];
        for (int i = 0; i < id.length; i++) {
            ids[i] = id[i];
        }
        if (ids != null && operateListener != null) {
            Stream.of(ids).forEach(integer -> {
                View view = layout.findViewById(integer.intValue());
                if (view != null) {
                    view.setOnClickListener(view1 -> {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (listener != null) {
                            listener.onItemClick(view1);
                        }
                    });
                }
            });
        }
    }

    /**
     * 不自动绑定点击事件
     *
     * @param context
     * @param layoutRes
     */
    public OperateDialog(Activity context, @LayoutRes int layoutRes) {
        layout = LayoutInflater.from(context).inflate(layoutRes, null);
        builder = new AlertDialog.Builder(context);
    }

    /**
     * 居中TextView的dialog
     *
     * @param context
     */
    public OperateDialog(Activity context, String title) {
        layout = new TextView(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (!TextUtils.isEmpty(title)) {
            ((TextView) layout).setText(title);
        }
        ((TextView) layout).setGravity(Gravity.CENTER);
        builder = new AlertDialog.Builder(context);
    }


    /**
     * 外部提供视图的View的dialog
     *
     * @param context
     */
    public OperateDialog(Activity context, View view) {
        this.layout = view;
        builder = new AlertDialog.Builder(context);
    }

}
