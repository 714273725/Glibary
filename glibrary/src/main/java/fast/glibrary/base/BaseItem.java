package fast.glibrary.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.facebook.drawee.view.SimpleDraweeView;

import fast.glibrary.R;

import java.util.Calendar;

import fast.glibrary.annotation.SaveState;
import fast.glibrary.annotation.helper.StateBinder;
import fast.glibrary.annotation.helper.StateSaver;

/**
 * Created by XY on 2016-08-08.
 */
public abstract class BaseItem extends RelativeLayout {

    private SparseArray<View> viewList;
    @SaveState
    protected int itemType;
    @SaveState
    protected int itemIcon;
    @SaveState
    protected int itemColor;
    @SaveState
    protected int itemChildColor;
    @SaveState
    protected int itemBg;
    @SaveState
    protected int itemRes;
    @SaveState
    protected int itemNum;
    @SaveState
    protected int itemCount;
    @SaveState
    protected int itemVisible;
    @SaveState
    protected int itemChildVisible;
    @SaveState
    protected int itemIndex;
    @SaveState
    protected boolean itemBool;
    @SaveState
    protected boolean itemCheck;
    @SaveState
    protected float itemFloat;
    @SaveState
    protected String itemName;
    @SaveState
    protected String itemHint;
    @SaveState
    protected String itemTitle;
    @SaveState
    protected String itemContent;
    @SaveState
    protected String itemDetail;
    @SaveState
    protected String itemDescription;

    @SaveState
    protected int itemInputType;
    @SaveState
    protected int itemMin;
    @SaveState
    protected int itemMax;
    @SaveState
    protected int itemMinLines;
    @SaveState
    protected int itemMaxLines;

    @SaveState
    private int[] date = new int[]{0, 0, 0};
    @SaveState
    private int[] time = new int[]{0, 0};

    protected OnViewSenseListener onViewSenseListener;

    @SaveState
    protected int layoutId = R.layout.layout_blank;

    public BaseItem(Context context) {
        super(context, null);
    }

