package com.wstrong.recyclerviewcommon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wstrong.recyclerviewcommon.adapter.MainAdapter;
import com.wstrong.recyclerviewcommon.util.DensityUtils;
import com.wstrong.recyclerviewcommon.widget.BorderDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> dataList;

    private MainAdapter adapter;

    private final String[] names = new String[]{
            "带头部和尾部的列表","SwipeRefreshLayout下拉刷新和上拉加载的列表",
            "自定义下拉刷新和上拉加载的列表"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
    }

    private void initData() {
        dataList = Arrays.asList(names);

        adapter = new MainAdapter(R.layout.listitem_main,dataList);

        recyclerView.setAdapter(adapter);

    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new BorderDividerItemDecoration(
                DensityUtils.dp2px(this,8),DensityUtils.dp2px(this,15)));

        recyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Toast.makeText(MainActivity.this,"position "+position,Toast.LENGTH_SHORT).show();
                Intent intent = null;
                if(position == 0){
                    intent = new Intent(MainActivity.this,HeaderAndFooterActivity.class);
                    startActivity(intent);
                }else if(position == 1){
                    intent = new Intent(MainActivity.this,SwipeRefreshActivity.class);
                    startActivity(intent);
                }else if(position == 2){
                    intent = new Intent(MainActivity.this,PullToRefreshActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }
}
