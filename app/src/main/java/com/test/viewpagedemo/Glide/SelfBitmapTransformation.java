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
import com.test.viewpagedemo.LoggerUtils;

public class SelfBitmapTransformation extends BitmapTransformation {
    public SelfBitmapTransformation(Context context) {
        super(context);
        LoggerUtils.LOGD("new self ");
    }

    // https://github.com/wasabeef/glide-transformations   各种变换效果实现
    @Override
    protected Bitmap transform(BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        LoggerUtils.LOGD("out width = " + outWidth + ",out height = " + outHeight);
        int r = Math.min(toTransform.getWidth(), toTransform.getHeight());

        //作色器。转换后的图片在着色器基础上做裁剪
        BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //平移到图片的中心
        Matrix matrix = new Matrix();
        LoggerUtils.LOGD("tranlate x = "+(-(toTransform.getWidth()-r)/2)+",y = "+(-(toTransform.getHeight()-r)/2));
        matrix.setTranslate(-(toTransform.getWidth()-r)/2,-(toTransform.getHeight()-r)/2);
        bitmapShader.setLocalMatrix(matrix);

        //创建新的bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);

        canvas.drawCircle(r / 2,r / 2, r / 2, paint);
        return bitmap;
    }

    @NonNull
    @Override
    public String getId() {
        LoggerUtils.LOGD("get id");
        return "com.test.viewpagedemo.Glide.SelfBitmapTransformation1";
    }
}
