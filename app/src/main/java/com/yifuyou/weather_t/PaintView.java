package com.yifuyou.weather_t;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;


import androidx.annotation.Nullable;


/**
 * 默认 显示六个坐标点，
 * 最长横坐标可以超过显示的宽度
 * padding 取显示宽度的十分之一[T,L,B,R ]
 * 显示宽度为 左padding + 折线宽（7*spaceX）+右padding
 * <p>
 * <p>
 * 显示高度为 上padding + 最大温差（dx * spaceY）+ 下padding
 */
public class PaintView extends View {

    public static final int RCOLOR=0x44ffffff;
    //边缘罩层
     int rlColor= Color.WHITE;

    Paint paint, textPaint;
    int textSize;
    int pathColor, textColor, bgColor;


    Rect vRect;

    /**
     * 横、纵 轴线 坐标
     */
    private float pointX, pointY;
    private float endX, endY;


    private int highX, lowX;

    /**
     * 最高、低温
     */
    private int highY, lowY;

    /**
     * @param padding 边缘填充量
     * @param spaceX,spaceY 点距
     */
    private int spaceX, spaceY;

    // t,l,b,r
    private int[] padding;
    /**
     * @params pathWide 线宽
     * (int) Math.round(padding[0]/10.0)
     */
    private int pathWide;

    /**
     * 偏移量
     */
    private int offset;
    //显示的点数
    private int count;

    //日期-温度 数据
    private int[] Xs, Ys;
    /*
        Bitmap bitmap;*/
    Paint bgPaint;
    //边缘粒度
    double granularity;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("ResourceAsColor")
    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFocusable(true);
        pathColor = Color.RED;
        count = 6;
        textSize =  2*count*count > 50 ? 50 : (200 / count < 18? 18 :200 /count);
        textColor = R.color.toolbar_text_color;
        bgColor = RCOLOR;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(pathColor);
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        padding = new int[4];
        offset = 0;
        vRect = new Rect();
//        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.yubg);
        bgPaint = new Paint();
        bgPaint.setColor(rlColor);
        bgPaint.setAlpha(100);
    }

    public void setXYSource(int[] x, int[] y) {
        System.out.println("set source");
        this.Xs = x;
        this.Ys = y;
        offset = 0;
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //宽高比 1：0.382
        if (MeasureSpec.getSize(widthMeasureSpec) < 100) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
        }
        ;

        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(
                        (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.382), MeasureSpec.EXACTLY));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (Xs == null || Ys == null) {
            return;
        }
        highX = lowX = Xs[0];
        highY = lowY = Ys[0];
        for (int x : Xs) {
            highX = Math.max(highX, x);
            lowX = Math.min(lowX, x);
        }
        for (int y : Ys) {
            highY = Math.max(highY, y);
            lowY = Math.min(lowY, y);
        }

        //横坐标 宽度固定，默认只显示6个坐标
        spaceX = getMeasuredWidth() / count;
        //纵坐标 温度会波动
        spaceY = getMeasuredHeight() / (highY - lowY + 6);
        padding[0] = spaceY >> 1;
        padding[1] = spaceX >> 2;
        padding[2] = spaceY >> 1;
        padding[3] = spaceX >> 3;
        pathWide = (int) Math.round(padding[0] / 10.0);

