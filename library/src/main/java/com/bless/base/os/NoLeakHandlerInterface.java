package com.bless.base.os;

import android.os.Looper;
import android.os.Message;

/**
 * 实现此接口以替代Handler
 * 
 * @see NoLeakHandler
 * @see NoLeakHandler#NoLeakHandler(NoLeakHandlerInterface)
 * @see NoLeakHandler#NoLeakHandler(Looper, NoLeakHandlerInterface)
 * @see NoLeakHandler#innerHandler()
 */
public interface NoLeakHandlerInterface {
    boolean isValid();

    void handleMessage(Message msg);
}
