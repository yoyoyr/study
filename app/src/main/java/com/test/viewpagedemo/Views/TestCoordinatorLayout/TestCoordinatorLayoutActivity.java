package com.test.viewpagedemo.Views.TestCoordinatorLayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.point.R;

import java.util.ArrayList;
import java.util.List;

public class TestCoordinatorLayoutActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<String> data;
    private TabLayout toolbar_tab;
    private ViewPager main_vp_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_behavior);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        setContentView(R.layout.activity_coordinator_test1);
//        mRecyclerView = findViewById(R.id.rv);

        setContentView(R.layout.activity_behavior_head);

        toolbar_tab = (TabLayout) findViewById(R.id.tab);
        main_vp_container = (ViewPager) findViewById(R.id.recyclerView);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        main_vp_container.setAdapter(vpAdapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp_container));
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(manager);
//        MyAdapter adapter = new MyAdapter();
//        mRecyclerView.setAdapter(adapter);
//        adapter.setData(mockData());
//        adapter.notifyDataSetChanged();
    }
    @NonNull
    private List<String> mockData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item:" + i);
        }
        return data;
    }


    public static class MyAdapter extends RecyclerView.Adapter {
        private List<String> mData;

        public void setData(@NonNull List<String> data) {
            mData = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coordinator_item_layout, null));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_content_text);
        }
    }
}
