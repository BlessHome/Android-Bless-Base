package com.bless.base.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.*;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      16-8-19
 * 版本:      V1.0
 * 描述:      运行环境信息相关
 */
public class SysEnv {

    /***
     * Log输出标识
     **/
    private static final String TAG = SysEnv.class.getSimpleName();

    /**
     * 屏幕显示材质
     */
    private static final DisplayMetrics mDisplayMetrics = new DisplayMetrics();

    /**
     * 获取系统显示材质
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics;
    }

    /**
     * 操作系统名称(GT-I9100G)
     *
     * @return
     */
    public static String getBuildModel() {
        return Build.MODEL;
    }

    /**
     * 操作系统名称(I9100G)
     *
     * @return
     */
    public static String getBuildDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 操作系统版本(4.4)
     *
     * @return
     */
    public static String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取应用程序版本（versionName）
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
            return "";
        }

        return info.versionName;
    }

    /**
     * 获取应用程序版本（versionName）
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
            return -1;
        }

        return info.versionCode;
    }

    /**
     * 获取系统内核版本
     *
     * @return
     */
    public static String getKernelVersion() {
        String strVersion = "";
        FileReader mFileReader = null;
        BufferedReader mBufferedReader = null;
        try {
            mFileReader = new FileReader("/proc/version");
            mBufferedReader = new BufferedReader(mFileReader, 8192);
            String str2 = mBufferedReader.readLine();
            strVersion = str2.split("\\s+")[2];//KernelVersion

        } catch (Exception e) {
            Log.e(TAG, "获取系统内核版本失败，原因：" + e.getMessage());
        } finally {
            try {
                mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strVersion;
    }

    /**
     * 获取运行时间
     *
     * @return 运行时间(单位/s)
     */
    public static long getRunTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        return ut;
    }

    /**
     * 获取可用内存
     *
     * @param mContext 上下文
     * @return
     */
    public static long gainUnusedMemory(Context mContext) {
        long MEM_UNUSED = 0L;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }

    /**
     * 获取总内存
     *
     * @return
     */
    public static long gainTotalMemory() {
        long mTotal = 0;
        // /proc/meminfo读出的内核信息进行解析
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');
            // 截取字符串信息

            content = content.substring(begin + 1, end).trim();
            mTotal = Integer.parseInt(content);
        } catch (Exception e) {
            Log.e(TAG, "获取总内存失败，原因：" + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return mTotal;
    }

    /**
     * 判断是否为模拟器环境需要权限
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @param mContext 上下文
     * @return
     */
    public static boolean isEmulator(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = telephonyManager.getDeviceId();
        // 如果 运行的 是一个 模拟器
        if (deviceID == null || deviceID.trim().length() == 0
                || deviceID.matches("0+")) {
            return true;
        }
        return false;
    }

    /**
     * 本机手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneMumber(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    /**
     * 设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 设备IMEI号码
     *
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
    }

    /**
     * 设备IMSI号码
     *
     * @param context
     * @return
     */
    public static String getSubscriberId(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获取摄像头支持的分辨率
     *
     * @param camera
     * @return
     */
    public static List<Camera.Size> getSupportedPreviewSizes(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        return sizeList;
    }

}
