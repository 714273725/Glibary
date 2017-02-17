package fast.glibrary.views.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.net.Socket;

import fast.glibrary.views.refresh.utils.DensityUtil;
import fast.glibrary.R;

/**
 * Created by Alexey on 28.01.2016.
 */
public class GRefreshLayout extends RelativeLayout {

    public GRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GRefreshLayout(Context context) {
        super(context);
    }

    public GRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(23)
    public GRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
