package com.zhwl.viewtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by pizi on 2018/11/16.
 */
public class MyView2 extends View {

    private String title_text;
    private float text_size;
    private int text_color;
    private Bitmap bitmap;
    private int image_scaletype;
    private Rect rect;
    private Rect textRect;
    private Paint paint;

    public MyView2(Context context) {
        this(context,null);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView2);
        title_text = typedArray.getString(R.styleable.MyView2_title_text);
        text_size = typedArray.getDimension(R.styleable.MyView2_title_text_size, 16);
        text_color = typedArray.getColor(R.styleable.MyView2_title_text_color, Color.GREEN);
        int imageId = typedArray.getResourceId(R.styleable.MyView2_title_image, R.drawable.ic_launcher_background);
        bitmap = BitmapFactory.decodeResource(getResources(), imageId);
        Log.i("zq","bitmap>>>"+bitmap);
        image_scaletype = typedArray.getInt(R.styleable.MyView2_title_image_scaletype, 0);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
        //储存文字范围
        textRect = new Rect();
        paint.setTextSize(text_size);
        paint.getTextBounds(title_text,0,title_text.length(), textRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("zq","bitmap>>>"+bitmap);
        /**
         * 设置宽度
         */
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width=0;
        if(mode==MeasureSpec.EXACTLY){ //固定大小
            width=size;
        }else{
            int wByImage=getPaddingLeft()+getPaddingRight()+bitmap.getWidth(); //图片的宽度+padding值
            int wByText=getPaddingLeft()+getPaddingRight()+rect.width();//文字宽度+padding值
            if(mode==MeasureSpec.AT_MOST){
                int w=Math.max(wByImage,wByText);
                width=Math.min(wByImage,wByText);
            }
        }

        /**
         * 设置高度
         */
        int h_size = MeasureSpec.getSize(heightMeasureSpec);
        int h_mode1 = MeasureSpec.getMode(heightMeasureSpec);
        int height=0;
        if(h_mode1==MeasureSpec.EXACTLY){
            height=h_size;
        }else{
            height=bitmap.getHeight()+getPaddingTop()+getPaddingBottom()+rect.height();
            if(h_mode1==MeasureSpec.AT_MOST){
                height=Math.min(h_size,height);
            }
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(4); //设置
        paint.setColor(Color.RED);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint); //先画范围，此范围包括，padding

        rect.left=getPaddingLeft();
        rect.right=getMeasuredWidth()-getPaddingRight();
        rect.top=getPaddingTop();
        rect.bottom=getMeasuredHeight()-getPaddingBottom();

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL); //画内部，将画笔设成实心

        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();
        //判断文字长度是否大于设置长度，如果大于，则需要。。。
        if(textRect.width()>getMeasuredWidth()){
            TextPaint textPaint=new TextPaint();
            textPaint.setColor(text_color);
            textPaint.setTextSize(text_size);
            String text= TextUtils.ellipsize(title_text,textPaint,(float)(mWidth - getPaddingLeft()-getPaddingRight()),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(text,getPaddingLeft(),getMeasuredHeight()-getPaddingBottom(),paint);
        }else{
            canvas.drawText(title_text,getMeasuredWidth()/2-rect.width()/2,getMeasuredHeight()-getPaddingBottom(),paint);
        }
        rect.bottom-=textRect.height();  //除去文字，剩下的为图片空间
        //取消使用掉的快
        if (image_scaletype == 0)
        {
            canvas.drawBitmap(bitmap, null, rect, paint);
        } else
        {
            //计算居中的矩形范围
            rect.left = mWidth / 2 - bitmap.getWidth() / 2;
            rect.right = mWidth / 2 + bitmap.getWidth() / 2;
            rect.top = (mHeight - textRect.height()) / 2 - bitmap.getHeight() / 2;
            rect.bottom = (mHeight - textRect.height()) / 2 + bitmap.getHeight() / 2;

            canvas.drawBitmap(bitmap, null, rect, paint);
        }
    }
}
