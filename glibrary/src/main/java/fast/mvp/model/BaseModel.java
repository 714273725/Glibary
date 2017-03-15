package fast.mvp.model;
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

public interface BaseModel {
    /**
     * 此接口的设计思路与{@link fast.mvp.view.BaseViewer#dispatchData(BaseModelAction, Object)}一致
     *
     * @param action
     */
    void dispatchAction(BaseUserAction action);

    void workDone(BaseModelAction action);
}
