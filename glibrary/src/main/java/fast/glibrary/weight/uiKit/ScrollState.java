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

/**
 * Created by Administrator on 2017/2/20.
 * Description:
 * Function:
 */

public enum ScrollState {
    Normal, //正常状态
    PullingDown, //正在下拉==holding
    UpwardResilience, //向上回弹
    Refreshing, //正在刷新
    PullingUp,  //正在上拉
    DownwardResilience, //向下回弹
    LoadingMore
}
