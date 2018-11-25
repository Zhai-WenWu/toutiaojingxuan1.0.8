package com.deshang.ttjx.wxapi;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mvp.cn.util.Md5Util;

public class DownloadWebImgTask extends AsyncTask<String, String, Void> {
    public static final String TAG = "DownloadWebImgTask";
    private Context context;

    public DownloadWebImgTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection urlCon = null;
        if (params.length == 0)
            return null;
        File dir = context.getCacheDir();
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = null;
        for (int i = 0; i < params.length; i++) {
            String urlStr = params[i];
            try {
                if (urlStr == null || urlStr.equals("")
                        || urlStr.equals("null")) {
                    continue;
                }
                String fileName = Md5Util.getMD5s(urlStr);
                file = new File(context.getCacheDir().getPath() + "/"
                        + fileName);
                if (file.exists()) {
                    continue;
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                url = new URL(urlStr);
                urlCon = (HttpURLConnection) url.openConnection();
                urlCon.setRequestMethod("GET");
                urlCon.setDoInput(true);
                urlCon.connect();

                inputStream = urlCon.getInputStream();
                outputStream = new FileOutputStream(file);
                byte buffer[] = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bufferLength);
                }
                outputStream.flush();
                publishProgress(urlStr, "file://" + file.getPath());
            } catch (Exception e) {
                e.printStackTrace();
                if (file != null) {
                    // 如果下载失败就删掉
                    file.delete();
                }
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String isExist(Context context, String url) {
        String fileName = Md5Util.getMD5s(url);
        File file = new File(context.getCacheDir().getPath() + "/"
                + fileName);
        if (file.exists()) {
            return file.getPath();
        }
        return null;
    }
}