//        textPaint.setTextSize(padding[1]);

        pointX = spaceX >> 2;
        pointY = spaceY >> 2;
        endX = right - left - (spaceX >> 2);
        endY = bottom - top - (spaceY << 2);

        paint.setStrokeWidth(pathWide);
        vRect.set(padding[1], padding[0], right - left - padding[1] - padding[3], bottom - top - padding[2] - padding[0]);
        granularity = spaceX / 256.0;

    }

    /**
     * @param canvas 显示画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(bitmap,0,0,paint);
//        canvas.drawColor(Color.YELLOW);
        canvas.drawColor(bgColor);
        if (Xs != null && Ys != null) {
            //绘制折线
            initCanvas(canvas);

            canvas.drawLine(pointX, endY, endX, endY, this.paint);
            canvas.drawLine(endX, endY, (float) (endX - (padding[3] / 10.0)), (float) (endY - (padding[2] / 5.0)), this.paint);
            canvas.drawLine(endX, endY, (float) (endX - (padding[3] / 10.0)), (float) (endY + (padding[2] / 5.0)), this.paint);

            for (int i = 0; i < 255; i += 1) {
                rlColor -= 0x01000000;
                bgPaint.setColor(rlColor);
                canvas.drawRect((float) (  i * granularity),
                        0,
                        (float) ( (i + 1) * granularity),
                        getHeight(),
                        bgPaint);
                canvas.drawRect((float) (getWidth() - i * granularity),
                        0,
                        (float) (getWidth() - (i + 1) * granularity),
                        getHeight(),
                        bgPaint);
            }
            canvas.clipRect(vRect);
            rlColor=Color.WHITE;
        }
        super.onDraw(canvas);
    }

    protected void initCanvas(Canvas canvas) {
        int count = Math.min(Xs.length, Ys.length);
        if (Ys[0] < (highY - lowY) / 2) {
            canvas.drawText(String.valueOf(Ys[0] + "C'"), (spaceX >> 1) + offset, padding[0] + getOppositeY(-padding[0] + spaceY * (Ys[0] - lowY)) - textSize, textPaint);
        } else {
            canvas.drawText(String.valueOf(Ys[0] + "C'"), (spaceX >> 1) + offset, padding[0] + getOppositeY(padding[0] + spaceY * (Ys[0] - lowY)) + textSize, textPaint);
        }
        canvas.drawText(String.valueOf(Xs[0] + "h"), (spaceX >> 1) + offset, (float) (endY + (padding[2])) + textSize / 2, textPaint);

        canvas.drawCircle((spaceX >> 1) + offset, getOppositeY(padding[0] + spaceY * (Ys[0] - lowY)), pathWide * 4, textPaint);
        for (int i = 1; i < count; i++) {
            float startX = (spaceX >> 1) + spaceX * (i - 1) + offset;
            float startY = getOppositeY(padding[0] + spaceY * (Ys[i - 1] - lowY));
            float stopY = getOppositeY(padding[0] + spaceY * (Ys[i] - lowY));
            canvas.drawLine(startX, startY, startX + spaceX, stopY, paint);
            canvas.drawCircle(startX+spaceX, stopY, pathWide * 4, textPaint);
            if (Ys[i] < (highY + lowY) / 2) {
                canvas.drawText(String.valueOf(Ys[i] + "C'"), startX + spaceX, stopY, textPaint);
            } else {
                canvas.drawText(String.valueOf(Ys[i] + "C'"), startX + spaceX, stopY + textSize, textPaint);
            }

            canvas.drawText(String.valueOf(Xs[i] + "h"), startX + spaceX, (float) (endY + (padding[2])) + textSize / 2, textPaint);
        }
    }

    private float getOppositeY(float y) {
        return endY - y - padding[0];
    }


    private float touchX, dX;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                stopNestedScroll();
                touchX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                dX = event.getRawX() - touchX;
                if (Math.abs(dX) > 10) {
                    touchX = event.getRawX();
                    computeScroll();
//                    requestLayout();
                    invalidate();

                }

                break;
            case MotionEvent.ACTION_UP:
                touchX = 0;
                stopNestedScroll();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        attemptClaimDrag();
        return super.dispatchTouchEvent(event);

    }

    private void attemptClaimDrag() {
        //  获取父组件
        ViewParent parent = getParent();
        if (parent != null) {
            // 请求不要拦截
            parent.requestDisallowInterceptTouchEvent(true);
        }

    }


    @Override
    public void computeScroll() {
        System.out.println("dx :" + dX + ", offset :" + offset);
        int d = (int) (dX + offset);
        if (d <= 0 && d > -spaceX * (Xs.length-1) + getWidth()-padding[1]-padding[3]-spaceX) {
            offset = d;
        }
        System.out.println("x偏移 " + offset);
    }

}
