package com.zhwl.viewtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pizi on 2018/11/16.
 */
public class MyView3 extends View {

    private int speed;
    private int color_back;
    private int color_move;
    private String cneter_text;
    private int width;
    private Paint paint;
    private float text_size;
    private Rect rect;

    public MyView3(Context context) {
        this(context,null);
    }

    public MyView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView3);
        //速度
        speed = typedArray.getInt(R.styleable.MyView3_speed, 5);
        //背景色
        color_back = typedArray.getColor(R.styleable.MyView3_color_backgrond, Color.BLUE);
        //移动的颜色
        color_move = typedArray.getColor(R.styleable.MyView3_color_move, Color.RED);
        cneter_text = typedArray.getString(R.styleable.MyView3_cneter_text);  //中心文字
        //中心文字
        text_size = typedArray.getDimension(R.styleable.MyView3_text_size, 18);
        width = typedArray.getDimensionPixelSize(R.styleable.MyView3_width,20); //宽度
        typedArray.recycle();

        initPaint();

        new Thread(){
            @Override
            public void run() {
                while (true) {
                    progress++;
                    if (progress == 360) {
                        progress = 0;
                        isNext=!isNext;
                    }
                    postInvalidate();
                    try
                    {
                        Thread.sleep(speed);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    private boolean isNext=false;
    private int progress=0;
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
        paint.setTextSize(text_size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int center=getWidth()/2; //圆心位置
        int radius=center-width/2;//半径

        paint.setStyle(Paint.Style.FILL);
        paint.getTextBounds(progress+"",0,(progress+"").length(),rect);
        canvas.drawText(progress+"",center-rect.width()/2,center+rect.height()/2,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);



        RectF rectF=new RectF();
        rectF.set(center-radius,center-radius,center+radius,center+radius); //边界
        if(isNext) {
            paint.setColor(color_back);
            canvas.drawCircle(center, center, radius, paint); //画圆
            paint.setColor(color_move);
            /**
             * 参数一：圆弧范围
             * 参数二：起始位置
             * 参数三，画多少弧度
             * 参数四：是否使用中心点，使用，则为扇形
             * 参数五，画笔
             */
            canvas.drawArc(rectF, -90, progress, false, paint);
        }else{
            paint.setColor(color_move);
            canvas.drawCircle(center, center, radius, paint); //画圆
            paint.setColor(color_back);
            /**
             * 参数一：圆弧范围
             * 参数二：起始位置
             * 参数三，画多少弧度
             * 参数四：是否使用中心点，使用，则为扇形
             * 参数五，画笔
             */
            canvas.drawArc(rectF, -90, progress, false, paint);
        }
    }
}
