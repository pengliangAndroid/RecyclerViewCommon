package com.wstrong.recyclerviewcommon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

public class SwipeRefreshActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    private static final int PAGE_SIZE = 6;
    private static final int TOTAL_NUMBER = 18;

    private SwipeRefreshLayout swipeRefreshLayout;

    private View noMoreView,errorView,emptyView;

    private RecyclerView recyclerView;

    private List<String> dataList;

    private HeaderAndFooterAdapter adapter;

    private Handler handler = new Handler();

    private int curCount;

    private boolean isError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        initViews();
        initAdapter();
        initHeader();
    }


    private void initAdapter() {
        adapter = new HeaderAndFooterAdapter(R.layout.listitem_main,getSimpleData(PAGE_SIZE));

        adapter.openLoadAnimation();
        adapter.isFirstOnly(true);
        adapter.openLoadMore(PAGE_SIZE);
        recyclerView.setAdapter(adapter);
        curCount = PAGE_SIZE;

        recyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                //Toast.makeText(SwipeRefreshActivity.this,"onItemClick position:"+position,Toast.LENGTH_SHORT).show();
                System.out.println(position);
                System.out.println("headerCount:"+adapter.getHeaderLayoutCount());
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

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if(curCount >= TOTAL_NUMBER){
                            adapter.loadComplete();

                            adapter.addFooterView(noMoreView);
                        }else{
                            if(isError){
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.addData(getSimpleData(PAGE_SIZE));
                                        curCount = adapter.getData().size();
                                    }
                                },1000);

                            }else{
                                isError = true;
                                Toast.makeText(SwipeRefreshActivity.this, "网络异常，请刷新重试", Toast.LENGTH_LONG).show();
                                adapter.showLoadMoreFailedView();
                            }
                        }
                    }
                });

            }
        });


    }

    private void initHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.list_header_layout, (ViewGroup) recyclerView.getParent(),false);
        headerView.setOnClickListener(this);

       /* View footerView = getLayoutInflater().inflate(R.layout.list_footer_layout,(ViewGroup) recyclerView.getParent(),false);
        footerView.setOnClickListener(this);*/

        adapter.addHeaderView(headerView);
        //adapter.addFooterView(footerView);

        //adapter.setEmptyView(emptyView);
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this));

        recyclerView.addItemDecoration(new BorderDividerItemDecoration(
                DensityUtils.dp2px(this,10),DensityUtils.dp2px(this,10)));

        noMoreView = getLayoutInflater().inflate(R.layout.no_more_layout,(ViewGroup) recyclerView.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.error_view,(ViewGroup) recyclerView.getParent(), false);
        emptyView = getLayoutInflater().inflate(R.layout.empty_view,(ViewGroup) recyclerView.getParent(), false);
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

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setNewData(getSimpleData(PAGE_SIZE));
                adapter.openLoadMore(PAGE_SIZE);
                adapter.removeAllFooterView();
                curCount = PAGE_SIZE;
                swipeRefreshLayout.setRefreshing(false);
                isError = false;
            }
        },1000);
    }

    public List<String> getSimpleData(int pageSize){
        List<String> tmpList = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            tmpList.add("我是New Item"+i);
        }
        return tmpList;
    }
}
