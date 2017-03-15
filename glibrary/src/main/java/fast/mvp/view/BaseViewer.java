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
import fast.mvp.base.BaseModelAction;
import fast.mvp.base.BaseUserAction;

/**
 * Created by Administrator on 2017/3/1.
 * Description:
 * Function:
 */

public interface BaseViewer {
    /**
     * 设计这个接口的原因是，view的行为到底是由自己来控制还是由presenter来协调，这取决于具体场景
     * 为了让view能完全掌控自身，activity本身也可以成为一个presenter而无须加重代码负担，设计了这个接口
     *
     * @param action {@link BaseUserAction}
     * @param data
     */
    void dispatchData(BaseModelAction action);

    void upDateView(BaseModelAction action);
}
