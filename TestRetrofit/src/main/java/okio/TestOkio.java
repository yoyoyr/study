package okio;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import Gson.Book;

/**
 * segment 双向链表  segmentPool 缓存segment
 * okio库
 * 1.以空间换时间
 * 2.多了超时检查机制
 * 3.操作相对简单
 */
public class TestOkio {

    public static void main(String[] args) throws IOException {
//        File file = new File("text.txt");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//        BufferedSource source = Okio.buffer(Okio.source(file));
//        byte[] bs = source.readByteArray();
//
//
//        System.out.println(bytesToHexFun1(bs));
//        source.close();
//
//        BufferedSink sink = Okio.buffer(Okio.sink(file));
//        byte[] b = new byte[]{
//                (byte) 0xe6, (byte) 0x9d, (byte) 0xa8, (byte) 0xe8, (byte) 0x8d, (byte) 0xa3
//        };
//        sink.write(b);
//        sink.writeUtf8("yr");
//        sink.flush();
//        sink.close();
        Book book1 = new Book();
        Book book2 = new Book();
        System.out.println(book1.getClass() ==  book2.getClass());

//        File file = new File("text.txt");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        File tmp = new File("tmp.txt");
//        if (!tmp.exists()) {
//            tmp.createNewFile();
//        }
//        BufferedSource bufferedSource = Okio.buffer(Okio.source(file));
//        BufferedSink bufferedSink = Okio.buffer(Okio.sink(tmp));
////              一个segment的大小
//        byte[] t = new byte[1024];
//        int count;
//        while ((count = bufferedSource.read(t)) != -1) {
//            bufferedSink.write(t, 0, count);
//            bufferedSink.flush();
//            bufferedSource.buffer().flush();
//
//        }
//
//        System.out.println(switchBalance("990.123456789"));
//        System.out.println(switchBalance("999.123456789"));
//        System.out.println(switchBalance("999999.123456789"));
//        System.out.println(switchBalance("0.00000000000"));

    }


    /**
     * 除eos外，默认余额保存小数点后8位
     * >999,4位
     * >999,999，2位
     * >999,999,999，不保存小数
     *
     * @param balanace
     * @return
     */
    public static String switchBalance(String balanace) {
        BigDecimal bigDecimal = new BigDecimal(balanace);

        if (bigDecimal.doubleValue() > 999999999) {
            return bigDecimal.setScale(0, RoundingMode.HALF_DOWN).toString();
        } else if (bigDecimal.doubleValue() > 999999) {
            return bigDecimal.setScale(2, RoundingMode.HALF_DOWN).toString();
        } else if (bigDecimal.doubleValue() > 999) {
            return bigDecimal.setScale(4, RoundingMode.HALF_DOWN).toString();
        } else {
            String rlt =  bigDecimal.setScale(8, RoundingMode.HALF_DOWN).toString();
            if("0E-8".equalsIgnoreCase(rlt)){
                rlt = "0.00000000";
            }
            return rlt;
        }
    }

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 方法一：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun1(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }

            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }

        return new String(buf);
    }

}
