package fast.glibrary.weight.uiKit;
//			          _ooOoo_
//	           	     o8888888o
//                   88" . "88
//                   (| -_- |)
//                    O\ = /O
//                ____/`---'\____
//              .   ' \\| |// `.
//               / \\||| : |||// \
//             / _||||| -:- |||||- \
//               | | \\\ - /// | |
//             | \_| ''\---/'' | |
//            \ .-\__ `-` ___/-. /
//          ___`. .' /--.--\ `. . __
//       ."" '< `.___\_<|>_/___.' >'"".
//      | | : `- \`.;`\ _ /`;.`/ - ` : | |
//        \ \ `-. \_ __\ /__ _/ .-` / /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//
//.............................................
//               佛祖保佑             永无BUG

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import fast.glibrary.R;

/**
 * Created by Administrator on 2017/2/23.
 * Description: 刷新头及刷新尾视图工具类
 * Function:
 */

public abstract class GRStateView {
    //下拉超过刷新域值时显示的视图
    View mHoldingView;
    View mHoldingAnimView;
    //加载中时显示的视图
    View mLoadingView;
    View mLoadingAnimView;
    //下拉但没超过刷新域值时显示的视图
    View mNormalView;
    View mNormalAnimView;
    Animation mRefreshingAnim;
    Animation mHoldingAnim;
    Animation mNormalAnim;
    public static final int NormalView = 0;
    public static final int LoadingView = 1;
    public static final int HoldingView = 2;
    private int currentIndex;

    public View getHoldingAnimView() {
        if (mHoldingAnimView == null) {
            mHoldingAnimView = obtainHoldingView().findViewById(getAnimViewId());
        }
        return mHoldingAnimView;
    }

    public View getLoadingAnimView() {
        if (mLoadingAnimView == null) {
            mLoadingAnimView = obtainLoadingView().findViewById(getAnimViewId());
        }
        return mLoadingAnimView;
    }

    public View getNormalAnimView() {
        if (mNormalAnimView == null) {
            mNormalAnimView = obtainNormalView().findViewById(getAnimViewId());
        }
        return mNormalAnimView;
    }


    public int getCurrentIndex() {
        return currentIndex;
    }


    public View obtainNormalView() {
        if (mNormalView == null) {
            mNormalView = getNormalView();
        }
        currentIndex = NormalView;
        return mNormalView;
    }

    public View obtainLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
        }
        currentIndex = LoadingView;
        return mLoadingView;
    }

    public View obtainHoldingView() {
        if (mHoldingView == null) {
            mHoldingView = getHoldingView();
        }
        currentIndex = HoldingView;
        return mHoldingView;
    }


    /**
     * 获取加载中时显示的视图
     *
     * @return {@link View}
     */

    public abstract View getLoadingView();

    /**
     * 获取下拉超过刷新域值时显示的视图,eg:提示释放马上刷新
     *
     * @return {@link View}
     */
    public abstract View getHoldingView();

    /**
     * 获取下拉但没超过刷新域值时显示的视图，eg:提示继续下拉以刷新
     *
     * @return {@link View}
     */
    public abstract View getNormalView();

    public abstract int getAnimViewId();

    //
    public Animation getLoadingAnimation(Context context) {
        if (mRefreshingAnim == null)
            mRefreshingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate);
        return mRefreshingAnim;
    }

    public Animation getHoldingAnimation(Context context) {
        if (mHoldingAnim == null)
            mHoldingAnim = AnimationUtils.loadAnimation(context, R.anim.translate_up);
        return mHoldingAnim;
    }

    public Animation getNormalAnimation(Context context) {
        if (mNormalAnim == null)
            mNormalAnim = AnimationUtils.loadAnimation(context, R.anim.translate_down);
        return mNormalAnim;
    }
}
