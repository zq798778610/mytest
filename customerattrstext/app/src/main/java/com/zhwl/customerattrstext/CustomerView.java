package com.zhwl.customerattrstext;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by pizi on 2018/11/14.
 * 1、自定义view，的构造方法里有AttributeSet：属性集
 * 2、通过属性集获取到的TypedArray，是什么
 */
public class CustomerView extends View {
    public CustomerView(Context context) {
        this(context,null);
    }

    public CustomerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customer_test);
        String text = typedArray.getString(R.styleable.customer_test_text);
        int text_int = typedArray.getInt(R.styleable.customer_test_text_int, 0);

        //打印自定义属性值
        Log.i("zq","text>>"+text);
        Log.i("zq","text_int>>"+text_int);

        //打印所有属性
        int attrs_count=attrs.getAttributeCount();
        for(int i=0;i<attrs_count;i++){
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            Log.i("zq","attributeName>>>"+attributeName+"|||||attributeValue>>>"+attributeValue);
        }

        typedArray.recycle();
    }
}
