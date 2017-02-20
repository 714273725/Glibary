package fast.mvp.view;
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

import fast.glibrary.network.Param;
import fast.mvp.base.ActionDispatcher;
import fast.mvp.base.BaseModelAction;
import fast.mvp.base.BaseUserAction;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public abstract class GView {
    public GView(ActionDispatcher actionDispatcher) {
        this.mActionDispatcher = actionDispatcher;
    }

    public ActionDispatcher mActionDispatcher;

    public abstract void userAction(BaseUserAction action);

    public abstract void dispatchData(BaseModelAction action, Object data);
    public abstract void upDateView(BaseModelAction action,Param params);
}
