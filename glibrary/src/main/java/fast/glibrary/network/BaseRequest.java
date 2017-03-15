package fast.glibrary.network;
/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * =====`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑             永无BUG
 */


import com.alibaba.fastjson.JSON;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/2/18.
 */

public class BaseRequest extends RestRequest {
    private NetAction mAction;

    public BaseRequest(String url, NetAction action, RequestMethod requestMethod) {
        super(url, requestMethod);
        this.mAction = action;
    }

    /**
     * 分析请求结果并转化成请求者需要的格式
     *
     * @param responseHeaders
     * @param responseBody
     * @return
     * @throws Throwable
     */
    @Override
    public Object parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        String jsonStr = StringRequest.parseResponseString(responseHeaders, responseBody);
        try {
            if (mAction.mClz == JSONObject.class) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                NetWorkDispatcher.getInstance().getConfig().handleData(mAction, jsonObject);
                return jsonObject;
            }
            Object object = JSON.parseObject(jsonStr, mAction.mClz);
            NetWorkDispatcher.getInstance().getConfig().handleData(mAction, object);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return NetWorkDispatcher.getInstance().getConfig().handleData(mAction, jsonStr);
        }
    }
}
