package com.test.viewpagedemo.Views.RecyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAct extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<Integer> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        initData();

        recyclerView.setAdapter(new MyRecyclerViewAdapterWrapper(new MyRecyclerViewAdapter(data)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.addOnScrollListener(new MyOnScrollListener());
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof MyRecyclerViewAdapter.VH) {
                    LoggerUtils.LOGD("recycler text " + ((MyRecyclerViewAdapter.VH) holder).textView.getText());
                }
            }
        });
        recyclerView.addOnItemTouchListener(onItemTouchListener);
    }

    private void initData() {
        for (int i = 0; i < 30; ++i) {
            data.add(i);
        }
    }
    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

}
