package com.bless.base.app.core;

import android.content.Intent;

public interface OnActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
