package entry;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * AES加解密工具
 * Created by steadyjack on 2018/2/9.
 */
public class EncryptUtil {

    /**
     * 明文 "{"accountPrivateKey":"5J7mBfHhBznU3KsD6wDKVFYEKY6ak8k8PLGCtTftgcKh3vDBQB8","isEncrypted":true}"
     * 密码 wujunchuan
     * 密文 U2FsdGVkX19O6/EcPFWjfKWNeWz2L6bEHntBHOwhpaYpd+qvTSmr6/AUxh99k9Kf/HzgpCw0tPcy26+pYjJgE5xdqdSav/YO9aw/YSW7FXtvGB023nHPZvlrG1IuNSClEPYOWuMhGJPchN6g7DaXTA==
     */
    public static void main(String[] args) throws Exception {
        //密钥 加密内容(对象序列化后的内容-json格式字符串)
//        String content = "{\"accountPrivateKey\":\"5J7mBfHhBznU3KsD6wDKVFYEKY6ak8k8PLGCtTftgcKh3vDBQB8\",\"isEncrypted\":true}";
//        String entryContent = "U2FsdGVkX19O6/EcPFWjfKWNeWz2L6bEHntBHOwhpaYpd+qvTSmr6/AUxh99k9Kf/HzgpCw0tPcy26+pYjJgE5xdqdSav/YO9aw/YSW7FXtvGB023nHPZvlrG1IuNSClEPYOWuMhGJPchN6g7DaXTA==";
//        String dencryptText = decrypt("U2FsdGVkX19PYNHIMJHLZZkei6+yvZLRbpcj6y4V/hGksh3Yzt4VmwozLEvgnPOrqni5IUHLumcZwrFWJ+w9ftva4yV2Zzp9Imjq8lK2cbs1Ef2ckCKMBAES1tI+Oh4puydclhmb9ef8i4oHQM8uGQ==", "wujunchuan");
//        String encryptText = encrypt(content, "wujunchuan");
//        System.out.println(String.format("明文：%s \n解密结果：%s", content, dencryptText));
//        System.out.println(String.format("加密结果：%s ", encryptText));

//        String ricardian = "adff {{from}} 123";
//        String key = "from";
//        String s = "\\{\\{\\s*" + key + "\\s*}}";
//        System.out.println(ricardian.replaceAll(s, "yoyo"));

        String memo = "";
        String data = memo.split("-")[0];
        System.out.println(data);
    }

    /**
     * OpenSSL's magic initial bytes.
     */
    private static final String SALTED_STR = "Salted__";
    private static final byte[] SALTED_MAGIC = SALTED_STR.getBytes(StandardCharsets.US_ASCII);


    static String encryptAndURLEncode(String password, String clearText) throws Exception {
        String encrypted = encrypt(password, clearText);
        return URLEncoder.encode(encrypted, StandardCharsets.UTF_8.name());
    }

    /**
     * @param password The password / key to encrypt with.
     * @param data     The data to encrypt
     * @return A base64 encoded string containing the encrypted data.
     */
    static String encrypt(String clearText, String password) throws Exception {

        System.out.println("context = " + clearText);
        System.out.println("password = " + password);
        final byte[] pass = password.getBytes(StandardCharsets.US_ASCII);
        final byte[] salt = (new SecureRandom()).generateSeed(8);
        final byte[] inBytes = clearText.getBytes(StandardCharsets.UTF_8);

        final byte[] passAndSalt = array_concat(pass, salt);
        byte[] hash = new byte[0];
        byte[] keyAndIv = new byte[0];
        for (int i = 0; i < 3 && keyAndIv.length < 48; i++) {
            final byte[] hashData = array_concat(hash, passAndSalt);
            final MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(hashData);
            System.out.println("hashData = " + byteToHex((hashData)) + ",hash = " + byteToHex(hash));
            keyAndIv = array_concat(keyAndIv, hash);
        }

        final byte[] keyValue = Arrays.copyOfRange(keyAndIv, 0, 32);
        final byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);
        final SecretKeySpec key = new SecretKeySpec(keyValue, "AES");

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] data = cipher.doFinal(inBytes);
        data = array_concat(array_concat(SALTED_MAGIC, salt), data);
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * @param password
     * @param source   The encrypted data
     * @return
     * @see http://stackoverflow.com/questions/32508961/java-equivalent-of-an-openssl-aes-cbc-encryption  for what looks like a useful answer.  The not-yet-commons-ssl also has an implementation
     */
    static String decrypt(String source, String password) throws Exception {
        final byte[] pass = password.getBytes(StandardCharsets.US_ASCII);

        final byte[] inBytes = Base64.getDecoder().decode(source);

        final byte[] shouldBeMagic = Arrays.copyOfRange(inBytes, 0, SALTED_MAGIC.length);
        if (!Arrays.equals(shouldBeMagic, SALTED_MAGIC)) {
            throw new IllegalArgumentException("Initial bytes from input do not match OpenSSL SALTED_MAGIC salt value.");
        }

        final byte[] salt = Arrays.copyOfRange(inBytes, SALTED_MAGIC.length, SALTED_MAGIC.length + 8);

        final byte[] passAndSalt = array_concat(pass, salt);

        byte[] hash = new byte[0];
        byte[] keyAndIv = new byte[0];
        for (int i = 0; i < 3 && keyAndIv.length < 48; i++) {
            final byte[] hashData = array_concat(hash, passAndSalt);
            final MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(hashData);
            keyAndIv = array_concat(keyAndIv, hash);
        }

        final byte[] keyValue = Arrays.copyOfRange(keyAndIv, 0, 32);
        final SecretKeySpec key = new SecretKeySpec(keyValue, "AES");

        final byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        final byte[] clear = cipher.doFinal(inBytes, 16, inBytes.length - 16);
        return new String(clear, StandardCharsets.UTF_8);
    }


    private static byte[] array_concat(final byte[] a, final byte[] b) {
        final byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }


    public static String byteToHex(byte[] bytes) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

}