package fast.mvplibrary.persenter;
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

import fast.mvplibrary.base.BaseModelAction;
import fast.mvplibrary.base.BaseUserAction;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public abstract class GPresenter {
    public GPresenter(Context context) {
        this.mContext = context;
    }

    public Context mContext;

    /**
     * 分发用户行为
     *
     * @param action {@link BaseUserAction}
     */
    public abstract void dispatchUserAction(BaseUserAction action);

    /**
     * 分发业务反馈
     *
     * @param action {@link BaseModelAction}
     */
    public abstract void dispatchGModelDone(BaseModelAction action);
}
