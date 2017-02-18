package fast.glibrary.network;

import android.support.annotation.NonNull;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

import org.json.JSONObject;

import fast.glibrary.R;
import fast.glibrary.tools.GLibraryHelper;

/**
 * Created by GeJianYe on 2017/2/16 0016.
 * Description:
 * Function:
 */

public class NetWorkDispatcher {
    private static RequestQueue mRequestQueue;
    private static final byte[] lock = new byte[0];
    private static NetWorkDispatcher mInstance = null;
    private DispatcherConfig config;

    public void init(DispatcherConfig initConfig) {
        this.config = initConfig;
    }

    private NetWorkDispatcher() {
        mRequestQueue = NoHttp.newRequestQueue(DispatcherConfig.MaxConcurrencyCount);
    }

    public static NetWorkDispatcher getInstance() {
        if (mInstance == null) {
            synchronized (lock) {
                if (mInstance == null) {
                    mInstance = new NetWorkDispatcher();
                }
            }
        }
        return mInstance;
    }

    public void sendPost(String url, Action action, Param params, OnResponseListener listener) {
        sendPost(url, action, params, listener, false, false);
    }

    /**
     *
     * 使用{@link Param}传递参数,
     * {@link OnResponseListener}提供网络访问完成后的回调
     * 网络请求完成后，将结果转化成{@link JSONObject}格式对象返回
     *
     * @param url               请求路径
     * @param Action            请求的行为{@link Action}
     * @param params            请求参数
     * @param listener          请求回调
     * @param needDefaultHeader 是否添加默认请求头
     * @param needDefaultParams 是否添加共同参数
     */
    public void sendPost(String url, Action action, Param params, OnResponseListener listener, boolean needDefaultHeader, boolean needDefaultParams) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        config = checkNotNull(config);
        if (params != null) {
            for (String key : params.keySet()) {
                request.add(key, params.get(key));
            }
        }
        if (needDefaultParams) {
            for (String key : config.getDefaultParams(action.mAction).keySet()) {
                request.add(key, config.getDefaultParams(action.mAction).getKey(key));
            }
        }
        if (needDefaultHeader) {
            for (String key : config.getDefaultHeader(action.mAction).keySet()) {
                request.addHeader(key, config.getDefaultHeader(action.mAction).getKey(key));
            }
        }
        post(action.mAction, request, listener);
    }

    public void post(int posterId, Request request, OnResponseListener listener) {
        mRequestQueue.add(posterId, request, listener);
    }


    public abstract static class DispatcherConfig {
        //连接超时时长
        private static int ConnectTimeOut = 5 * 1000;
        //响应超时时长
        private static int ReadTimeOut = 5 * 1000;
        //最大并发数
        private static int MaxConcurrencyCount = 3;
        public abstract Param getDefaultHeader(int postId);

        public abstract Param getDefaultParams(int postId);
    }

    private DispatcherConfig checkNotNull(DispatcherConfig config) {
        if (config == null) {
            throw new NullPointerException(GLibraryHelper.getContext()
                    .getString(R.string.network_not_initialize));
        }
        return config;
    }
}
