package com.test.viewpagedemo.Views.SelfView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class MyView extends View {

    int defR = 200;
    Scroller scroller;
    float lastX, lastY;//, cirX, cirY;
    int mslop = 5;
    GestureDetector mDetector;
    @NonNull
    Paint paint = new Paint();

    //    开启线程或者动画，避免内存泄露
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    //    关闭线程和动画，避免内存泄露
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * canvas会切换为子控件的坐标系，也就是以自控的的左上角为坐标轴原点。
     * getLeft,getRight等方法是以父控件为坐标系计算距离，因此不适合在onDraw方法出现计算位置关系
     *
     * @param canvas
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {


        float r = (getWidth() - getPaddingLeft()) / 2;
        float cirX = r + getPaddingLeft();
        float cirY = getHeight() / 2;
        canvas.drawCircle(cirX, cirY, r, paint);
//        多线程尽量使用post系列函数
//        post();


    }

    /**
     * warp_content AT_MOST
     * match_parent/指定大小 EXACTLY
     *
     * @param widthMeasureSpec  父控件剩余宽度和控件大小模式
     * @param heightMeasureSpec 父控件剩余高度和控件大小模式
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        LoggerUtils.LOGD(name + " width = " + width + ",height = " + height + ",model = " + widthModel);

        if (widthModel == MeasureSpec.AT_MOST || heightModel == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defR * 2, defR * 2);
            return;
        }

        int r = width / 2;
        if (width > height) {
            r = height / 2;
        }

        setMeasuredDimension(r * 2, r * 2);
        return;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean flg = super.dispatchTouchEvent(event);
//        LoggerUtils.LOGD(flg + "");
        return flg;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        LoggerUtils.LOGD("action = " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
            lastY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            if (Math.abs(lastX - event.getX()) <= mslop && Math.abs(lastY - event.getY()) <= mslop) {
                LoggerUtils.LOGD("on click");
                lastY = lastX = 0;
            }
        }
        return false;
    }

    @Override
    public Matrix getMatrix() {
        LoggerUtils.LOGD("getMatrix");
        return super.getMatrix();
    }

    @Override
    public Rect getClipBounds() {
        LoggerUtils.LOGD(super.getClipBounds().toString());
        return super.getClipBounds();
    }

    @Override
    public void getDrawingRect(@NonNull Rect outRect) {
        LoggerUtils.LOGD(outRect.toString());
        super.getDrawingRect(outRect);
    }

    /**
     * This method is called by ViewGroup.drawChild() to have each child view draw itself.
     * This draw() method is an implementation detail and is not intended to be overridden or
     * to be called from anywhere else other than ViewGroup.drawChild().
     */
//            boolean draw(Canvas canvas, ViewGroup parent, long drawingTime) {}

    int color;
    @Nullable
    String name;

    private void init(Context context, AttributeSet attrs) {
        //解析myview.xml文件，并获取相关值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        color = a.getColor(R.styleable.MyView_color, 0);
        name = a.getString(R.styleable.MyView_name);
        a.recycle();

        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }


    /**
     * java代码直接new的时候调用
     *
     * @param context
     */
    public MyView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    /**
     * inflater初始化view的时候调用
     *
     * @param context
     * @param attrs
     */
    public MyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


}
