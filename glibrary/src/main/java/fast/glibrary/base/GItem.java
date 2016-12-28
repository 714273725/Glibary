package fast.glibrary.base;

import fast.glibrary.R;
import fast.glibrary.annotation.SaveState;
import fast.glibrary.uiKit.GViewHolder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/27 13:31
 * 修改人：Administrator
 * 修改时间：2016/12/27 13:31
 * 修改备注：
 */
public abstract class GItem extends LinearLayout {
    @SaveState
    protected int itemType;
    private GViewHolder holder;

    public GItem(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public GItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public GItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);

    }

    @TargetApi(21)
    public GItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, setExtendEnumStyle());
        int type = a.getInt(setItemTypeEnumStyle(), -1);
        if (type != -1) itemType = type;
        if (itemType == -1) itemType = 0;
        a.recycle();
    }

    public GViewHolder getHolder() {
        return holder;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(getLayoutId(itemType), this, true);
        holder = new GViewHolder(view);
    }

    protected abstract int getLayoutId(int type);

    protected
    @StyleableRes
    abstract int setItemTypeEnumStyle();

    protected
    @StyleableRes
    abstract int[] setExtendEnumStyle();
}
