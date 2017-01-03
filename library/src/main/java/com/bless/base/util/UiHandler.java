package com.bless.base.util;


import android.os.Looper;

import com.bless.base.os.NoLeakHandler;

/**
 * 封装了在<b>子线程时</b>到<b>主线程</b>运行一段逻辑的操作.
 *
 */
public class UiHandler {

    private static NoLeakHandler sUiHandler;


    /**
     * 在主线程运行一段逻辑
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        initUIHandlerIfNeed();
        sUiHandler.handler().post(runnable);
    }

    /**
     * 在主线程延时运行一段逻辑
     *
     * @param runnable
     * @param delayMills
     */
    public static void runOnUiThreadDelayed(Runnable runnable, long delayMills) {
        initUIHandlerIfNeed();
        sUiHandler.handler().postDelayed(runnable, delayMills);
    }

    private static void initUIHandlerIfNeed() {
        if (sUiHandler == null) {
            synchronized (UiHandler.class) {
                if (sUiHandler == null || sUiHandler.handler() == null) {
                    sUiHandler = new NoLeakHandler(Looper.getMainLooper());
                }
            }
        }
    }
}
