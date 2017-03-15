package fast.glibrary.thread;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
public interface BaseThreadPool {
    void executeNow(Runnable now);
    void executeWhenFree(Runnable free);
    void cancelAll();
    void cancelAllNow();
    void cancelAllFree();
}
