package fast.mvp.base;
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

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public class BaseUserAction {
    public int mAction;

    public BaseUserAction setParams(Object params) {
        this.mParams = params;
        return this;
    }

    public Object getParams() {
        return mParams;
    }

    private Object mParams;

    public BaseUserAction(int action) {
        this.mAction = action;
    }
}
