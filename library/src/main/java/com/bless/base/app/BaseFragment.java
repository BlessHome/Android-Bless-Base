package com.bless.base.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bless.base.app.core.OnActivityResultListener;

import java.lang.ref.WeakReference;

/**
 * Created by ASLai on 2016/1/11 0011.
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG = null;
    protected View mView;

    /** 当前Activity的弱引用，防止内存泄露 **/
    private WeakReference<Activity> mContextWR = null;

    /** 共通操作 **/
    protected Operation mOperation = null;

    private String mFragmentName;
    protected long mBaseStartTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContextWR = new WeakReference<Activity>(activity);
        mOperation = new Operation(activity);
        mBaseStartTime = System.currentTimeMillis();
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    protected Activity gainActivity() {
        if (mContextWR != null)
            return mContextWR.get();
        return null;
    }

    protected Operation getOperation() {
        return mOperation;
    }

    public void setFragmentName(String fragmentName) {
        this.mFragmentName = fragmentName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        return mView;
    }
    protected View findViewById(int redId){
        return mView.findViewById(redId);
    }
    protected abstract void initViews();
    /**
     * 定义布局文件
     *
     * @return
     */
    abstract protected int getLayoutId();

    public void invokeOnActivityResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        if (fragment instanceof OnActivityResultListener) {
            OnActivityResultListener listener = (OnActivityResultListener) fragment;
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }
}
