package com.zhwl.viewgroupdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pizi on 2018/11/23.
 */
public class VoiceView extends View {

    private int frist_color;
    private int second_color;
    private float circl_width;
    private int dot_count;
    private int split_size;
    private Drawable bg;
    private Paint paint;
    private int down_y;

    public VoiceView(Context context) {
        this(context,null);
    }

    public VoiceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VoiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.voice_view);
        //音量进度，背景色
        frist_color = typedArray.getColor(R.styleable.voice_view_frist_color, Color.BLACK);
        //音量进度，进度色
        second_color = typedArray.getColor(R.styleable.voice_view_second_color, Color.BLUE);
        //圆的宽度
        circl_width = typedArray.getDimension(R.styleable.voice_view_circl_width, 500);
        //音量进度，点的数量
        dot_count = typedArray.getInt(R.styleable.voice_view_dot_count, 10);
        //线的宽度
        split_size = typedArray.getInt(R.styleable.voice_view_split_size, 20);
        //每个音量线的长度
        pointSize = typedArray.getInt(R.styleable.voice_view_pointSize, 18);
        //初始音量
        progress = typedArray.getInt(R.styleable.voice_view_progress, 18);
        bg = typedArray.getDrawable(R.styleable.voice_view_bg);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(frist_color);

    }
    private int progress=3;
    public int pointSize=18;
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(split_size);
        paint.setStrokeCap(Paint.Cap.ROUND);

        int center= (int) (circl_width/2); //圆心
        int radius=center-split_size/2;//半径
        RectF rectF=new RectF();//设置范围
        rectF.set(center-radius,center-radius,center+radius,center+radius);
        float hudu=240/dot_count;
        for(int i=0;i<dot_count;i++){
            paint.setColor(frist_color);
            canvas.drawArc(rectF,-210+hudu*i+(hudu-pointSize)/2,pointSize,false,paint);
         }
        if(progress>dot_count){
            progress=dot_count;
        }

        if(progress<0){
            progress=0;
        }
        for(int i=0;i<progress;i++){
            paint.setColor(second_color);
            canvas.drawArc(rectF,-210+hudu*i+(hudu-pointSize)/2,pointSize,false,paint);
        }


        Rect rect=new Rect(); //文字的范围
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setTextSize(50);
        paint.getTextBounds(progress+"",0,(progress+"").length(),rect);

        paint.setColor(Color.GREEN);//文字颜色
        canvas.drawText(progress+"",center-rect.width()/2,center-rect.height()/2,paint);
//        //右半部分
//        canvas.drawArc(rectF,-87,18,false,paint);
//        canvas.drawArc(rectF,-63,18,false,paint);
//        canvas.drawArc(rectF,-39,18,false,paint);
//        canvas.drawArc(rectF,-15,18,false,paint);
//        canvas.drawArc(rectF,9,18,false,paint);
//
//        //左半部分
//        canvas.drawArc(rectF,-207,18,false,paint);
//        canvas.drawArc(rectF,-183,18,false,paint);
//        canvas.drawArc(rectF,-159,18,false,paint);
//        canvas.drawArc(rectF,-135,18,false,paint);
//        canvas.drawArc(rectF,-111,18,false,paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                down_y = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int up_y= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int move_y= (int) event.getY();
                if(down_y - move_y>30){
                    progress++;
                    postInvalidate();
                    down_y=move_y;
                }

                if(move_y - down_y>30){
                    progress--;
                    postInvalidate();
                    down_y=move_y;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width= (int) (circl_width+split_size*2);
        int height= (int) (circl_width-circl_width/4);
        setMeasuredDimension(width,height);
    }
}
