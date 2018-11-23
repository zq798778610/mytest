package com.zhwl.recycleviewtext;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新旧不同
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        //设置滑动方式，水平，竖直
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        //添加布局管理器
//        recyclerview.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        recyclerview.setLayoutManager(gridLayoutManager);
        //设置item增加，移处动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        recyclerview.addItemDecoration(new GridItemDecoration(this));
        //设置数据适配器
        MyAdapter adapter=new MyAdapter();
        recyclerview.setAdapter(adapter);


        for(int i=0;i<100;i++){
            datas.add("条目》》"+i);
        }
        adapter.notifyDataSetChanged();
    }


    private List<String> datas=new ArrayList<>();
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_recycleview, viewGroup, false);
            MyViewHolder viewHolder=new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,int i) {
           myViewHolder.et.setText(datas.get(i));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView et;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            et = itemView.findViewById(R.id.et);
        }
    }
}
