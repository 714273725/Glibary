package fast.mvplibrary.base;
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

public class BaseModelAction {
    public int mAction;
    public BaseModelAction setParams(Object params) {
        this.mParams = params;
        return this;
    }

    public Object getParams() {
        return mParams;
    }

    private Object mParams;
    public BaseModelAction(int action) {
        this.mAction = action;
    }
}
