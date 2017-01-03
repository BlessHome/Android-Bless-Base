package com.bless.base.app;

import android.app.Activity;
import android.os.Bundle;
import com.bless.base.util.Log;


import java.lang.ref.WeakReference;

public abstract class BaseActivity extends Activity {

	protected String TAG;

	/** 当前Activity的弱引用，防止内存泄露 **/
	private WeakReference<Activity> mContextWR = null;

	/** 共通操作 **/
	protected Operation mOperation = null;
	
	/* ******************************* 【Activity LifeCycle For Debug】 ****************************************** */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 将当前Activity压入栈
		mContextWR = new WeakReference<Activity>(this);

		// 实例化共通操作
		mOperation = new Operation(this);

		TAG = this.getClass().getSimpleName().toString();
		Log.d(TAG, " onCreate() invoked!!");
		super.onCreate(savedInstanceState);
		initCreate(savedInstanceState);
	}

	/**
	 * 初始化
	 *
	 * @param savedInstanceState
	 */
	protected abstract void initCreate(Bundle savedInstanceState);
	
	@Override
	protected void onStart() {
		Log.d(TAG, " onStart() invoked!!");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.d(TAG, " onRestart() invoked!!");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, " onResume() invoked!!");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, " onPause() invoked!!");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, " onStop() invoked!!");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, " onDestroy() invoked!!");
		super.onDestroy();
	}

	/**
	 * 当前Activity的弱引用
	 *
	 * @return
	 */
	protected WeakReference<Activity> getWeakReference() {
		return mContextWR;
	}


	/**
	 * 获取当前Activity
	 *
	 * @return
	 */
	public Activity getContext() {
		if (mContextWR != null)
			return mContextWR.get();
		return null;
	}

	/**
	 * 获取共通操作机能
	 */
	public Operation getOperation() {
		return this.mOperation;
	}

}