    public BaseItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewList = new SparseArray<>();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseItem);

        itemNum = a.getInt(R.styleable.BaseItem_gNum, 0);
        itemCount = a.getInt(R.styleable.BaseItem_gCount, 0);
        itemIndex = a.getInt(R.styleable.BaseItem_gIndex, 0);
        itemBool = a.getBoolean(R.styleable.BaseItem_gBool, false);
        itemCheck = a.getBoolean(R.styleable.BaseItem_gCheck, false);
        itemVisible = a.getInt(R.styleable.BaseItem_gItemVisible, GONE);
        itemChildVisible = a.getInt(R.styleable.BaseItem_gItemChildVisible, VISIBLE);
        itemFloat = a.getFloat(R.styleable.BaseItem_gFloat, 0);
        itemIcon = a.getResourceId(R.styleable.BaseItem_gIcon, 0);
        itemColor = a.getResourceId(R.styleable.BaseItem_gColor, 0);
        itemChildColor = a.getResourceId(R.styleable.BaseItem_gChildColor, 0);
        itemBg = a.getResourceId(R.styleable.BaseItem_gBg, 0);
        itemRes = a.getResourceId(R.styleable.BaseItem_gRes, 0);
        itemName = a.getString(R.styleable.BaseItem_gName);
        itemHint = a.getString(R.styleable.BaseItem_gHint);
        itemTitle = a.getString(R.styleable.BaseItem_gTitle);
        itemContent = a.getString(R.styleable.BaseItem_gContent);
        itemDetail = a.getString(R.styleable.BaseItem_gDetail);
        itemDescription = a.getString(R.styleable.BaseItem_gDescription);
        itemInputType = a.getInt(R.styleable.BaseItem_android_inputType, 0);
        itemMinLines = a.getInt(R.styleable.BaseItem_android_maxLines, 0);
        itemMaxLines = a.getInt(R.styleable.BaseItem_android_maxLines, 0);
        itemMin = a.getInt(R.styleable.BaseItem_gMin, -1);
        itemMax = a.getInt(R.styleable.BaseItem_gMax, -1);
        itemName = itemName == null ? "" : itemName;
        itemHint = itemHint == null ? "" : itemHint;
        itemTitle = itemTitle == null ? "" : itemTitle;
        itemContent = itemContent == null ? "" : itemContent;
        itemDetail = itemDetail == null ? "" : itemDetail;
        itemDescription = itemDescription == null ? "" : itemDescription;
        itemType = a.getInt(R.styleable.BaseItem_gType, -1);
        if (setExtendEnumStyle() != R.styleable.BaseItem && setItemTypeEnumStyle() != R.styleable.BaseItem_gType) {
            a.recycle();
            a = context.obtainStyledAttributes(attrs, setExtendEnumStyle());
            int type = a.getInt(setItemTypeEnumStyle(), -1);
            if (type != -1) itemType = type;
        }
        if (itemType == -1) itemType = 0;
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(getLayoutId(itemType), this, true);
        setViews(itemType);
    }

    public int getItemNum() {
        return itemNum;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public float getItemFloat() {
        return itemFloat;
    }

    public <T extends View> T getView(int viewId) {
        View view = viewList.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            viewList.put(viewId, view);
        }
        return (T) view;
    }

    public String getText(int textViewId) {
        TextView view = getView(textViewId);
        if (view != null) {
            return view.getText().toString();
        } else {
            return null;
        }
    }

    public BaseItem setFormat(int viewId, int formatRes, Object... objects) {
        setTextForView(viewId, String.format(getContext().getString(formatRes), objects));
        return this;
    }

    public BaseItem setText(int viewId, @StringRes int textRes) {
        setText(viewId, getContext().getString(textRes));
        return this;
    }

    public BaseItem setText(int viewId, Object text) {
        String string;
        if (text == null) {
            string = "";
        } else if (text instanceof String) {
            string = (String) text;
        } else {
            string = String.valueOf(text);
        }
        setTextForView(viewId, string);
        return this;
    }


    private BaseItem setTextForView(int viewId, String text) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
        }
        return this;
    }

    public BaseItem setTextColor(int viewId, @ColorRes int textColorRes) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(getResources().getColor(textColorRes));
            }
        }
        return this;
    }

    public BaseItem setImageUrl(int viewId, String url) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof SimpleDraweeView) {
                ((SimpleDraweeView) view).setImageURI(Uri.parse(url));
            } else if (view instanceof ImageView) {
                ((ImageView) view).setImageURI(Uri.parse(url));
            }
        }
        return this;
    }

    public BaseItem setImageURI(int viewId, Uri uri) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof SimpleDraweeView) {
                ((SimpleDraweeView) view).setImageURI(uri);
            } else if (view instanceof ImageView) {
                ((ImageView) view).setImageURI(uri);
            }
        }
        return this;
    }

    public BaseItem setImageBitmap(int viewId, Bitmap bitmap) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }
        return this;
    }

    public BaseItem setImageRes(int viewId, @DrawableRes int drawableRes) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            }
        }
        return this;
    }

    public BaseItem setRippleComplete(int viewId, RippleView.OnRippleCompleteListener completeListener) {
        View view = getView(viewId);
        if (view != null) {
            if (view instanceof RippleView) {
                ((RippleView) view).setOnRippleCompleteListener(completeListener);
            }
        }
        return this;
    }

    public BaseItem setClick(int viewId, OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public BaseItem setLongClick(int viewId, OnLongClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }

    public BaseItem setEnable(int viewId, boolean enable) {
        View view = getView(viewId);
        if (view != null) {
            view.setEnabled(enable);
        }
        return this;
    }

    public BaseItem setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public Calendar getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0], date[1], date[2], time[0], time[1]);
        return calendar;
    }


    public void setOnViewSenseListener(OnViewSenseListener onViewSenseListener) {
        this.onViewSenseListener = onViewSenseListener;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public boolean isItemCheck() {
        return itemCheck;
    }

    public void setItemCheck(boolean itemCheck) {
        this.itemCheck = itemCheck;
    }

    public int getItemMin() {
        return itemMin;
    }

    public void setItemMin(int itemMin) {
        this.itemMin = itemMin;
    }

    public int getItemMax() {
        return itemMax;
    }

    public void setItemMax(int itemMax) {
        this.itemMax = itemMax;
    }

    public String getItemHint() {
        return itemHint;
    }

    public void setItemHint(String itemHint) {
        this.itemHint = itemHint;
    }

    public void setItemFloat(float itemFloat) {
        this.itemFloat = itemFloat;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(StateSaver.INSTANCE_STATE, super.onSaveInstanceState());
        StateSaver.saveStatue(this, bundle);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            StateBinder.bindState(this, bundle);
            state = bundle.getParcelable(StateSaver.INSTANCE_STATE);
        }
        super.onRestoreInstanceState(state);
    }

    protected abstract int getLayoutId(int type);

    public abstract void setViews(int type);

    protected
    @StyleableRes
    int setItemTypeEnumStyle() {
        return R.styleable.BaseItem_gType;
    }

    protected
    @StyleableRes
    int[] setExtendEnumStyle() {
        return R.styleable.BaseItem;
    }

    public interface OnViewSenseListener {
        void onClick(View view, Object object);
    }

}
