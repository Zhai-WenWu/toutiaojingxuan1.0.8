package com.deshang.ttjx.framework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by 梁 on 2017/12/15.
 */

public class EmulatorCheck {

    private static EmulatorCheck emulatorCheck;

    public static EmulatorCheck getInstance() {
        if (emulatorCheck == null) {
            emulatorCheck = new EmulatorCheck();
        }
        return emulatorCheck;
    }

    private static String[] known_pipes = {"/dev/socket/qemud", "/dev/qemu_pipe"};

    /*
     *  第一种 检测模拟器上特有的几个文件
     */
    public boolean checkPipes() {
        for (int i = 0; i < known_pipes.length; i++) {
            String pipes = known_pipes[i];
            File qemu_socket = new File(pipes);
            if (qemu_socket.exists()) {
                Log.v("Result:", "Find pipes!");
                return true;
            }
        }
        Log.v("Result:", "Not Find pipes!");
        return false;
    }

    /*
    * 第二种  检测手机号 是不是以下 号码
    * */
    private static String[] known_numbers = {"15555215554", "15555215556",
            "15555215558", "15555215560", "15555215562", "15555215564",
            "15555215566", "15555215568", "15555215570", "15555215572",
            "15555215574", "15555215576", "15555215578", "15555215580",
            "15555215582", "15555215584",};

    public static boolean CheckPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String phonenumber = telephonyManager.getLine1Number();
        Log.e("检测手机号", phonenumber + " 123123123");

        for (String number : known_numbers) {
            if (number.equalsIgnoreCase(phonenumber)) {
                Log.v("Result:", "Find PhoneNumber!");
                return true;
            }
        }
        Log.v("Result:", "Not Find PhoneNumber!");
        return false;
    }

    /*
    * 第三种 检测设备IDS 是不是 15 个 0
    *
    */
    private static String[] known_device_ids = {"000000000000000" // 默认ID
    };

    public static boolean CheckDeviceIDS(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String device_ids = telephonyManager.getDeviceId();
        Log.e("检测设备IDS", device_ids);

        for (String know_deviceid : known_device_ids) {
            if (know_deviceid.equalsIgnoreCase(device_ids)) {
                Log.v("Result:", "Find ids: 000000000000000!");
                return true;
            }
        }
        Log.v("Result:", "Not Find ids: 000000000000000!");
        return false;
    }

    /*
    *
    * 第四种 检测imesi is 是不是 31026  + 10个 0
    * */
    private static String[] known_imsi_ids = {"310260000000000" // 默认的 imsi id
    };

    public static boolean CheckImsiIDS(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String imsi_ids = telephonyManager.getSubscriberId();
        Log.e("检测imesi", imsi_ids);

        for (String know_imsi : known_imsi_ids) {
            if (know_imsi.equalsIgnoreCase(imsi_ids)) {
                Log.v("Result:", "Find imsi ids: 310260000000000!");
                return true;
            }
        }
        Log.v("Result:", "Not Find imsi ids: 310260000000000!");
        return false;
    }

    /*
    *  第五种 检测设备信息
    * */
    public static boolean CheckEmulatorBuild(Context context) {
        String BOARD = android.os.Build.BOARD;
        String BOOTLOADER = android.os.Build.BOOTLOADER;
        String BRAND = android.os.Build.BRAND;
        String DEVICE = android.os.Build.DEVICE;
        String HARDWARE = android.os.Build.HARDWARE;
        String MODEL = android.os.Build.MODEL;
        String PRODUCT = android.os.Build.PRODUCT;
        Log.e("检测设备信息", "BOARD:" + BOARD + " BOOTLOADER:" + BOOTLOADER + " BRAND:" + BRAND + " DEVICE:" + DEVICE
                + " HARDWARE:" + HARDWARE + " MODEL:" + MODEL + " PRODUCT:" + PRODUCT);
        if (BOARD == "unknown" || BOOTLOADER == "unknown"
                || BRAND == "generic" || DEVICE == "generic"
                || MODEL == "sdk" || PRODUCT == "sdk"
                || HARDWARE == "goldfish") {
            Log.v("Result:", "Find Emulator by EmulatorBuild!");
            return true;
        }
        Log.v("Result:", "Not Find Emulator by EmulatorBuild!");
        return false;
    }

    /*
    *   第六种  检测运营商 如果 是Android 那么就是 模拟器
    * */

    public static boolean CheckOperatorNameAndroid(Context context) {
        @SuppressLint("WrongConstant")
        String szOperatorName = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
        Log.e("检测运营商", szOperatorName);

        if (szOperatorName.toLowerCase().equals("android")) {
            Log.v("Result:", "Find Emulator by OperatorName!");
            return true;
        }
        Log.v("Result:", "Not Find Emulator by OperatorName!");
        return false;
    }

    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        Log.e("检测cpu", result);
        return result;
    }

    private static String[] known_bluestacks = {"/data/app/com.bluestacks.appmart-1.apk", "/data/app/com.bluestacks.BstCommandProcessor-1.apk",
            "/data/app/com.bluestacks.help-1.apk", "/data/app/com.bluestacks.home-1.apk", "/data/app/com.bluestacks.s2p-1.apk",
            "/data/app/com.bluestacks.searchapp-1.apk", "/data/bluestacks.prop", "/data/data/com.androVM.vmconfig",
            "/data/data/com.bluestacks.accelerometerui", "/data/data/com.bluestacks.appfinder", "/data/data/com.bluestacks.appmart",
            "/data/data/com.bluestacks.appsettings", "/data/data/com.bluestacks.BstCommandProcessor", "/data/data/com.bluestacks.bstfolder",
            "/data/data/com.bluestacks.help", "/data/data/com.bluestacks.home", "/data/data/com.bluestacks.s2p", "/data/data/com.bluestacks.searchapp",
            "/data/data/com.bluestacks.settings", "/data/data/com.bluestacks.setup", "/data/data/com.bluestacks.spotlight", "/mnt/prebundledapps/bluestacks.prop.orig"
    };

    public static boolean checkBlueStacksFiles() {
        for (int i = 0; i < known_bluestacks.length; i++) {
            String file_name = known_bluestacks[i];
            File qemu_file = new File(file_name);
            if (qemu_file.exists()) {
                Log.e("检测bluestacks", " Find BlueStacks Files!");
                return true;
            }
        }
        Log.e("检测bluestacks", "Not Find BlueStacks Files!");
        return false;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     *
     * @return true 为模拟器
     */
    public static boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // 光
        Sensor sensor9 = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY); // 距离
        return null == sensor9;
    }

    /**
     * 根据部分特征参数设备信息来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    public static boolean isFeatures() {
        Log.e("设备信息", Build.MANUFACTURER);
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

}