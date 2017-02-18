package fast.glibrary.base;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

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

/**
 * Created by Administrator on 2017/2/18.
 */

public abstract class BaseResponseLister<T> implements OnResponseListener {
    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response response) {
        success(what, response, (T) response.get());
    }

    @Override
    public void onFinish(int what) {

    }

    abstract public void success(int what, Response response, T t);
}
