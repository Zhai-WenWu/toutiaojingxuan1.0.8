package com.deshang.ttjx.framework.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deshang.ttjx.R;


public class SelectImageHelper {

    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITHOUT_CROP = 3025;

    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;

    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;

    /* 用来标识请求裁切 */
    private static final int CROP_REQUEST_CODE = 3020;

    /* 拍照的照片存储位置 */
    public static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/casemeet" + "/images");
    private static File mCurrentPhotoFile;// 照相机拍照得到的图片

    private Activity activity;

    private int aspectX = 1;
    private int aspectY = 1;
    private int outputX = 320;
    private int outputY = 320;

    public SelectImageHelper(Activity activity) {
        this.activity = activity;
    }

    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * 拍照带裁剪
     */
    public void doTakePhoto() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                boolean dd = PHOTO_DIR.mkdirs();// 创建照片的存储目录
                mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
                final Intent intent = getTakePickIntent(mCurrentPhotoFile);
                if (fragment != null) {
                    fragment.startActivityForResult(intent, CAMERA_WITH_DATA);
                } else {
                    activity.startActivityForResult(intent, CAMERA_WITH_DATA);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "没有照相机程序", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activity, "没有SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拍照不裁剪
     */
    public void doTakePhotoWithoutCrop() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                boolean dd = PHOTO_DIR.mkdirs();// 创建照片的存储目录
                mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
                final Intent intent = getTakePickIntent(mCurrentPhotoFile);
                if (fragment != null) {
                    fragment.startActivityForResult(intent, CAMERA_WITHOUT_CROP);
                } else {
                    activity.startActivityForResult(intent, CAMERA_WITHOUT_CROP);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "没有照相机程序", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activity, "没有SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    public Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 从图库选择
     */
    public void doPickPhotoFromGallery() {
        try {
            PHOTO_DIR.mkdirs();
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());

            Intent itPhoto = new Intent(Intent.ACTION_PICK);
            itPhoto.setType("image/*");
            if (fragment != null) {
                fragment.startActivityForResult(itPhoto, PHOTO_PICKED_WITH_DATA);
            } else {
                activity.startActivityForResult(itPhoto, PHOTO_PICKED_WITH_DATA);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "not find photo", Toast.LENGTH_LONG).show();
        }
    }

    public Intent getPhotoPickIntent(File f) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 裁剪图片
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();
        }
        mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));

        if (fragment != null) {
            fragment.startActivityForResult(intent, CROP_REQUEST_CODE);
        } else {
            activity.startActivityForResult(intent, CROP_REQUEST_CODE);
        }
    }

    /**
     * 用当前时间给取得的图片命名
     */
    public String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 处理结果
     *
     * @param requestCode
     * @param data
     * @param onGetPhotoListener
     */
    @SuppressLint("NewApi")
    public void doResult(int requestCode, int resultCode, Intent data, OnGetPhotoListener onGetPhotoListener) {
        if (requestCode == CAMERA_WITHOUT_CROP) {// 拍照返回的
            if (mCurrentPhotoFile != null) {
                if (mCurrentPhotoFile.getTotalSpace() == 0) {
                    return;
                }
                if (onGetPhotoListener != null) {
                    onGetPhotoListener.onGetPhoto(mCurrentPhotoFile);
                }
            }
        } else if (requestCode == CAMERA_WITH_DATA) {// 拍照返回的
            if (mCurrentPhotoFile != null) {
                startPhotoZoom(Uri.fromFile(mCurrentPhotoFile));
            }
        } else if (requestCode == PHOTO_PICKED_WITH_DATA) {// 从图库选择的2
            if (data == null) {
                return;
            }
            startPhotoZoom(data.getData());
        } else if (requestCode == CROP_REQUEST_CODE) {// 裁切返回
            String path = mCurrentPhotoFile.getAbsolutePath();
            if (mCurrentPhotoFile.getTotalSpace() == 0) {
                return;
            }
            if (path != null) {
                File uploadFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
                if (onGetPhotoListener != null) {
                    onGetPhotoListener.onGetPhoto(mCurrentPhotoFile);
                }
            }
        }
    }


    /**
     * 选择上传头像对话框
     */
    @SuppressLint("NewApi")
    public void showChooseImgDialog() {
        final StringListDialog dialog = new StringListDialog(activity, R.style.dialog_style);
        List<String> itemList = new ArrayList<String>();
        itemList.add("相机拍摄");
        itemList.add("手机相册");
        itemList.add("取消");
        dialog.setData(itemList);
        dialog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:// 拍照上传
                        doTakePhoto();
                        dialog.dismiss();
                        break;
                    case 1:// 从gallery选择
                        doPickPhotoFromGallery();
                        dialog.dismiss();
                        break;
                    case 2:// 取消
                        dialog.dismiss();
                        break;
                }
            }
        });
        dialog.show();
    }


    /**
     * 裁剪比例,输出宽高参数
     *
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     */
    public void setCropParams(int aspectX, int aspectY, int outputX, int outputY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.outputX = outputX;
        this.outputY = outputY;
    }


    /**
     * 得到照片文件的回调
     *
     * @author wdf
     *         <p>
     *         2014-12-16
     */
    public interface OnGetPhotoListener {
        void onGetPhoto(File photoFile);
    }

}