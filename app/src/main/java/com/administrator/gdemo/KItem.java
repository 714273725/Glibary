package com.administrator.gdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import fast.glibrary.base.GItem;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/28 9:59
 * 修改人：Administrator
 * 修改时间：2016/12/28 9:59
 * 修改备注：
 */
public class KItem extends GItem {
    public static final int AddToShoppingCard = 0;

    public KItem(Context context) {
        super(context);
    }

    public KItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public KItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getLayoutId(int type) {
        int layoutId = R.layout.layout_blank;
        switch (type) {
            case AddToShoppingCard:
                layoutId = R.layout.add_to_shopping_card;
                break;
        }
        return layoutId;
    }

    @Override
    protected int setItemTypeEnumStyle() {
        return R.styleable.KItem_function;
    }

    @Override
    protected int[] setExtendEnumStyle() {
        return R.styleable.KItem;
    }
}
