package fast.game.library.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;

import fast.game.library.R;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/29 11:23
 * 修改人：Administrator
 * 修改时间：2016/12/29 11:23
 * 修改备注：
 */
public class GSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    //屏幕宽高
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private Context mContext;
    private SurfaceHolder mHolder;
    //最大帧数 (1000 / 30)
    private static final int DRAW_INTERVAL = 30;

    private DrawThread mDrawThread;
    private FrameAnimation[] spriteAnimations;
    private Sprite mSprite;
    private int spriteWidth = 0;
    private int spriteHeight = 0;
    private float spriteSpeed = (float) ((500 * SCREEN_WIDTH / 480) * 0.01);
    private int row = 4;
    private int col = 4;
    private Bitmap bg;

    public GSurfaceView(Context context) {
        super(context);
        this.mContext = context;
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        initResources();

        mSprite = new Sprite(spriteAnimations, 0, 0, spriteWidth, spriteHeight, spriteSpeed);
    }

    private void initResources() {
        bg = decodeBitmapFromRes(mContext, R.drawable.pic);

        //将图片分成4行4列
        Bitmap[][] spriteImgs = generateBitmapArray(mContext, R.drawable.sprite, row, col);
        spriteAnimations = new FrameAnimation[row];
        for (int i = 0; i < row; i++) {
            //每列的图片
            Bitmap[] spriteImg = spriteImgs[i];
            FrameAnimation spriteAnimation = new FrameAnimation(spriteImg, new int[]{150, 150, 150, 150}, true);
            spriteAnimations[i] = spriteAnimation;
        }
    }

    public Bitmap decodeBitmapFromRes(Context context, int resourseId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public Bitmap createBitmap(Context context, Bitmap source, int row,
                               int col, int rowTotal, int colTotal) {
        int pointX = (col) * source.getWidth() / colTotal;
        int pointY = (row) * source.getHeight() / rowTotal;
        //从某个点(pointX,pointY)创建某个大小(this.spriteWidth,this.spriteHeight)的bitmap
        Bitmap bitmap = Bitmap.createBitmap(source,
                pointX,
                pointY, this.spriteWidth, this.spriteHeight);
        return bitmap;
    }

    //获取图片资源
    public Bitmap[][] generateBitmapArray(Context context, int resourseId,
                                          int row, int col) {
        Bitmap bitmaps[][] = new Bitmap[row][col];
        Bitmap source = decodeBitmapFromRes(context, resourseId);
        //单个图片的宽度=图片宽度/列数
        this.spriteWidth = source.getWidth() / col;
        //单个图片的高度=图片高度/行数
        this.spriteHeight = source.getHeight() / row;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                bitmaps[i][j] = createBitmap(context, source, i, j,
                        row, col);
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return bitmaps;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (null == mDrawThread) {
            mDrawThread = new DrawThread();
            mDrawThread.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mDrawThread) {
            mDrawThread.stopThread();
        }
    }

    private class DrawThread extends Thread {
        public boolean isRunning = false;

        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            boolean workIsNotFinish = true;
            while (workIsNotFinish) {
                try {
                    this.join();// 保证run方法执行完毕
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                workIsNotFinish = false;
            }
        }

        public void run() {
            long deltaTime = 0;
            long tickTime = 0;
            tickTime = System.currentTimeMillis();
            while (isRunning) {
                Canvas canvas = null;
                try {
                    synchronized (mHolder) {
                        canvas = mHolder.lockCanvas();
                        //设置方向
                        mSprite.setDirection();
                        //更新精灵位置
                        mSprite.updatePosition(deltaTime);
                        //画精灵
                        drawSprite(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (null != mHolder) {
                            mHolder.unlockCanvasAndPost(canvas);
                        }
                    } catch (Exception e) {

                    }

                }
                deltaTime = System.currentTimeMillis() - tickTime;
                if (deltaTime < DRAW_INTERVAL) {
                    try {
                        Thread.sleep(DRAW_INTERVAL - deltaTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tickTime = System.currentTimeMillis();
            }

        }
    }

    private void drawSprite(Canvas canvas) {
        //清屏操作
        canvas.drawBitmap(bg, 0, 0, null);
        mSprite.draw(canvas);
    }
}
