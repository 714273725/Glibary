package fast.glibrary.views.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.security.InvalidParameterException;

import fast.glibrary.tools.L;
import fast.glibrary.views.refresh.utils.Utils;
import fast.glibrary.R;

/**
 * Created by Alexey on 28.01.2016.
 */
public class GRefreshLayout extends SwipeRefreshLayout {

    public GRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GRefreshLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                L.e("dddddd");
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                    L.e("qqqqq");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
