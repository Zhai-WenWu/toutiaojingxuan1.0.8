package mvp.cn.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.math.BigDecimal;

public class StringUtil {

    /**
     * 判断字符串是否为null或�?空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        boolean result = false;
        if (null == str || "".equals(str.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * 如果i小于10，添�?后生成string
     *
     * @param i
     * @return
     */
    public static String addZreoIfLessThanTen(int i) {

        String string = "";
        int ballNum = i + 1;
        if (ballNum < 10) {
            string = "0" + ballNum;
        } else {
            string = ballNum + "";
        }
        return string;
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        if (null != string && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 去掉�?��字符串中的所有的单个空格" "
     *
     * @param string
     */
    public static String replaceSpaceCharacter(String string) {
        if (null == string || "".equals(string)) {
            return "";
        }
        return string.replace(" ", "");
    }

    /**
     * 获取小数位为6位的经纬�?
     *
     * @param string
     * @return
     */
    public static String getStringLongitudeOrLatitude(String string) {
        if (StringUtil.isNullOrEmpty(string)) {
            return "";
        }
        if (string.contains(".")) {
            String[] splitArray = string.split("\\.");
            if (splitArray[1].length() > 6) {
                String substring = splitArray[1].substring(0, 6);
                return splitArray[0] + "." + substring;
            } else {
                return string;
            }
        } else {
            return string;
        }
    }

    /**
     * 银行卡号截取替换
     * @param str
     * @return
     */
    public static String StringReplace(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            return "";
        }
        //字符串截取
        String s = "";
        if(str.length() == 16){
           s = str.substring(4, 12);
        }else{
            s = str.substring(4, 15);
        }
        //字符串替换
        String s1 = str.replace(s," **** ");
        return s1;
    }

    /**
     * 银行卡截取替换
     * @param str
     * @return
     */
    public static String StringReplaceCard(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            return "";
        }
        //字符串截取
        String s = "";
        if(str.length() == 16){
            s = str.substring(4, 12);
        }else{
            s = str.substring(4, 15);
        }
        //字符串替换
        String s1 = str.replace(s," *********** ");
        return s1;
    }
    /**
     * 取整数
     * @param str
     * @return
     */
    public static int getIntValue(String str)
    {
        int r = 0;
        if(str!=null && str.length()!=0)
        {
            StringBuffer bf = new StringBuffer();

            char[] chars = str.toCharArray();
            for(int i=0;i<chars.length;i++)
            {
                char c = chars[i];
                if(c>='0' && c<='9')
                {
                    bf.append(c);
                }
                else if(c==',')
                {
                    continue;
                }
                else
                {
                    if(bf.length()!=0)
                    {
                        break;
                    }
                }
            }
            try
            {
                r = Integer.parseInt(bf.toString());
            }
            catch(Exception e)
            {}
        }
        return r;
    }

    /**
     * 双精度值
     * @param str
     * @return
     */
    public static String getDoubleData(String str){
        if (StringUtil.isNullOrEmpty(str)) {
            return "";
        }
        BigDecimal bd = new BigDecimal(str);
        String data = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return data;
    }

    /**
     * 截取时间
     * @param str
     * @return
     */
    public static String getCutOutData(String str){
        if (StringUtil.isNullOrEmpty(str)) {
            return "";
        }
       String strs = str.substring(0, 10);
        return strs;
    }

    /**
     * 截取时间
     * @param str
     * @return
     */
    public static String getCutOutNineData(String str){
        if (StringUtil.isNullOrEmpty(str)) {
            return "";
        }
        String strs = str.substring(0,9);
        return strs;
    }

    public static int getVersion(Context context) {
        int version = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


}
