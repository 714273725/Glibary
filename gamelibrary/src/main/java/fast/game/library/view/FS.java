package fast.game.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

import fast.glibrary.tools.L;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/29 16:11
 * 修改人：Administrator
 * 修改时间：2016/12/29 16:11
 * 修改备注：
 */
public class FS extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public FS(Context context) {
        super(context);
        init(context);
    }

    private SurfaceHolder mHolder;
    Bitmap[] bitmaps;
    Thread th;

    private void init(Context context) {
        ArrayList<String> a = new ArrayList<>();
        for (int i = 0; i < 129; i++) {
            String fix = null;
            if (String.valueOf(i).length() == 1) {
                fix = "df000" + String.valueOf(i);
            }
            if (String.valueOf(i).length() == 2) {
                fix = "df00" + String.valueOf(i);
            }
            if (String.valueOf(i).length() == 3) {
                fix = "df0" + String.valueOf(i);
            }
            a.add(fix + ".png");
        }
        bitmaps = new Bitmap[a.size()];
        for (int i = 0; i < a.size(); i++) {
            try {
                Bitmap b = BitmapFactory.decodeStream(context.getAssets().open(a.get(i)));
                Matrix m = new Matrix();
                m.postScale(0.6f, 0.6f);
                bitmaps[i] = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                b.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        th = new Thread(this);
        th.start();
    }

    public FS(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private int i = 0;

    @Override
    public void run() {
        long deltaTime = 0;
        long tickTime = 0;
        tickTime = System.currentTimeMillis();
        while (true) {
            Canvas v = null;
            synchronized (mHolder) {
                try {

                    /*if (i > 128) {
                        i = 0;
                    } else {
                        i++;
                    }*/
                    i=(i>127?0:i+1);
                    v = mHolder.lockCanvas();
                    v.drawBitmap(bitmaps[i], 0, 0, null);
                    mHolder.unlockCanvasAndPost(v);
                    deltaTime = System.currentTimeMillis() - tickTime;
                    Thread.sleep(25);
                } catch (IllegalStateException r){
                    r.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }

        }
    }
}
