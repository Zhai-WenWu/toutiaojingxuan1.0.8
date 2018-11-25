package com.deshang.ttjx.framework.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by L on 2017/12/5.
 */

public class ProjectUtils {
    public static final String share_img_path = Environment.getExternalStorageDirectory() + "/share.png";

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime(double time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String times = format.format(time);
        return times;
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime1(double time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String times = format.format(time);
        return times;
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime2(double time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String times = format.format(time);
        return times;
    }

    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd");
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    public static String timedate2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm");
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    public static String timedateHHmm(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * 保存bitmap到本地
     *
     * @param bmp
     * @param bitName
     * @return
     * @throws IOException
     */
    public static boolean saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        String time = System.currentTimeMillis() + "";
        File dirFile = new File("./sdcard/DCIM/Camera/");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File("./sdcard/DCIM/Camera/" + bitName + time + ".png");
        boolean flag = false;
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /* 保留double类型小数后两位，不四舍五入，直接取小数后两位 比如：10.1269 返回：10.12
     *
     * @param doubleValue
     * @return
     */
    public static String calculateProfit(double doubleValue) {
        // 保留4位小数
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0000");
        String result = df.format(doubleValue);

        // 截取第一位
        String index = result.substring(0, 1);

        if (".".equals(index)) {
            result = "0" + result;
        }

        // 获取小数 . 号第一次出现的位置
        int inde = firstIndexOf(result, ".");

        // 字符串截断
        return result.substring(0, inde + 3);
    }

    /**
     * 查找字符串pattern在str中第一次出现的位置
     *
     * @param str
     * @param pattern
     * @return
     */
    public static int firstIndexOf(String str, String pattern) {
        for (int i = 0; i < (str.length() - pattern.length()); i++) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j))
                    break;
                j++;
            }
            if (j == pattern.length())
                return i;
        }
        return -1;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 小数点精确到两位.不足补0  四舍五入
     *
     * @param p
     * @return
     */
    public static String getTwo(double p) {

        BigDecimal bigDecimal = new BigDecimal(p);
        //这里的 2 就是你要保留几位小数。
        double f1 = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        DecimalFormat format = new DecimalFormat("##0.00");
        String formatted = format.format(f1);

        return formatted;
    }

    /**
     * 小数点精确到两位.不足补0  四舍五入
     *
     * @param p
     * @return
     */
    public static String getSix(double p) {

        BigDecimal bigDecimal = new BigDecimal(p);
        //这里的 2 就是你要保留几位小数。
        double f1 = bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

        DecimalFormat format = new DecimalFormat("##0.0000");
        String formatted = format.format(f1);

        return formatted;
    }

    /**
     * dp转换成px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Context context, EditText editText) {
        editText.clearFocus();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);// 显示输入法
    }

    /**
     * EditText隐藏软键盘
     */
    public static void closeSoftInputFromWindow(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    // 时间对比
    public static int compare_date(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断版本大小
     *
     * @param beanVersion
     * @param mVersion
     * @return
     */
    public static boolean check(String beanVersion, String mVersion) {
        boolean result = false;
        beanVersion = beanVersion.replace(".", ";");
        mVersion = mVersion.replace(".", ";");
        System.out.println(beanVersion + "////////////" + mVersion);
        String[] bean_version = beanVersion.split(";");
        String[] m_Version = mVersion.split(";");
        for (int i = 0; i < m_Version.length; i++) {
            int b = Integer.parseInt(bean_version[i]);
            int m = Integer.parseInt(m_Version[i]);
            if (b < m) {
                result = false;
                break;
            } else if (b > m) {
                result = true;
                break;
            } else {
                if (i < m_Version.length - 1) {
                    continue;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }


    /**
     * bitmap转成byte
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length / 1024 > 32) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            output.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * url->bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static boolean savePicture(Bitmap bitmap) {
        boolean isDone = false;
        if (null == bitmap)
            return false;
        String pictureName = share_img_path;
        File file = new File(pictureName);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            isDone = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            isDone = false;
        } catch (IOException e) {
            e.printStackTrace();
            isDone = false;
        }

        return isDone;
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap urlToBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    // 文件存储
    private static File updateDir = null;
    private static File updateFile = null;

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {  // 判断sd卡的状态，是否可以被读写，MEDIA_MOUNTED可以被读写
            updateDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/toutiaojingxuan");
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            updateFile = new File(updateDir, "ttjx_update.apk");
            if (!updateFile.exists()) {
                try {
                    if (!updateFile.createNewFile()) {
                        System.out.println("File already exists");
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }

        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);

            //获取到文件的大小
            pd.setMax(conn.getContentLength() / 1024);
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(updateFile);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total / 1024);
            }
            fos.close();
            bis.close();
            is.close();
            return updateFile;
        } else {
            return null;
        }
    }

}
