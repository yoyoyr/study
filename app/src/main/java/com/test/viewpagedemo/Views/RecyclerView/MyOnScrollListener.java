package com.test.viewpagedemo.Views.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.viewpagedemo.LoggerUtils;

public class MyOnScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        int lastPosition = (layoutManager.findLastVisibleItemPosition());
//        LoggerUtils.LOGD("last position = " + lastPosition + ",item count = " + recyclerView.getAdapter().getItemCount());
        if (lastPosition + 1 >= recyclerView.getAdapter().getItemCount()) {
            //load more
            LoggerUtils.LOGD("load more");
        }

    }
}
