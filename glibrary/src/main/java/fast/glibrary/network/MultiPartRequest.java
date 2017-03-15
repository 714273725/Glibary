package fast.glibrary.network;
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

import com.alibaba.fastjson.JSON;
import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/14.
 * Description:
 * Function:
 */

public class MultiPartRequest extends RestRequest {
    private NetAction mAction;

    public MultiPartRequest(String url, NetAction action) {
        super(url);
        this.mAction = action;
    }

    public MultiPartRequest(String url, NetAction action, RequestMethod requestMethod) {
        super(url, requestMethod);
        this.mAction = action;
    }

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

    protected boolean hasBinary() {
        try {
            Set<String> keys = getParamKeyValues().keySet();
            for (String key : keys) {
                List<Object> values = getParamKeyValues().getValues(key);
                for (Object value : values) {
                    if (value instanceof Binary)
                        return true;
                }
            }
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

    }

}
