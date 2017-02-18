package fast.glibrary.network;

import org.json.JSONObject;

import fast.glibrary.base.Action;
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
 * Created by GeJianYe on 2017/2/18 0018.
 * Description: 网络请求行为，通过{@link this#mAction}区分行为
 * Function:
 */

/**
 * 该类中的所有成员变量均有两种配置方式
 * 1.通过setXX（）方法配置
 * 2.通过{@link NetWorkDispatcher#config}配置
 * 若同时通过两种方式配置，则以setXX设置的为最后结果
 */
public class NetAction extends Action {
    /**
     * 是否添加默认的请求头
     * 要添加的请求头参数通过{@link NetWorkDispatcher#config}{@link NetWorkDispatcher.DispatcherConfig#getDefaultHeader(NetAction)}
     * 来配置
     */
    public boolean mAddHeader = false;
    /**
     * 是否添加默认的请求参数
     * 要添加的公共参数通过{@link NetWorkDispatcher#config}{@link NetWorkDispatcher.DispatcherConfig#getDefaultParams(NetAction)}
     * 来配置
     */
    public boolean mAddDefault = false;


    /**
     * 请求结果以何种类型返回给请求者
     */
    public Class mClz = JSONObject.class;

    public NetAction(int actionCode) {
        super(actionCode);
        mClz = NetWorkDispatcher.getInstance().getConfig().getClz(this);
        mAddHeader = NetWorkDispatcher.getInstance().getConfig().needDefaultHeader(this);
        mAddDefault = NetWorkDispatcher.getInstance().getConfig().needDefaultParams(this);
    }

    public NetAction addHeader() {
        mAddHeader = true;
        return this;
    }

    public NetAction addDefault() {
        mAddDefault = true;
        return this;
    }

    public NetAction setClz(Class clz) {
        this.mClz = clz;
        return this;
    }
}
