package fast.mvp.persenter;
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

import fast.mvp.base.BaseModelAction;
import fast.mvp.base.BaseUserAction;

/**
 * Created by Administrator on 2017/3/1.
 * Description:
 * Function:
 */

public interface BasePresenter {
    /**
     * 分发用户行为
     *
     * @param action {@link BaseUserAction}
     */
    void dispatchUserAction(BaseUserAction action);

    /**
     * 分发业务反馈
     *
     * @param action {@link BaseModelAction}
     */
    void dispatchGModelDone(BaseModelAction action);
}
