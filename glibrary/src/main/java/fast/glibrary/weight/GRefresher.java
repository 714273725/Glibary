package fast.glibrary.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import fast.glibrary.R;
import fast.glibrary.tools.L;
import fast.glibrary.tools.ScreenTools;
import fast.glibrary.views.refresh.utils.ScrollUtils;
import fast.glibrary.weight.uiKit.GRStateView;
import fast.glibrary.weight.uiKit.ScrollState;
// TODO: 2017/2/25 上拉加载尚未完工

/**
 * Created by Administrator on 2017/2/23.
 * Description:下拉刷新,上拉加载ViewGroup
 * Function:支持越界模式
 */
public class GRefresher extends RelativeLayout {
    public static final int BoundNormal = 0;
    public static final int BoundTopOnly = 1;
    public static final int BoundBottomOnly = 2;
    public static final int BoundBoth = 3;
    Handler mHandler;
    RelativeLayout mRefreshHeader;
    RelativeLayout mRefreshFooter;
    View mChildView;
    ScrollState mState = ScrollState.Normal;
    float mDownX;
    float mDownY;
    float mLastX;
    float mLastY;
    //往上回弹任务
    UpwardSlide mUpwardSlideTask;
    //
    DownSlide mDownSlideTask;
    //
    ToRefresh mRefresh;
    //
    ToLoadMore mLoadMore;
    //刷新头的高度
    int mRefreshHeaderHeight = ScreenTools.dp2px(80);
    //刷新头的高度
    int mLoadMoreFooterHeight = ScreenTools.dp2px(80);
    //回弹速度 1秒内回弹
    int mSpeed = 1;
    float mUpwardSlideSpeed;
    float mDownwardSpeed;
    private PullListener mPullListener;
    float mMaxSpeed = 0;
    int mBoundsMode;

    /**
     * 设置刷新头显示的视图工具
     *
     * @param headerView {@link GRStateView}
     */
    public void setHeaderView(GRStateView headerView) {
        this.mHeaderView = headerView;
        post(() -> initHeadView(mHeaderView.obtainHoldingView()));
    }

    GRStateView mHeaderView;

    /**
     * 设置刷新底显示的视图工具
     *
     * @param headerView {@link GRStateView}
     */
    public void setFooterView(GRStateView footerView) {
        this.mFooterView = footerView;
        post(() -> initFooterView(mFooterView.obtainNormalView()));
    }

    GRStateView mFooterView;

    public GRefresher(Context context) {
        this(context, null, 0);
    }

    public GRefresher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setPullListener(PullListener pullListener) {
        this.mPullListener = pullListener;
    }

