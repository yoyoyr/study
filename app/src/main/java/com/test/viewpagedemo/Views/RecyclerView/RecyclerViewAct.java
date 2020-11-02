package com.test.viewpagedemo.Views.RecyclerView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

public class RecyclerViewAct extends AppCompatActivity {

    @Nullable
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @NonNull
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
                }
            }
        });
        recyclerView.addOnItemTouchListener(onItemTouchListener);

//        Observable.interval(100, TimeUnit.MICROSECONDS, AndroidSchedulers.mainThread())
//                .map(new Function<Long, Boolean>() {
//                    @Override
//                    public Boolean apply(Long aLong) throws Exception {
//                        long start = System.currentTimeMillis();
//                        for (int i = 0; i < 3000000; ++i) {
//                            data.get(0);
//                        }
//                        LoggerUtils.LOGV(Thread.currentThread().getName() + " , use time = " + (System.currentTimeMillis() - start));
//                        return true;
//                    }
//                }).subscribe();
    }

    private void initData() {
        for (int i = 0; i < 30; ++i) {
            data.add(i);
        }
    }

    @NonNull
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
