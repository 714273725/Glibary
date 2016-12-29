package fast.game.library.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;
import java.util.logging.Logger;

import fast.game.library.R;
import fast.glibrary.tools.L;
import fast.glibrary.tools.ScreenTools;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/29 13:30
 * 修改人：Administrator
 * 修改时间：2016/12/29 13:30
 * 修改备注：
 */
public class HSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private int imageWidth = 0;
    private int imageHeight = 0;
    private boolean isRunning = false;
    private int col = 2;
    int current = 0;
    Bitmap[] spriteImgs = new Bitmap[2];
    Thread th;
    private SurfaceHolder mHolder;

    public HSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Bitmap bitmap = decodeBitmapFromRes(context, R.drawable.pic);
        float off = size(R.drawable.pic,context);
        Matrix matrix = new Matrix();
        matrix.postScale(off,off);
        for (int i = 0; i < col; i++) {
            spriteImgs[i] = Bitmap.createBitmap(bitmap, i * bitmap.getWidth() / 2, 0, bitmap.getWidth() / 2, bitmap.getHeight(),matrix,true);
        }
        this.th = new Thread(this);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    public HSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public HSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public HSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        L.e("surfaceCreated");
        isRunning = true;
        th = new Thread(this);
        th.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        L.e("surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        L.e("surfaceDestroyed");
    }

    /**
     * 采样缩放
     *
     * @param context
     * @param resourseId
     * @return
     */
    public int size(Context context, int resourseId) {
        int i = 1;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        op.inPreferredConfig = Bitmap.Config.RGB_565;
        // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
        InputStream is = context.getResources().openRawResource(resourseId);
        BitmapFactory.decodeStream(is, null, op); // 获取尺寸信息
        if (op.outHeight > ScreenTools.dp2px(140) || op.outWidth > ScreenTools.dp2px(50)) {
            int h = (int) Math.floor(op.outHeight / ScreenTools.dp2px(140));
            int w = (int) Math.floor(op.outWidth / ScreenTools.dp2px(50));
            if (h > 1 || w > 1) {
                i = (h > w ? h : w);
            }
        }
        L.e("Size" + i);
        return i;
    }

    /**
     * 精确缩放
     *
     * @param context
     * @param resourseId
     * @return
     */
    public float size(int resourseId, Context context) {
        float i = 1;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        op.inPreferredConfig = Bitmap.Config.RGB_565;
        // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
        InputStream is = context.getResources().openRawResource(resourseId);
        BitmapFactory.decodeStream(is, null, op); // 获取尺寸信息
        if (op.outHeight > ScreenTools.dp2px(140) || op.outWidth > ScreenTools.dp2px(50)) {
            float h = op.outHeight / ScreenTools.dp2px(140);
            float w = op.outWidth / ScreenTools.dp2px(50)/col;
            if (h > 1 || w > 1) {
                i = (h > w ? h : w);
            }
        }
        L.e("Size" + i);
        return 1/i;
    }

    public Bitmap decodeBitmapFromRes(Context context, int resourseId, int off) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = off;
        InputStream is = context.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    public Bitmap decodeBitmapFromRes(Context context, int resourseId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    @Override
    public void run() {
        while (isRunning) {
            try {
                synchronized (mHolder) {
                    Canvas v = mHolder.lockCanvas();
                    current = (current == 1 ? 0 : 1);
                    v.drawBitmap(spriteImgs[current], 0, 0, null);
                    mHolder.unlockCanvasAndPost(v);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