    public GRefresher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GRefresher, defStyleAttr, 0);
        mRefreshHeaderHeight = a.getDimensionPixelSize(R.styleable.GRefresher_headHeight, 80);
        mLoadMoreFooterHeight = a.getDimensionPixelSize(R.styleable.GRefresher_footerHeight, 80);
        mHandler = new Handler(Looper.getMainLooper());
        mPullListener = new PullListener() {
            @Override
            public boolean canLoadMore() {
                return false;
            }

            @Override
            public void refresh() {

            }

            @Override
            public void loadMore() {

            }
        };
        mBoundsMode = a.getInt(R.styleable.GRefresher_boundsMode, 0);
        a.recycle();
    }

    /**
     * 刷新完成后回调此方法
     */
    public void endRefreshing() {
        mState = ScrollState.Normal;
        initHeadView(mHeaderView.obtainNormalView());
        postUpwardTask();
    }

    public void endLoading() {
        mState = ScrollState.Normal;
        initFooterView(mFooterView.obtainNormalView());
        postDownwardTask();
    }


    /**
     * View和Window绑定时就会调用这个函数
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mRefreshHeader == null) {
            mRefreshHeader = new RelativeLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenTools.getScreenHeightPx() / 2);
            layoutParams.addRule(ALIGN_PARENT_TOP);
            layoutParams.addRule(CENTER_VERTICAL);
            this.addView(mRefreshHeader, layoutParams);
        }
        if (mRefreshFooter == null) {
            mRefreshFooter = new RelativeLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenTools.getScreenHeightPx() / 2);
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(CENTER_VERTICAL);
            this.addView(mRefreshFooter, layoutParams);
        }
        mChildView = getChildAt(0);
    }

    private void initFooterView(View view) {
        LayoutParams tParams = new LayoutParams(LayoutParams.MATCH_PARENT, mLoadMoreFooterHeight);
        tParams.addRule(ALIGN_PARENT_BOTTOM);
        mRefreshFooter.removeAllViews();
        mRefreshFooter.addView(view, tParams);
        mRefreshFooter.setPadding(0, 0, 0, -mLoadMoreFooterHeight);
    }

    /**
     * 设置当前刷新头显示的视图
     *
     * @param view 显示的视图
     */
    private void initHeadView(View view) {
        LayoutParams tParams = new LayoutParams(LayoutParams.MATCH_PARENT, mRefreshHeaderHeight);
        mRefreshHeader.removeAllViews();
        mRefreshHeader.addView(view, tParams);
        mRefreshHeader.setPadding(0, -mRefreshHeaderHeight, 0, 0);
    }


    /**
     * 返回true,事件不再下发，交由本层处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mLastY = ev.getY();
                mLastX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mDownX;
                float dy = ev.getY() - mDownY;
                return interceptEvent(dx, dy, ev);
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                float dx1 = ev.getX() - mDownX;
                float dy1 = ev.getY() - mDownY;
                if (dx1 < 10 && dy1 < 10) {
                    return super.onInterceptTouchEvent(ev);
                } else {
                    return true;
                }

        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 判断是否要中断事件传递
     *
     * @param dx 触摸事件在x轴上的偏移
     * @param dy 触摸事件在y轴上的偏移
     * @param ev {@link MotionEvent}
     * @return
     */
    private boolean interceptEvent(float dx, float dy, MotionEvent ev) {
        //如果abs（dy）大于abs（dx），说明在y轴上移动速度比在x轴上快，可认为用户上拉或下拉
        if (Math.abs(dx) <= Math.abs(dy) && (dx != 0 || dy != 0)) {//滑动允许最大角度为45度，单点事件不作处理
            return needInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 触摸事件偏移是否向上
     *
     * @param e
     * @return
     */
    private boolean isUpEvent(MotionEvent e) {
        float dy = e.getY() - mLastY;
        float dx = e.getX() - mLastX;
        return dy < 0 && Math.abs(dy) > Math.abs(dx);
    }

    /**
     * 触摸事件偏移是否向下
     *
     * @param e
     * @return
     */
    private boolean isDownEvent(MotionEvent e) {
        float dy = e.getY() - mLastY;
        float dx = e.getX() - mLastX;
        return dy > 0 && Math.abs(dy) > Math.abs(dx);
    }

    /**
     * 判断是否由Grefresher来处理触摸事件
     *
     * @return
     */
    private boolean needInterceptTouchEvent(MotionEvent dy) {
        if (!(mState == ScrollState.Normal)) {
            return true;
        } else {
            //列表不足一屏时，下拉事件或能上拉加载更多时返回true处理事件
            if (!ScrollUtils.canChildScrollDown(mChildView) && !ScrollUtils.canChildScrollDown(mChildView)) {
                return isDownEvent(dy) || canLoadMore();
                //列表无法下拉时下拉，返回true处理事件
            } else if (!ScrollUtils.canChildScrollDown(mChildView)) {
                return isDownEvent(dy);
                //列表无法上拉时上拉并且能上拉加载更多时，返回true处理事件
            } else if (!ScrollUtils.canChildScrollUp(mChildView)) {
                return isUpEvent(dy) && canLoadMore();
            } else {
                if (mRefreshHeader.getChildAt(0).getTranslationY() != 0) {
                    mRefreshHeader.getChildAt(0).setTranslationY(0);
                }
                initTop();
            }
        }
        return false;
    }

    /**
     * 是否需要处理上拉事件
     *
     * @return
     */
    private boolean canLoadMore() {
        return false;
        //return mPullListener == null ? false : mPullListener.canLoadMore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = e.getY() - mDownY;
                boolean temp = dispatchTouchEvent(e, dy);
                mLastY = e.getY();
                mLastX = e.getX();
                return temp || super.onTouchEvent(e);
            case MotionEvent.ACTION_UP:
                if (mState == ScrollState.UpwardResilience) {
                    postUpwardTask();
                    return true;
                }
                if (mState == ScrollState.PullingDown) {
                    if (mChildView.getTranslationY() < mRefreshHeaderHeight) {
                        postUpwardTask();
                    } else {
                        postToRefreshTask();
                    }
                    return true;
                }
                if (mState == ScrollState.PullingUp) {
                    postToLoadMoreTask();
                    return true;
                }
                if (mState == ScrollState.DownwardResilience) {
                    postDownwardTask();
                    return true;
                }
                /*if (mChildView.getTranslationY() > 0) {
                    if (mChildView.getTranslationY() > mRefreshHeaderHeight) {
                        mState = ScrollState.Refreshing;
                        removeAllCallbacks();
                        postToRefreshTask();
                    }
                } else {
                    if (-mChildView.getTranslationY() > mLoadMoreFooterHeight) {
                        mState = ScrollState.LoadingMore;
                        removeAllCallbacks();
                        toLoadMore();
                    }
                }*/
                break;
        }
        return super.onTouchEvent(e);
    }

    private void postDownwardTask() {
        removeAllCallbacks();
        mDownwardSpeed = mChildView.getTranslationY() / 5f;
        mHandler.post(mDownSlideTask = new DownSlide());
    }

    private void postToLoadMoreTask() {
        removeAllCallbacks();
        mDownwardSpeed = mChildView.getTranslationY() / 5f;
        mHandler.post(mLoadMore = new ToLoadMore());
    }

    private void postUpwardTask() {
        removeAllCallbacks();
        mUpwardSlideSpeed = mChildView.getTranslationY() / 5f;
        mHandler.post(mUpwardSlideTask = new UpwardSlide());
    }

    private void postToRefreshTask() {
        removeAllCallbacks();
        mUpwardSlideSpeed = mChildView.getTranslationY() / 5f;
        mHandler.post(mRefresh = new ToRefresh());
    }

    private void toLoadMore() {
        mDownwardSpeed = -mChildView.getTranslationY() / 5f;
        mHandler.post(mLoadMore = new ToLoadMore());
    }


    private boolean allowBottomBound() {
        return mBoundsMode == BoundBoth || mBoundsMode == BoundBottomOnly;
    }

    //处理触摸事件
    private boolean dispatchTouchEvent(MotionEvent e, float dy) {
        if (mState == ScrollState.Normal) {
            return stateNormal(e, dy);
        }
        //状态-》往下拉超过阈值 -》消费掉事件，下层不处理事件
        if (mState == ScrollState.PullingDown) {
            return statePullingDown(e, dy);
        }
        //临界状态-》往下拉不超过阈值 -》消费掉事件，下层不处理事件
        if (mState == ScrollState.UpwardResilience) {
            return stateUpwardResilience(e, dy);
        }
        if (mState == ScrollState.Refreshing) {
            return stateRefreshing(e);
        }

        if (mState == ScrollState.LoadingMore) {
            L.e("--->state LoadingMore");
            mState = ScrollState.DownwardResilience;
            translationBottomY(mChildView.getTranslationY() + getBottomBoundary(dy));
            return true;
        }
        if (mState == ScrollState.DownwardResilience) {
            if (mChildView.getTranslationY() > 0) {
                L.e("state DownwardResilience --->state Normal" + getBottomBoundary(dy));
                mState = ScrollState.Normal;
            } else {
                if (dy < -mLoadMoreFooterHeight) {
                    mState = ScrollState.PullingUp;
                    return true;
                }
                translationBottomY(getBottomBoundary(dy));
                return true;
            }
        }
        if (mState == ScrollState.PullingUp) {
            if (dy > 0) {
                mState = ScrollState.Normal;
            } else {
                translationBottomY(getBottomBoundary(dy));
            }
        }
        return super.onTouchEvent(e);
    }

    private boolean stateRefreshing(MotionEvent e) {
        L.e("--->state Refreshing");
        if (isDownEvent(e)) {
            L.e("state Refreshing --->state PullingDown");
            mState = ScrollState.PullingDown;
            return true;
        } else if (isUpEvent(e)) {
            L.e("state Refreshing --->state UpwardResilience");
            mState = ScrollState.UpwardResilience;
            return true;
        }
        //translationTopY(mChildView.getTranslationY() + getTopBoundary(false, e, dy));
        return true;
    }

    /**
     * 处理临界状态触摸事件
     * <p>
     * 有三种状态与临界态可以相互进入
     * 1.正常态《-》临界态 2.越界态《-》临界态 2.刷新态《-》临界态
     * <p>
     * </p>
     *
     * @param e  {@link MotionEvent}
     * @param dy {垂直方向偏移值}
     * @return
     */
    private boolean stateUpwardResilience(MotionEvent e, float dy) {
        L.i("---> state UpwardResilience");
        //临界态时，一旦mChildView.getTranslationY() < 0，回到正常态，刷新头隐藏
        if (mChildView.getTranslationY() < 0) {
            L.e("state UpwardResilience---> state Normal");
            // TODO: 2017/2/25 这里是否要重置 TranslationY = 0？
            mState = ScrollState.Normal;
            return true;
            //
        } else {
            //临界态时，一旦mChildView.getTranslationY() > mRefreshHeaderHeight，回到越界态
            if (mChildView.getTranslationY() > mRefreshHeaderHeight) {
                // TODO: 2017/2/25 这里是否要重置 TranslationY = mRefreshHeaderHeight？
                mState = ScrollState.PullingDown;
                return true;
            }
            //临界态，上拉事件
            if (isUpEvent(e)) {
                float x = getTopBoundary(true, e, dy);
                float y = mRefreshHeaderHeight + x;
                if (dy < 0) {
                    if (Math.abs(dy) < mRefreshHeaderHeight / 3) {
                        if (mChildView.getTranslationY() > mRefreshHeaderHeight / 2) {
                            translationTopY(y);
                            if (y <= 0) {
                                mState = ScrollState.Normal;
                                return mChildView.dispatchTouchEvent(e);
                            }
                            return true;
                        } else {
                            translationTopY(0);
                            mState = ScrollState.Normal;
                            return mChildView.dispatchTouchEvent(e);
                        }
                    } else {
                        translationTopY(y);
                        if (y <= 0) {
                            mState = ScrollState.Normal;
                            return mChildView.dispatchTouchEvent(e);
                        }
                    }
                    return true;

                } else {
                    if (dy > 0) {
                        L.e("PullingDown  Math.abs(dy - mChildView.getTranslationY()) < mRefreshHeaderHeight / 2" + dy);
                        translationTopY(getTopBoundary(false, e, x));
                    } else {
                        mState = ScrollState.Normal;
                        translationTopY(0);
                            /*L.e("PullingDown  !Math.abs(dy - mChildView.getTranslationY()) < mRefreshHeaderHeight / 2" + (mRefreshHeaderHeight + dy));
                            translationTopY(getTopBoundary(false, e, y));*/
                    }
                }
                return true;
            } else if (isDownEvent(e)) {
                L.e("state UpwardResilience---> NotUpEvent");
                translationTopY(getTopBoundary(true, e, dy));
                return true;
            }
        }
        return true;
    }

    private boolean statePullingDown(MotionEvent e, float dy) {
        L.e("state PullingDown" + isDownEvent(e) + "==" + isUpEvent(e));
        if (mChildView.getTranslationY() < 0) {
            mState = ScrollState.Normal;
        } else {
            if (mChildView.getTranslationY() < mRefreshHeaderHeight) {
                L.e("state PullingDown---> state UpwardResilience");
                mState = ScrollState.UpwardResilience;
                return true;
            }
            if (isDownEvent(e)) {
                if (dy < mRefreshHeaderHeight) {
                    L.e("PullingDown  dy < mRefreshHeaderHeight" + (mRefreshHeaderHeight + dy));
                    translationTopY(getTopBoundary(false, e, mRefreshHeaderHeight + dy));
                    return true;
                } else {
                    if (Math.abs(dy - mChildView.getTranslationY()) < mRefreshHeaderHeight / 1.5) {
                        L.e("PullingDown  Math.abs(dy - mChildView.getTranslationY()) < mRefreshHeaderHeight / 2" + dy);
                        translationTopY(getTopBoundary(false, e, dy));
                    } else {
                        L.e("PullingDown  !Math.abs(dy - mChildView.getTranslationY()) < mRefreshHeaderHeight / 2" + (mRefreshHeaderHeight + dy));
                        translationTopY(getTopBoundary(false, e, mRefreshHeaderHeight + dy));
                    }
                    return true;
                }
            } else {
                if (mChildView.getTranslationY() < mRefreshHeaderHeight) {
                    L.e("state PullingDown---> state UpwardResilience");
                    mState = ScrollState.UpwardResilience;
                    return true;
                }
                translationTopY(getTopBoundary(true, e, dy));
                /*if (Math.abs(dy) < mRefreshHeaderHeight) {
                    translationTopY(getTopBoundary(true, e, dy));
                }*/
               /* if (Math.abs(dy) < mRefreshHeaderHeight) {
                    translationTopY(getTopBoundary(true, e, dy));
                } else {
                    if ((Math.abs(dy) - mChildView.getTranslationY()) < mRefreshHeaderHeight / 2) {
                        translationTopY(getTopBoundary(true, e, dy));
                    } else {
                        translationTopY(mRefreshHeaderHeight + getTopBoundary(true, e, dy));
                    }
                }*/
            }
        }
        return true;
    }

    /**
     * 当视图是正常状态时，处理触摸事件的逻辑
     *
     * @param e
     * @param dy
     * @return
     */
    private boolean stateNormal(MotionEvent e, float dy) {
        L.i("-->state Normal");
        //下拉事件
        if (isDownEvent(e)) {
            //子视图还能往下滑，不触发下拉事件，事件传递给子控件
            if (ScrollUtils.canChildScrollDown(mChildView)) {
                reset();
                mDownY = e.getY();
                return mChildView.onTouchEvent(e);
                //子视图不能往下滑动，此时控件进入临界状态，刷新头开始显示出来
            } else {
                L.i("state Normal-->state UpwardResilience");
                translationTopY(getTopBoundary(false, e, dy));
                mState = ScrollState.UpwardResilience;
                return true;
            }
        }

        if (isUpEvent(e)) {
            if (ScrollUtils.canChildScrollUp(mChildView)) {
                reset();
                mDownY = e.getY();
                return mChildView.dispatchTouchEvent(e);
            } else if (canLoadMore()) {
                translationBottomY(getBottomBoundary(dy));
                mState = ScrollState.DownwardResilience;
                return true;
            }
        }
        return true;
    }

    /**
     * 上拉边界
     *
     * @param dy 负数
     * @return
     */
    private float getBottomBoundary(float dy) {
        if (!canLoadMore()) {
            return 0;
        }
        float temp = dy;
        if (Math.abs(dy) > ScreenTools.getScreenHeightPx() / 2) {
            temp = -ScreenTools.getScreenHeightPx() / 2;
        }
        //bottom 不能越界的情况下，如果dy < -mLoadMoreFooterHeight 直接加载,状态改为加载中
        if ((mBoundsMode == BoundNormal || mBoundsMode == BoundBottomOnly) && dy < -mLoadMoreFooterHeight) {
            if (mPullListener != null) {
                mPullListener.loadMore();
            }
            mState = ScrollState.LoadingMore;
            temp = -mLoadMoreFooterHeight;
            L.e("state -> position to LoadMore :" + temp);
            //bottom 能越界的情况下，如果dy < -mLoadMoreFooterHeight 状态改为上拉中
        } else if (dy < -mLoadMoreFooterHeight) {
            L.e("state -> position to PullingUp :" + temp);
            mState = ScrollState.PullingUp;
        }
        L.e("getBottomBoundary" + temp);
        return temp;
    }

    private void reset() {
        initTop();
        initBottom();
        initChild();
    }

    private void initChild() {
        if (mChildView.getTranslationY() != 0) {
            mChildView.setTranslationY(0);
        }
    }

    private void initBottom() {
        if (mRefreshFooter.getChildAt(0).getTranslationY() != 0) {
            mRefreshFooter.getChildAt(0).setTranslationY(0);
        }
    }

    /**
     * 判断下拉时的边界
     *
     * @param dy
     * @return
     */
    private float getTopBoundary(boolean initDy, MotionEvent e, float dy) {
        float temp = dy;
        L.e("top-dy" + dy);
        if (dy > ScreenTools.getScreenHeightPx() / 3) {
            temp = ScreenTools.getScreenHeightPx() / 3;
        }
        //top 不能越界的情况下，如果dy > mRefreshHeaderHeight 直接刷新,状态改为刷新中
        if ((mBoundsMode == BoundNormal || mBoundsMode == BoundBottomOnly) && dy > mRefreshHeaderHeight) {
            if (mPullListener != null) {
                mPullListener.refresh();
            }
            mState = ScrollState.Refreshing;
            temp = mRefreshHeaderHeight;
            //top 能越界的情况下，如果dy > mRefreshHeaderHeight 状态改为下拉中
        }
        L.e("top" + temp);
        return temp;
    }

    private void initTop() {
        if (mRefreshHeader.getChildAt(0).getTranslationY() != 0) {
            translationTopY(0);
        }
    }

    private void removeAllCallbacks() {
        L.e("removeAllCallbacks");
        mHandler.removeCallbacks(mUpwardSlideTask);
        mHandler.removeCallbacks(mDownSlideTask);
        mHandler.removeCallbacks(mRefresh);
        mHandler.removeCallbacks(mLoadMore);
    }


    public class DownSlide implements Runnable {
        @Override
        public void run() {
            float position = mChildView.getTranslationY() + mDownwardSpeed;
            L.e(position + "向下" + mDownwardSpeed);
            if (position >= 0) {
                mMaxSpeed = 0;
                translationBottomY(0);
                mState = ScrollState.Normal;
                removeAllCallbacks();
            } else {
                translationBottomY(position);
                mHandler.postDelayed(mDownSlideTask = new DownSlide(), 30 / mSpeed);
            }
        }
    }

    public class ToRefresh implements Runnable {

        @Override
        public void run() {
            float position = mChildView.getTranslationY() - mUpwardSlideSpeed;
            L.e(position + "ToRefresh" + mUpwardSlideSpeed);
            if (position <= mRefreshHeaderHeight) {
                mMaxSpeed = 0;
                mState = ScrollState.Refreshing;
                translationTopY(mRefreshHeaderHeight);
                removeAllCallbacks();
                if (mPullListener != null) {
                    mPullListener.refresh();
                }
            } else {
                translationTopY(position);
                mHandler.postDelayed(mRefresh = new ToRefresh(), 30 / mSpeed);
            }
        }
    }

    public class ToLoadMore implements Runnable {

        @Override
        public void run() {
            float position = mChildView.getTranslationY() + mDownwardSpeed;
            L.e(position + "ToLoadMore" + mDownwardSpeed);
            if (position <= -mLoadMoreFooterHeight) {
                mMaxSpeed = 0;
                translationBottomY(-mLoadMoreFooterHeight);
                removeAllCallbacks();
                if (mPullListener != null) {
                    mPullListener.loadMore();
                }
            } else {
                translationBottomY(position);
                mHandler.postDelayed(mLoadMore = new ToLoadMore(), 30 / mSpeed);
            }
        }
    }

    private void translationTopY(float offerSet) {
        if (offerSet < 0) {
            offerSet = 0;
        }
        if (offerSet == 0) {
            mRefreshHeader.getChildAt(0).setVisibility(INVISIBLE);
        } else {
            mRefreshHeader.getChildAt(0).setVisibility(VISIBLE);
        }
        if (offerSet > ScreenTools.getScreenHeightPx() / 2) {
            offerSet = ScreenTools.getScreenHeightPx() / 2;
        }
        if (offerSet > mRefreshHeaderHeight) {
            if (mHeaderView != null && mHeaderView.getCurrentIndex() != GRStateView.HoldingView) {
                initHeadView(mHeaderView.obtainHoldingView());
                animation(mHeaderView.getHoldingAnimView(), mHeaderView.getHoldingAnimation(getContext()));
            }
        } else {
            if (mState == ScrollState.Refreshing && mRefreshHeaderHeight == offerSet) {
                if (mHeaderView != null && mHeaderView.getCurrentIndex() != GRStateView.LoadingView) {
                    initHeadView(mHeaderView.obtainLoadingView());
                    animation(mHeaderView.getLoadingAnimView(), mHeaderView.getLoadingAnimation(getContext()));
                }

            } else {
                if (mHeaderView != null && mHeaderView.getCurrentIndex() != GRStateView.NormalView) {
                    initHeadView(mHeaderView.obtainNormalView());
                    animation(mHeaderView.getNormalAnimView(), mHeaderView.getNormalAnimation(getContext()));
                }

            }
        }
        mRefreshHeader.getChildAt(0).setTranslationY(offerSet);
        mChildView.setTranslationY(offerSet);
    }

    private void animation(View view, Animation animation) {
        if (animation != null && view != null) {
            view.clearAnimation();
            view.startAnimation(animation);
        }
    }

    private void translationBottomY(float offerSet) {
        if (offerSet > 0) {
            offerSet = 0;
        }
        if (offerSet == 0) {
            mRefreshFooter.getChildAt(0).setVisibility(INVISIBLE);
        } else {
            mRefreshFooter.getChildAt(0).setVisibility(VISIBLE);
        }

        if (offerSet < -ScreenTools.getScreenHeightPx() / 2) {
            offerSet = -ScreenTools.getScreenHeightPx() / 2;
        }
        if (offerSet < -mLoadMoreFooterHeight) {
            if (mFooterView != null && mFooterView.getCurrentIndex() != GRStateView.HoldingView) {
                initFooterView(mFooterView.obtainHoldingView());
                animation(mFooterView.getHoldingAnimView(), mFooterView.getHoldingAnimation(getContext()));
            }
        } else {
            if (mState == ScrollState.LoadingMore && -mLoadMoreFooterHeight == offerSet) {
                if (mFooterView != null && mFooterView.getCurrentIndex() != GRStateView.LoadingView) {
                    initFooterView(mFooterView.obtainLoadingView());
                    animation(mFooterView.getLoadingAnimView(), mFooterView.getLoadingAnimation(getContext()));
                }
            } else {
                if (mFooterView != null && mFooterView.getCurrentIndex() != GRStateView.NormalView) {
                    initFooterView(mFooterView.obtainNormalView());
                    animation(mFooterView.getNormalAnimView(), mFooterView.getNormalAnimation(getContext()));
                }
            }
        }
        mRefreshFooter.getChildAt(0).setTranslationY(offerSet);
        mChildView.setTranslationY(offerSet);
    }

    public class UpwardSlide implements Runnable {
        @Override
        public void run() {
            if (mUpwardSlideSpeed == 0) {
                mUpwardSlideSpeed = mChildView.getTranslationY() / 5f;
            }
            float position = mChildView.getTranslationY() - mUpwardSlideSpeed;
            if (position <= 0) {
                mMaxSpeed = 0;
                mState = ScrollState.Normal;
                translationTopY(0);
                removeAllCallbacks();
            } else {
                mState = ScrollState.UpwardResilience;
                translationTopY(position);
                mHandler.postDelayed(mUpwardSlideTask = new UpwardSlide(), 30 / mSpeed);
            }
        }
    }

    @TargetApi(23)
    public GRefresher(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GRefresher, defStyleAttr, 0);
        mHandler = new Handler(Looper.getMainLooper());
        a.recycle();
    }

    public interface PullListener {
        boolean canLoadMore();

        void refresh();

        void loadMore();
    }
}
