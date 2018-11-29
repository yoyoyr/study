package com.test.viewpagedemo.OkHttp;

import android.content.Context;

import com.test.viewpagedemo.LoggerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

import okhttp3.ResponseBody;

public class FileUtils {

    public static File createFile(Context context, String fileName, String type) {

        File file = null;
//        String state = context.getCacheDir().getAbsolutePath();
//
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//
//            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + fileName + "." + type);
//        } else {
        file = new File(context.getCacheDir().getAbsolutePath() + fileName + "." + type);
        LoggerUtils.LOGD("file = " + file.getAbsolutePath());
//        }
        return file;
    }

    public static String writeFile2Disk(ResponseBody body, File file) throws Exception {

        long currentLength = 0;
        OutputStream os = null;

        InputStream is = body.byteStream();
        long totalLength = body.contentLength();

        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static final int BUFSIZE = 1024 * 8;

    public static String mergeFiles(String outFile, String[] files) {
        FileChannel outChannel = null;
        LoggerUtils.LOGD("Merge " + Arrays.toString(files) + " into " + outFile);
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for (String f : files) {
                Charset charset = Charset.forName("utf-8");
                CharsetDecoder chdecoder = charset.newDecoder();
                CharsetEncoder chencoder = charset.newEncoder();
                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
                CharBuffer charBuffer = chdecoder.decode(bb);
                ByteBuffer nbuBuffer = chencoder.encode(charBuffer);
                while (fc.read(nbuBuffer) != -1) {
                    bb.flip();
                    nbuBuffer.flip();
                    outChannel.write(nbuBuffer);
                    bb.clear();
                    nbuBuffer.clear();
                }
                fc.close();
            }
            LoggerUtils.LOGD("Merged!! ");
            return outFile;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return "";
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}