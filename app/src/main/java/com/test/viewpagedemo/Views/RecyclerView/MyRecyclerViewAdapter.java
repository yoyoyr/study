package com.test.viewpagedemo.Views.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.point.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * notifyItemChanged 局部刷新
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.VH> {

    List<Integer> data;

    public MyRecyclerViewAdapter(List data) {
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.textView.setText(data.get(position) + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 返回item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * view被放进pool里面时调用
     * @param holder
     */
    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
    }

    class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv)
        TextView textView;

        public VH(View itemView) {
            super(itemView);
//            textView = itemView.findViewById(R.id.tv);
            ButterKnife.bind(this, itemView);
        }
    }
}
