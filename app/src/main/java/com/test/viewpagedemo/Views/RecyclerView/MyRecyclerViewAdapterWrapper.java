package com.test.viewpagedemo.Views.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;


public class MyRecyclerViewAdapterWrapper extends RecyclerView.Adapter {

    MyRecyclerViewAdapter adapter;

    static final int HEADER = 0;
    static final int FOOTER = 1;
    static final int NORMAL = 2;

    public MyRecyclerViewAdapterWrapper(MyRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new HeaderVH(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false));
        } else if (viewType == FOOTER) {
            return new FooterVH(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foot, parent, false));
        } else {
            return adapter.createViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == NORMAL) {
            adapter.onBindViewHolder((MyRecyclerViewAdapter.VH) holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1 + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 1) {
//            LoggerUtils.LOGD("return header");
            return HEADER;
        } else if (position >= 1 && position <= adapter.getItemCount()) {
//            LoggerUtils.LOGD("return normal");
            return NORMAL;
        } else {
//            LoggerUtils.LOGD("return footer");
            return FOOTER;
        }
    }

    class HeaderVH extends RecyclerView.ViewHolder {

        public HeaderVH(@NonNull View itemView) {
            super(itemView);
        }
    }


    class FooterVH extends RecyclerView.ViewHolder {

        public FooterVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
