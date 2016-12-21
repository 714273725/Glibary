package fast.glibrary.base;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import fast.glibrary.R;
import fast.glibrary.tools.ScreenTools;

/**
 * Created by Administrator on 2016/12/9.
 */

public class BaseDialog {
    public AlertDialog.Builder builder;
    public AlertDialog dialog;
    public BaseDialogOperateListener listener;
    public View layout;

    public void show() {
        if (dialog == null) {
            dialog = builder.create();
            //dialog.setCanceledOnTouchOutside(false);//点击外部是否消失
        }
        //dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        //解决输入法无法弹出问题
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void showFullScreen() {
        if (dialog == null) {
            dialog = builder.create();
            //dialog.setCanceledOnTouchOutside(false);//点击外部是否消失
        }
        dialog.show();
        dialog.setContentView(layout);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.width = ScreenTools.getScreenWidthPx() - 40;
        params.height = ScreenTools.getScreenHeightPx() - ScreenTools.getStatusBarHeight() - 100;
        layout.setLayoutParams(params);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
