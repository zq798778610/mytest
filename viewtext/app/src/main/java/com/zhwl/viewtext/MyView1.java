package com.zhwl.viewtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by pizi on 2018/11/15.
 */
public class MyView1 extends View {

    private String text;
    private int color;
    private float dimension;
    private Paint paint;
    private Rect rect;

    public MyView1(Context context) {
        this(context,null);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myview);
        text = typedArray.getString(R.styleable.myview_text_str);
        color = typedArray.getColor(R.styleable.myview_text_color, Color.GREEN);
        dimension = typedArray.getDimension(R.styleable.myview_text_size, 18);

        Log.i("zq","text>>"+ text);
        Log.i("zq","color>>"+ color);
        Log.i("zq","dimension>>"+ dimension);

        typedArray.recycle();

        initPain();
    }

    private void initPain() {
        //抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(dimension);
        //文字边界
        rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);

        paint.setColor(color);
        canvas.drawText(text,getWidth()/2-rect.width()/2,getHeight()/2+rect.height()/2,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //什么模式，三种模式
        /**
         * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
         * AT_MOST:表示子布局限制在一个最大值内，一般为WARP_CONTENT
         * UNSPECIFIED:表示子布局想要多大就多大，很少使用
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width=0;
        int height=0;
        if(widthMode==MeasureSpec.EXACTLY){ //确定值，按照控件大小来设置
            width=widthSize;
        }else{ //按照最大值来设置
            paint.setTextSize(dimension);
            paint.getTextBounds(text,0,text.length(),rect); //获得text的范围
            int text_width = rect.width();
            int text_all_width=getPaddingLeft()+text_width+getPaddingRight();
            width=text_all_width;
        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{
            paint.setTextSize(dimension);
            paint.getTextBounds(text,0,text.length(),rect); //获得text的范围
            int text_height = rect.height();
            int text_all_height=getPaddingTop()+text_height+getPaddingBottom();
            height=text_all_height;
        }
        setMeasuredDimension(width,height);
    }
}
