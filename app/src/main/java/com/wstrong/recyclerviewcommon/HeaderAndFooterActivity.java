package com.wstrong.recyclerviewcommon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wstrong.recyclerviewcommon.adapter.HeaderAndFooterAdapter;
import com.wstrong.recyclerviewcommon.util.DensityUtils;
import com.wstrong.recyclerviewcommon.widget.BorderDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HeaderAndFooterActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;

    private List<String> dataList;

    private HeaderAndFooterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load_more);

        initViews();
        initData();
        initHeader();
    }



    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("我是Item"+i);
        }

        adapter = new HeaderAndFooterAdapter(R.layout.listitem_main,dataList);

        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);

        recyclerView.setAdapter(adapter);
    }

    private void initHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.list_header_layout, (ViewGroup) recyclerView.getParent(),false);
        headerView.setOnClickListener(this);

        View footerView = getLayoutInflater().inflate(R.layout.list_footer_layout,(ViewGroup) recyclerView.getParent(),false);
        footerView.setOnClickListener(this);

        adapter.addHeaderView(headerView);
        adapter.addFooterView(footerView);
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new BorderDividerItemDecoration(
                DensityUtils.dp2px(this,10),DensityUtils.dp2px(this,10)));

        recyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Toast.makeText(HeaderAndFooterActivity.this,"onItemClick position:"+position,Toast.LENGTH_SHORT).show();

                adapter.remove(position);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_header:
                Toast.makeText(this,"Header Click!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_footer:
                Toast.makeText(this,"Footer Click!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
