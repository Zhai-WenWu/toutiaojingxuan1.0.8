package mvp.cn.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hyn on 2017/2/18.
 * 内容：
 */

public class Md5Util {
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    public static String getMD5x100(String content) {
        String s1 = content;
        for (int i = 0; i < 100; i++) {
            s1 = getMD5(s1);
        }
        return s1;
    }

    /** */
    /**
     * 对一段String生成MD5加密信息
     *
     * @param message
     *            要加密的String
     * @return 生成的MD5信息
     */
    public static String getMD5s(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(message.getBytes("utf-8"));
            String digestCode = byteToHexStringSingle(b);
            return digestCode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 独立把byte[]数组转换成十六进制字符串表示形式
     *
     * @author Bill
     * @create 2010-2-24 下午03:26:53
     * @since
     * @param byteArray
     * @return
     */
    public static String byteToHexStringSingle(byte[] byteArray) {
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }
}
