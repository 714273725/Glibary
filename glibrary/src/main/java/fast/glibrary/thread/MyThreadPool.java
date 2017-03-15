package fast.glibrary.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
public class MyThreadPool implements BaseThreadPool {
    ExecutorService mNowPool;
    ExecutorService mFreePool;
    public static MyThreadPool mPool;

    {
        mNowPool = Executors.newCachedThreadPool();
        mFreePool = Executors.newFixedThreadPool(3);
    }

    public static MyThreadPool getInstance() {
        if (null == mPool) {
            synchronized (MyThreadPool.class) {
                if (null == mPool) {
                    mPool = new MyThreadPool();
                }
            }
        }
        return mPool;
    }

    private MyThreadPool() {
    }

    @Override
    public void executeNow(Runnable now) {
        mNowPool.execute(now);
    }

    @Override
    public void executeWhenFree(Runnable free) {
        mFreePool.execute(free);
    }

    @Override
    public void cancelAll() {

    }

    @Override
    public void cancelAllNow() {
    }

    @Override
    public void cancelAllFree() {

    }
}
