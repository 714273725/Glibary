package fast.glibrary.exception;

import android.annotation.TargetApi;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 10:14
 * 修改人：Administrator
 * 修改时间：2016/12/22 10:14
 * 修改备注：
 */
public class ErrorUesException extends RuntimeException {
    public ErrorUesException(String message) {
        super(message);
    }

    public ErrorUesException(Throwable cause) {
        super(cause);
    }

    public ErrorUesException(String message, Throwable cause) {
        super(message, cause);
    }

    @TargetApi(24)
    public ErrorUesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorUesException() {
    }
}
