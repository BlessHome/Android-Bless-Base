package com.bless.base.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.bless.base.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 基本的操作共通抽取
 *
 * Created by ASLai on 2016/1/11 0011.
 */
public class Operation {


    public enum AnimaType {
        /**
         * 无动画
         */
        NONE,
        /**
         * 左右动画
         */
        LEFT_RIGHT,
        /**
         * 上下动画
         */
        TOP_BOTTOM,
        /**
         * 淡入淡出
         */
        FADE_IN_OUT
    }

    /**
     * 用于 intent 传输动画类型数据
     */
    final String ANIMATION_TYPE = "AnimationType";

    /** 激活Activity组件意图 **/
    private Intent mIntent = new Intent();

    /*** 上下文 **/
    private Activity mContext = null;

    private AnimaType mAnimationType;

    /** 日志输出标志 **/
    private final static String TAG = Operation.class.getSimpleName();

    public Operation(Activity mContext) {
        this.mContext = mContext;
        // 初始化参数
        Bundle bundle = mContext.getIntent().getExtras();
        if (bundle != null) {
            mAnimationType = (AnimaType) bundle.getSerializable(ANIMATION_TYPE);
        }
        if (mAnimationType == null) {
            mAnimationType = AnimaType.NONE;
        }
    }

    public Context getContext() {
        return mContext;
    }


    /**
     * 使用系统自带的分享功能
     *
     * @param shareTitle
     * @param shareUrl
     */
    public void toShareByLocal(String shareTitle, String shareUrl) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareTitle + "   " + shareUrl);
        Intent itn = Intent.createChooser(intent, "分享");
        mContext.startActivity(itn);
    }

    public boolean hasExtra(String pExtraKey) {
        if (mContext.getIntent() != null)
            return mContext.getIntent().hasExtra(pExtraKey);
        return false;
    }

    /**
     * 跳转Activity
     *
     * @param activity 需要跳转至的Activity
     */
    public void forward(Class activity) {
        forward(activity.getName());
    }

    /**
     * 跳转Activity
     *
     * @param className 需要跳转至的Activity
     */
    public void forward(String className) {
        forward(className, AnimaType.NONE);
    }

    /**
     * 跳转Activity
     *
     * @param className 需要跳转至的Activity
     * @param animaType 动画类型AnimaType.LEFT_RIGHT/TOP_BOTTOM/FADE_IN_OUT
     */
    public void forward(String className, AnimaType animaType) {
        mIntent.setClassName(mContext, className);
        mIntent.putExtra(ANIMATION_TYPE, animaType);
        mContext.startActivity(mIntent);

        int mAnimIn = 0;
        int mAnimOut = 0;

        if (animaType == AnimaType.LEFT_RIGHT)
        {
            mAnimIn = getAnimId(mContext, "base_slide_right_in");
            mAnimOut = getAnimId(mContext, "base_slide_left_out");
        }
        else if (animaType == AnimaType.TOP_BOTTOM)
        {
            mAnimIn = getAnimId(mContext, "base_push_bottom_in");
            mAnimOut = getAnimId(mContext, "base_push_up_out");
        }
        else if (animaType == AnimaType.FADE_IN_OUT)
        {
            mAnimIn = getAnimId(mContext, "base_fade_in");
            mAnimOut = getAnimId(mContext, "base_fade_out");
        }
        if (mAnimIn != 0 && mAnimOut != 0)
            mContext.overridePendingTransition(mAnimIn, mAnimOut);
    }

    /**
     * 代理Activity的finish() 方法
     */
    public void finish() {
        mContext.finish();
        int mAnimIn = 0;
        int mAnimOut = 0;
        if (mAnimationType == AnimaType.LEFT_RIGHT)
        {
            mAnimIn = getAnimId(mContext, "base_slide_left_in");
            mAnimOut = getAnimId(mContext, "base_slide_right_out");
        }
        else if (mAnimationType == AnimaType.TOP_BOTTOM)
        {
            mAnimIn = getAnimId(mContext, "base_push_up_in");
            mAnimOut = getAnimId(mContext, "base_push_bottom_out");
        }
        else if (mAnimationType == AnimaType.FADE_IN_OUT)
        {
            mAnimIn = getAnimId(mContext, "base_fade_in");
            mAnimOut = getAnimId(mContext, "base_fade_out");
        }
        if (mAnimIn != 0 && mAnimOut != 0)
            mContext.overridePendingTransition(mAnimIn, mAnimOut);
        mAnimationType = AnimaType.NONE;
    }

    /**
     * 设置传递参数
     *
     * @param key 参数key
     * @param value 数据传输对象
     */
    public void addParameter(String key, Bundle value) {
        mIntent.putExtra(key, value);
    }

    /**
     * 设置传递参数
     *
     * @param key 参数key
     * @param value 数据传输对象
     */
    public void addParameter(String key, Serializable value) {
        mIntent.putExtra(key, value);
    }

    /**
     * 设置传递参数
     *
     * @param key 参数key
     * @param value 数据传输对象
     */
    public void addParameter(String key, String value) {
        mIntent.putExtra(key, value);
    }

    /**
     * 获取跳转时设置的参数
     *
     * @param key
     * @return
     */
    public Object getParameter(String key) {
        Bundle extras = mContext.getIntent().getExtras();
        if(null == extras) return null;

        return mContext.getIntent().getExtras().get(key);
    }

    private Toast mToast;
    private Toast getToast() {
        if (mToast == null)
        {
            mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        }
        return mToast;
    }

    protected void showShortToast(int pResId) {
        showShortToast(mContext.getString(pResId));
    }

    protected void showShortToast(final String pMessage) {
        postUiRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    getToast().setText(pMessage);
                    getToast().setDuration(Toast.LENGTH_SHORT);
                    getToast().show();
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 通过反射来设置对话框是否要关闭，在表单校验时很管用， 因为在用户填写出错时点确定时默认Dialog会消失， 所以达不到校验的效果
     * 而mShowing字段就是用来控制是否要消失的，而它在Dialog中是私有变量， 所有只有通过反射去解决此问题
     *
     * @param pDialog
     * @param pIsClose
     */
    public void setAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
        try {
            Field field = pDialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(pDialog, pIsClose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    protected void hideKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager.isActive())
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private DHandler mHandler = new DHandler();

    static class DHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
        }
    }

    /**
     * 运行UI 线程
     * @param runnable
     * @return
     */
    protected boolean postUiRunnable(Runnable runnable) {
        return mHandler.post(runnable);
    }


    static int getAnimId(Context paramContext, String paramString) {
        try {
            return paramContext.getResources().getIdentifier(paramString, "anim", paramContext.getPackageName());
        } catch (Exception e) {
            Log.e("Operation", "Not fount layout: " + paramString);
            return 0;
        }
    }
}
