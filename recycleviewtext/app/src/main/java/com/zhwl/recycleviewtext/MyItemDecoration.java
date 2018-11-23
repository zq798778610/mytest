package com.zhwl.recycleviewtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by pizi on 2018/11/14.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    public static final int[] ATTRS = new int[]{android.R.attr.listDivider}; //分割线的属性
    public static final int HORIZONTAL= LinearLayoutManager.HORIZONTAL;  //分割线的方向
    public static final int VERTICAL=LinearLayoutManager.VERTICAL;
    private final TypedArray typedArray;
    private final Drawable drawable; //分割线drawable
    private  int myOrientataion; //方向
    public MyItemDecoration(Context context,int orientation) {
        typedArray = context.obtainStyledAttributes(ATTRS);
        drawable = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);
    }

    /**
     * 设置方向
     * @param orientation
     */
    public void setOrientation(int orientation){
        if(orientation != HORIZONTAL && orientation !=VERTICAL){
            throw new IllegalArgumentException("orientation error");
        }
        this.myOrientataion=orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        if(myOrientataion==HORIZONTAL){
            drawHorizontal(c,parent);
        } else{
            drawVertical(c,parent);
        }
    }

    /**
     * 画竖直
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int left=parent.getPaddingLeft();  //左边距
        int right=parent.getWidth()-parent.getPaddingRight(); //右边距

        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top=child.getBottom()+layoutParams.bottomMargin;  //顶部位置
            int bottom=top+drawable.getIntrinsicHeight();  //底部位置
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    /**
     * 画水平
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top=parent.getPaddingTop();
        int bottom=parent.getHeight()-parent.getPaddingBottom();

        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left=child.getRight()+layoutParams.rightMargin;
            int right=left+drawable.getIntrinsicWidth();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if(myOrientataion==HORIZONTAL){
            outRect.set(0,0,drawable.getIntrinsicWidth(),0);
        }else{
            outRect.set(0,0,0,drawable.getIntrinsicHeight());
        }
    }


}
