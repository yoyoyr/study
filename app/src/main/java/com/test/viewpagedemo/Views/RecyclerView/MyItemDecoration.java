package com.test.viewpagedemo.Views.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class MyItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 绘制recyclerView的时候调用一次，
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); ++i) {
            View view = parent.getChildAt(i);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            c.drawRect(view.getLeft(), view.getBottom(), view.getRight(), view.getBottom() + 10, paint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * insets为每个item的矩形范围
     * insets.left += outRect.left;
     * insets.top += outRect.top;
     * insets.right += outRect.right;
     * insets.bottom += outRect.bottom;
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 20;
    }
}
