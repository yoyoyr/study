package com.test.viewpagedemo.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class CircleCrop extends BitmapTransformation {

    public CircleCrop(Context context) {
        super(context);
    }

    public CircleCrop(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @NonNull
    @Override
    public String getId() {
        return "com.example.glidetest.CircleCrop";
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int R = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(R, R, Bitmap.Config.ARGB_8888);
        }


        Matrix matrix = new Matrix();
//        matrix.setTranslate(toTransform.getWidth() / 2, -toTransform.getHeight() / 2);
//        matrix.setTranslate(-(toTransform.getWidth()-R)/2,-(toTransform.getHeight()-R)/2);

        BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        Canvas canvas = new Canvas(result);
//        canvas.drawCircle(R / 2, R / 2, R / 2, paint);
        canvas.drawCircle(R / 2, R / 2, R / 2, paint);
        return result;
    }
}