package fast.glibrary.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

import java.io.File;
import java.util.List;

import fast.glibrary.R;
import fast.glibrary.tools.GLibraryHelper;

/**
 * Created by GeJianYe on 2017/2/16 0016.
 * Description:网络请求分发中心，通过{@link NetAction}标志发起请求的使用者,并配置请求属性
 * Function:
 */

public class NetWorkDispatcher {
    private static RequestQueue mRequestQueue;
    /**
     * 多线程操作锁
     */
    private static final byte[] lock = new byte[0];
    private static NetWorkDispatcher mInstance = null;
    private DispatcherConfig config;

    public void init(Context c, DispatcherConfig initConfig) {
        this.config = initConfig;
        NoHttp.initialize(c, new NoHttp.Config()
                .setConnectTimeout(initConfig.ConnectTimeOut)
                .setReadTimeout(initConfig.ReadTimeOut));
    }

    public DispatcherConfig getConfig() {
        return checkNotNull(config);
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

    /**
     * post请求发起者
     *
     * @param url
     * @param action   {@link NetAction}
     * @param params   {@link Param}
     * @param listener {@link OnResponseListener}
     */
    public static void sendPost(String url, NetAction action, Param params, OnResponseListener listener) {
        getInstance().dispatchPost(url, action, params, listener);
    }

    /**
     * get请求发起者
     *
     * @param url
     * @param action   {@link NetAction}
     * @param listener {@link OnResponseListener}
     */
    public static void get(String url, NetAction action, OnResponseListener listener) {
        getInstance().dispatchGet(url, action, listener);
    }

    public static void upLoadFile(String url, NetAction action, Param params, OnResponseListener listener) {
        getInstance().dispatchPost(url, action, params, listener);
    }

    private void dispatchGet(String url, NetAction action, OnResponseListener listener) {
        doGet(url, action, listener);
    }

    /**
     * 文件上传，可同时提交参数及文件
     * {@link OnResponseListener}提供网络访问完成后的回调
     * 网络请求完成后，将结果转化成{@link NetAction#mClz}对象返回
     *
     * @param url      请求路径
     * @param action   请求者的行为{@link NetAction}
     * @param params   请求参数
     * @param listener 请求回调
     */
    private void doUpLoad(String url, NetAction action, Param params, OnResponseListener listener) {
        Request request = new BaseRequest(url, action, RequestMethod.POST);
        if (params != null) {
            for (String key : params.keySet()) {
                Object o = params.get(key);
                if (o instanceof String) {
                    request.add(key, o.toString());
                }
                if (o instanceof File) {
                    request.add(key, (File) o);
                }
            }
        }
        if (action.mAddHeader) {
            for (String key : config.getDefaultParams(action).keySet()) {
                request.add(key, config.getDefaultParams(action).getKey(key).toString());
            }
        }
        if (action.mAddDefault) {
            for (String key : config.getDefaultHeader(action).keySet()) {
                request.addHeader(key, config.getDefaultHeader(action).getKey(key).toString());
            }
        }
        doPost(action.mAction, request, listener);

    }

    /**
     * 根据分发post请求
     */
    private void dispatchPost(String url, NetAction action, Param params, OnResponseListener listener) {
        post(url, action, params, listener);
    }

    /**
     * 使用{@link Param}传递参数,
     * {@link OnResponseListener}提供网络访问完成后的回调
     * 网络请求完成后，将结果转化成{@link NetAction#mClz}对象返回
     *
     * @param url      请求路径
     * @param action   请求者的行为{@link NetAction}
     * @param params   请求参数
     * @param listener 请求回调
     */
    private void post(String url, NetAction action, Param params, OnResponseListener listener) {
        Request request = new BaseRequest(url, action, RequestMethod.POST);
        config = checkNotNull(config);
        if (params != null) {
            for (String key : params.keySet()) {
                request.add(key, params.get(key).toString());
            }
        }
        if (action.mAddHeader) {
            for (String key : config.getDefaultParams(action).keySet()) {
                request.add(key, config.getDefaultParams(action).getKey(key).toString());
            }
        }
        if (action.mAddDefault) {
            for (String key : config.getDefaultHeader(action).keySet()) {
                request.addHeader(key, config.getDefaultHeader(action).getKey(key).toString());
            }
        }
        doPost(action.mAction, request, listener);
    }

    public void doPost(int posterId, Request request, OnResponseListener listener) {
        mRequestQueue.add(posterId, request, listener);
    }

    private void doGet(String url, NetAction action, OnResponseListener listener) {
        mRequestQueue.add(action.mAction, new BaseRequest(url, action, RequestMethod.GET), listener);
    }

    /**
     * 网络请求配置
     */
    public abstract static class DispatcherConfig {
        //连接超时时长
        public static int ConnectTimeOut = 5 * 1000;
        //响应超时时长
        public static int ReadTimeOut = 5 * 1000;
        //最大并发数
        public static int MaxConcurrencyCount = 3;

        public abstract Param getDefaultHeader(NetAction action);

        public abstract Param getDefaultParams(NetAction action);

        public abstract Class getClz(NetAction action);

        /**
         * 添加公共参数的入口
         *
         * @param action {@link NetAction}
         * @return
         */
        public abstract boolean needDefaultHeader(NetAction action);

        /**
         * 添加公共请求头的入口
         *
         * @param action {@link NetAction}
         * @return
         */
        public abstract boolean needDefaultParams(NetAction action);

        /**
         * 网络访问请求返回来的数据经过格式化处理后，将回调此方法做集中化处理
         *
         * @param action {@link NetAction}
         * @param data
         */
        public abstract void handleData(NetAction action, Object data);
    }

    private DispatcherConfig checkNotNull(DispatcherConfig config) {
        if (config == null) {
            throw new NullPointerException(GLibraryHelper.getContext()
                    .getString(R.string.network_not_initialize));
        }
        return config;
    }
}
