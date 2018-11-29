package com.hotfix;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.test.viewpagedemo.LoggerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 校验文件的md5值
 */
public class CustomPatchListener extends DefaultPatchListener {

    private String currentMD5;

    public void setCurrentMD5(String md5Value) {

        this.currentMD5 = md5Value;
    }

    public CustomPatchListener(Context context) {
        super(context);
    }

    /**
     * 添加校验后台下发的文件MD5
     *
     * @param path
     * @param patchMd5
     * @return
     */
    @Override
    protected int patchCheck(String path, String patchMd5) {
        LoggerUtils.LOGD("check md5 ");
//        isFileMD5Matched(path,patchMd5);
        return super.patchCheck(path, patchMd5);
    }

    static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    //检查文件md5是否与原始的匹配
    public static boolean isFileMD5Matched(String filePath, String originalMD5) {
        MessageDigest messagedigest = null;
        try {
            InputStream fis;
            messagedigest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            fis.close();
        } catch (Exception e) {
        }
        return originalMD5.equals(bufferToHex(messagedigest.digest(),
                0, messagedigest.digest().length));
    }


    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
            char c1 = hexDigits[bytes[l] & 0xf];// 取字节中低 4 位的数字转换
            stringbuffer.append(c0);
            stringbuffer.append(c1);
        }
        return stringbuffer.toString();
    }

}
