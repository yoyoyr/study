package com.test.viewpagedemo.Glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageLoaderAdapter extends RecyclerView.Adapter {

    List<String> urls;

    public ImageLoaderAdapter(List<String> urls) {
        this.urls = urls;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (urls.get(position) != null) {
            LoggerUtils.LOGD("load image from url " + urls.get(position));
            Glide.with(holder.itemView.getContext())
                    .load(urls.get(position))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(((Item) holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class Item extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.imageItem)
        ImageView imageView;

        public Item(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
