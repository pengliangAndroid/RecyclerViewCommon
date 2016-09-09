package com.wstrong.recyclerviewcommon.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wstrong.recyclerviewcommon.R;

import java.util.List;

/**
 * Created by pengl on 2016/9/7.
 */
public class MainAdapter extends BaseQuickAdapter<String> {

    public MainAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        holder.setText(R.id.tv_content,s);

    }
}
