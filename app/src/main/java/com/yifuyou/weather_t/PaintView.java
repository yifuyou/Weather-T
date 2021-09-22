package com.yifuyou.weather_t;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class PaintView extends View {

    Canvas canvas;
    Paint paint ,textPaint;

    Context mContext;


    //
    private float pointX,pointY;
    private float endX, endY;
    private int highX,lowX;
    private int highY,lowY;
    private int padding,spaceX,spaceY;
    private int pathWide;
    private int[] Xs,Ys;


    public PaintView(Context context) {
        this(context,null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext=context;
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#008888"));
        textPaint=new Paint();
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.parseColor("#880088"));
        textPaint.setStrokeWidth(1);
    }

    public void setXYSource(int[] x,int[] y){
        System.out.println("set source");
        this.Xs=x;
        this.Ys=y;
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(Xs==null||Ys==null){
            return;
        }
        highX=lowX=Xs[0];
        highY=lowY=Ys[0];
        for(int x:Xs){
            highX=Math.max(highX,x);
            lowX=Math.min(lowX,x);
        }
        for(int y:Ys){
            highY=Math.max(highY,y);
            lowY=Math.min(lowY,y);
        }

        //横坐标 可以不理会x, 毕竟是一直递增的
        int countX = Xs.length + 1;
        spaceX = getMeasuredWidth() / countX ;
        //纵坐标 温度会波动
        int countY = highY-lowY;
        spaceY = getMeasuredHeight() / (countY+5) ;
        padding=Math.min(spaceX,spaceY);
        pathWide = 6;

        textPaint.setTextSize(padding);

        pointX = left + (spaceX>>2);
        pointY = top + (spaceY);
        endX = right - (spaceX>>2);
        endY = bottom - (spaceY>>1);
        paint.setStrokeWidth(pathWide);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#2222ff22"));
        canvas.drawLine(pointX,endY,endX,endY,paint);
        canvas.drawLine(pointX,pointY,pointX,endY,paint);
        if(Xs!=null&&Ys!=null){
            int count= Math.min(Xs.length, Ys.length);
            canvas.drawText(String.valueOf(Ys[0]),spaceX >> 1,padding+getOppositeY(padding + spaceY* (Ys[0]-lowY)),textPaint);
            for(int i=1;i<count;i++){
                int startX = (spaceX >> 1) + spaceX * (i-1);
                float startY =  getOppositeY(padding  + spaceY * (Ys[i-1]-lowY));
                float stopY = getOppositeY(padding  + spaceY * (Ys[i]-lowY));
                canvas.drawLine(startX,startY,startX+spaceX,stopY,paint);
                canvas.drawText(String.valueOf(Ys[i]),startX+spaceX,stopY+padding,textPaint);
            }
        }
        this.canvas=canvas;

        super.onDraw(canvas);
    }
    private float getOppositeY(float y){
        return endY-y-padding;
    }


    private void notifyChanged() {

            final AutofillManager afm = mContext.getSystemService(AutofillManager.class);
            if (afm != null) {
                afm.notifyValueChanged(PaintView.this);
            }
        }


    }
