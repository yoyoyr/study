package com.test.viewpagedemo.Bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.test.viewpagedemo.LoggerUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {
    /**
     * 第一种：质量压缩法
     *
     * @param image   目标原图
     * @param maxSize 最大的图片大小
     * @return bitmap，注意可以测试以下压缩前后bitmap的大小值
     */
    public static Bitmap compressImage(Bitmap image, long maxSize) {
        int byteCount = image.getByteCount();
        LoggerUtils.LOGV("yc压缩图片", "压缩前大小" + byteCount);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = null;
        // 质量压缩方法，options的值是0-100，这里100表示原来图片的质量，不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        // 循环判断如果压缩后图片是否大于maxSize,大于继续压缩
        while (baos.toByteArray().length > maxSize) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10，当为1的时候停止，options<10的时候，递减1
            if (options == 1) {
                break;
            } else if (options <= 10) {
                options -= 1;
            } else {
                options -= 10;
            }
        }
        byte[] bytes = baos.toByteArray();
        if (bytes.length != 0) {
            // 把压缩后的数据baos存放到bytes中
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            int byteCount1 = bitmap.getByteCount();
            LoggerUtils.LOGV("yc压缩图片", "压缩后大小" + byteCount1);
        }
        return bitmap;
    }


    /**
     * 第一种：质量压缩法
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize, final boolean recycle) {
        if (src == null || src.getWidth() == 0 || src.getHeight() == 0 || maxByteSize <= 0) {
            return null;
        }
        LoggerUtils.LOGV("yc压缩图片", "压缩前大小" + src.getByteCount());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {// 最好质量的不大于最大字节，则返回最佳质量
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节，则返回最差质量
                bytes = baos.toByteArray();
            } else {
                // 二分法寻找最佳质量
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        LoggerUtils.LOGV("yc压缩图片", "压缩后大小" + bitmap.getByteCount());
        return bitmap;
    }


    /**
     * 第一种：质量压缩法
     *
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, @IntRange(from = 0, to = 100) final int quality, final boolean recycle) {
        if (src == null || src.getWidth() == 0 || src.getHeight() == 0) {
            return null;
        }
        LoggerUtils.LOGV("yc压缩图片", "压缩前大小" + src.getByteCount());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        LoggerUtils.LOGV("yc压缩图片", "压缩后大小" + bitmap.getByteCount());
        return bitmap;
    }


    /**
     * 第二种：按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int sampleSize, final boolean recycle) {
        if (src == null || src.getWidth() == 0 || src.getHeight() == 0) {
            return null;
        }
        Log.i("yc压缩图片", "压缩前大小" + src.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Log.i("yc压缩图片", "压缩后大小" + bitmap.getByteCount());
        return bitmap;
    }


    /**
     * 第二种：按采样大小压缩
     *
     * @param src       源图片
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param recycle   是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int maxWidth, final int maxHeight, final boolean recycle) {
        if (src == null || src.getWidth() == 0 || src.getHeight() == 0) {
            return null;
        }
        Log.i("yc压缩图片", "压缩前大小" + src.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Log.i("yc压缩图片", "压缩后大小" + bitmap.getByteCount());
        return bitmap;
    }

    /**
     * 计算获取缩放比例inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    /**
     * 第三种：按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(final Bitmap src, final int newWidth, final int newHeight, final boolean recycle) {
        return scale(src, newWidth, newHeight, recycle);
    }

    public static Bitmap compressByScale(final Bitmap src, final float scaleWidth, final float scaleHeight, final boolean recycle) {
        return scale(src, scaleWidth, scaleHeight, recycle);
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放后的图片
     */
    private static Bitmap scale(final Bitmap src, final float scaleWidth, final float scaleHeight, final boolean recycle) {
        if (src == null || src.getWidth() == 0 || src.getHeight() == 0) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }


    /**
     * 将图片的四角圆弧化
     *
     * @param roundPixels 弧度
     * @param half        （上/下/左/右）半部分圆角
     * @return
     */
    public static Drawable getRoundCornerImage(Context context, int resId, int roundPixels, HalfType half) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId); // 先从资源中把背景图获取出来
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建一个和原始图片一样大小的位图
        Canvas canvas = new Canvas(roundConcerImage);//创建位图画布
        Paint paint = new Paint();//创建画笔

        Rect rect = new Rect(0, 0, width, height);//创建一个和原始图片一样大小的矩形
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);// 抗锯齿

        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);//画一个基于前面创建的矩形大小的圆角矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置相交模式
        canvas.drawBitmap(bitmap, null, rect, paint);//把图片画到矩形去

        switch (half) {
            case LEFT:
                return new BitmapDrawable(context.getResources(), Bitmap.createBitmap(roundConcerImage, 0, 0, width - roundPixels, height));
            case RIGHT:
                return new BitmapDrawable(context.getResources(), Bitmap.createBitmap(roundConcerImage, width - roundPixels, 0, width - roundPixels, height));
            case TOP: // 上半部分圆角化 “- roundPixels”实际上为了保证底部没有圆角，采用截掉一部分的方式，就是截掉和弧度一样大小的长度
                return new BitmapDrawable(context.getResources(), Bitmap.createBitmap(roundConcerImage, 0, 0, width, height - roundPixels));
            case BOTTOM:
                return new BitmapDrawable(context.getResources(), Bitmap.createBitmap(roundConcerImage, 0, height - roundPixels, width, height - roundPixels));
            case ALL:
                return new BitmapDrawable(context.getResources(), roundConcerImage);
            default:
                return new BitmapDrawable(context.getResources(), roundConcerImage);
        }
    }


    public static void saveBitmap(Context context, byte[] datas, float percent, String name) {
        String newFilePath = context.getFilesDir().getAbsolutePath() + "/"
                + name + ".png";
        File file = new File(newFilePath);
        FileOutputStream fos = null;
        Bitmap bm = null;
        try {

            //竖屏拍照需要旋转90度
            Bitmap toTransform = toturn(datas, 90);
            BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bm = Bitmap.createBitmap(toTransform.getWidth(), (int) (toTransform.getHeight() * percent), Bitmap.Config.ARGB_8888);

            LoggerUtils.LOGV("width = " + bm.getWidth() + ",height = " + bm.getHeight() + ",toTransform.getWidth() = " + toTransform.getWidth()
                    + ",toTransform.getHeight() = " + toTransform.getHeight());
            Canvas canvas = new Canvas(bm);
            Paint paint = new Paint();
            paint.setShader(bitmapShader);
            paint.setAntiAlias(true);
            canvas.drawRect(0, 0, bm.getWidth(), bm.getHeight(), paint);
            file.createNewFile();
            fos = new FileOutputStream(file);
            LoggerUtils.LOGV("path=" + file.getAbsoluteFile());
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            // width = PAPER_WIDTH;
        } catch (IOException e) {
            LoggerUtils.LOGE(e);
        } finally {

            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * view截图
     *
     * @return
     */
    public static void viewShot(@NonNull final View v, @Nullable final String name) {
        if (null == v) {
            LoggerUtils.LOGE("view is null");
            return;
        }
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                // 核心代码start
                Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
//                v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
                v.draw(c);

                FileOutputStream fos = null;
                try {
                    String newFilePath = v.getContext().getFilesDir().getAbsolutePath() + "/"
                            + name + ".png";
                    File file = new File(newFilePath);
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    LoggerUtils.LOGV("path=" + file.getAbsoluteFile());
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public static class Data {
        File file;
        Bitmap bitmap;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Data(File file, Bitmap bitmap) {
            this.file = file;
            this.bitmap = bitmap;
        }
    }

    //旋将旋转后的图片翻转
    public static Bitmap toturn(byte[] datas, int degree) {
        Bitmap img = BitmapFactory.decodeByteArray(datas, 0, datas.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(degree); /*翻转90度*/
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }


    /**
     * 图片圆角规则 eg. TOP：上半部分
     */
    public enum HalfType {
        LEFT, // 左上角 + 左下角
        RIGHT, // 右上角 + 右下角
        TOP, // 左上角 + 右上角
        BOTTOM, // 左下角 + 右下角
        ALL // 四角
    }


}
