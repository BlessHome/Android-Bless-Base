package com.bless.base.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Log {


    private static String TAG = "Android-Bless";
    private static boolean isDebug = false;

    public static void setTag(String tag) {
        TAG = tag;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        Log.isDebug = isDebug;
    }

    public static boolean isLoggable(String paramString, int paramInt) {
        return android.util.Log.isLoggable(paramString, paramInt);
    }

    public static void v(String tag, String msg) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.v(TAG, tag + "---" + msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.v(TAG, tag + "---" + msg, tr);
    }

    public static void d(String tag, String msg) {
        if (!isDebug) return;
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.d(TAG, tag + "---" + msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (!isDebug) return;
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.d(TAG, tag + "---" + msg, tr);
    }

    public static void i(String tag, String msg) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.i(TAG, tag + "---" + msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.i(TAG, tag + "---" + msg, tr);
    }

    public static void w(String tag, String msg) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.w(TAG, tag + "---" + msg);
    }

    public static void w(String tag, Throwable tr) {
        android.util.Log.w(tag, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.w(TAG, tag + "---" + msg, tr);
    }

    public static void e(String tag, String msg) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.e(TAG, tag + "---" + msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.e(TAG, tag + "---" + msg, tr);
    }

    public static void wtf(String tag, String msg) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.wtf(TAG, tag + "---" + msg);
    }

    public static void wtf(String tag, Throwable tr) {
        android.util.Log.wtf(tag, tr);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (msg == null)
            msg = "this is a null message in " + tag;
        android.util.Log.wtf(TAG, tag + "---" + msg, tr);
    }

    public static String getStackTraceString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    public static void println(int priority, String tag, String msg) {
        android.util.Log.println(priority, TAG, tag + "---" + msg);
    }

    /**
     * 读取当前应用的Logcat
     *
     * @return
     */
    public static String readLogCat(Context context) {
        StringBuilder l = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d -v time " + context.getPackageName() + ":E *:S");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                l.append(line);
            }
        } catch (IOException e) {
        }
        return l.toString();
    }
}
