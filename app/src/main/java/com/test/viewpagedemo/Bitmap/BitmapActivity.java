package com.test.viewpagedemo.Bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * bitmap大小计算：
 * width*height*每个像素的大小 = size
 * 其中，RGB_565为每个像素2byte  ARGB_8888 4byte。
 * 所以压缩图片有两个方向
 * 替换图片格式，缩小每个像素点的大小。但是RGB不支持透明
 * 根据加载图片的空间，缩放图片的大小。
 * <p>
 * options
 * <p>
 * 1.public boolean inJustDecodeBounds　//如果设置为true，不获取图片，不分配 内存，但会返回图片的高度宽度信息。
 * 如果将这个值置为true，那么在解码的时候将不会返回bitmap，只会返回这个bitmap 的尺寸。这个属性的目的是，如果你只想知道一个bitmap的尺寸，但又不想将其加载到内存时。这是一个非常有用的属性。
 * public int inSampleSize　//图片缩放的倍数
 * 这个值是一个int，当它小于1的时候，将会被当做1处理，如果大于1，那么就会按照比例（1 / inSampleSize）缩小bitmap的宽和高、降低分辨率，大于1时这个值将会被处置为2的倍数。例如，width=100，height=100，inSampleSize=2，那么就会将bitmap处理为，width=50，height=50，宽高降为1 / 2，像素数降为1 / 4。
 * <p>
 * 2.public int outWidth　//获取图片的宽度值
 * <p>
 * 3.public int outHeight　//获取图片的高度值
 * <p>
 * 4.public Bitmap.Config inPreferredConfig　 //设置解码器
 * 这个值是设置色彩模式，默认值是ARGB_8888，在这个模式下，一个像素点占用4bytes空间，一般对透明度不做要求的话，一般采用RGB_565模式，这个模式下一个像素点占用2bytes。
 */
public class BitmapActivity extends AppCompatActivity {

    @BindView(R.id.bitmap)
    ImageView imageView;

    @BindView(R.id.gif)
    GifImageView gifImageView;

    GifDrawable gifDrawable;

    Bitmap bitmap;
    String jpg = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.play})
    void play() {
        try {
            gifDrawable = new GifDrawable(getAssets().openFd("big.gif"));
            gifImageView.setImageDrawable(gifDrawable);
            gifDrawable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //一般处理方式是，先将 options.inJustDecodeBounds = true，测量出图片的宽高，然后计算inSimpleSize的值。
    //通过控制采样率的好处是，不用将原始bitmap加载就可以缩放图片，节省内存
    @OnClick({R.id.sampleSize})
    void sampleSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iamge, options);

        LoggerUtils.LOGD("bitmap size = " + bitmap.getByteCount() + ",width = " + bitmap.getWidth()
                + ",height = " + bitmap.getHeight());
    }

    @OnClick({R.id.qualityDown})
    void qualityDown() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iamge, options);

        LoggerUtils.LOGD("bitmap size = " + bitmap.getByteCount() + ",width = " + bitmap.getWidth()
                + ",height = " + bitmap.getHeight());
    }


    @OnClick({R.id.loadImage})
    void load() {
        LoggerUtils.LOGD("load image");
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iamge);
        LoggerUtils.LOGD("bitmap size = " + bitmap.getByteCount() + ",width = " + bitmap.getWidth()
                + ",height = " + bitmap.getHeight());
        imageView.setImageBitmap(bitmap);
    }

    @OnClick(R.id.bigBitmap)
    void bigBitmap() {
        InputStream in = null;
        try {
            in = getAssets().open("map.jpg");
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(in, false);
            Rect rect = new Rect(354, 216, 708, 432);
            Bitmap map = bitmapRegionDecoder.decodeRegion(rect, new BitmapFactory.Options());
            imageView.setImageBitmap(map);
            LoggerUtils.LOGD("image width = " + bitmapRegionDecoder.getWidth()
                    + ",height = " + bitmapRegionDecoder.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.release)
    void release() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

//    @OnClick({R.id.RGB})
//    void RGB565() {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iamge, options);
//
//        LoggerUtils.LOGD("bitmap size = " + bitmap.getByteCount() + ",width = " + bitmap.getWidth()
//                + ",height = " + bitmap.getHeight());
//    }
//
//    @OnClick({R.id.creatScaleBitmap})
//    void createScaleBitmap() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iamge);
//
//        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1280, 720, true);
//
//        LoggerUtils.LOGD("bitmap size = " + bitmap.getByteCount() + ",width = " + bitmap.getWidth()
//                + ",height = " + bitmap.getHeight());
//        LoggerUtils.LOGD("matrixBitmap size = " + scaleBitmap.getByteCount() + ",width = " + scaleBitmap.getWidth()
//                + ",height = " + scaleBitmap.getHeight());
//    }
//
//    //通过缩放控制图片大小
//    @OnClick({R.id.matrix})
//    void matrix() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iamge);
//
//        Matrix matrix = new Matrix();
//        matrix.setScale(0.5f, 0.5f);
//        Bitmap matrixBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//
//
//        LoggerUtils.LOGD("bitmap size = " + bitmap.getByteCount() + ",width = " + bitmap.getWidth()
//                + ",height = " + bitmap.getHeight());
//        LoggerUtils.LOGD("matrixBitmap size = " + matrixBitmap.getByteCount() + ",width = " + matrixBitmap.getWidth()
//                + ",height = " + matrixBitmap.getHeight());
//    }

}
