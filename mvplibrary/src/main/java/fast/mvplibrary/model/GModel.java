package fast.mvplibrary.model;
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

import fast.mvplibrary.base.BaseModelAction;
import fast.mvplibrary.base.BaseUserAction;
import fast.mvplibrary.base.WorkDoneDispatcher;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public abstract class GModel {
    public GModel(WorkDoneDispatcher workDoneDispatcher) {
        this.mWorkDoneDispatcher = workDoneDispatcher;
    }

    public WorkDoneDispatcher mWorkDoneDispatcher;

    public abstract void workDone(BaseModelAction action);

    public abstract void dispatchUserAction(BaseUserAction action);
}
