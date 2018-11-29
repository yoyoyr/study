package com.test.viewpagedemo.Views.DrawerLayout;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.point.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    List<String> data = new ArrayList<>();
    Fragment parentFragment;
    FragmentRight fragmentNow;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer position = (Integer) v.getTag();
            //为什么要用getChildFragmentManager？？？
            FragmentRight fragmentRight = (FragmentRight) parentFragment.getChildFragmentManager().findFragmentByTag(data.get(position));

            if (fragmentRight == null) {
                fragmentRight = FragmentRight.newInstance(data.get(position));
                if (fragmentNow == null) {
                    parentFragment.getChildFragmentManager().beginTransaction()
                            .add(R.id.right, fragmentRight, data.get(position))
                            .commit();
                } else {
                    parentFragment.getChildFragmentManager().beginTransaction()
                            .add(R.id.right, fragmentRight, data.get(position))
                            .hide(fragmentNow)
                            .commit();
                }
            } else {
                if (fragmentNow == null) {
                    parentFragment.getChildFragmentManager().beginTransaction()
                            .show(fragmentRight)
                            .commit();
                } else {
                    parentFragment.getChildFragmentManager().beginTransaction()
                            .show(fragmentRight)
                            .hide(fragmentNow)
                            .commit();
                }

            }

            fragmentNow = fragmentRight;
        }
    };

    public MyAdapter(Fragment fragment) {

        parentFragment = fragment;
        for (int i = 0; i < 20; ++i) {
            data.add("item-" + i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(data.get(position));
        holder.textView.setOnClickListener(listener);
        holder.textView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
