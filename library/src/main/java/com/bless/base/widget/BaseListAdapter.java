package com.bless.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bless.base.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      16-8-19
 * 版本:      V1.0
 * 描述:      自定义适配器基础类
 */
public abstract class BaseListAdapter<E> extends BaseAdapter {

    private static String TAG;

    public List<E> mList;

    private Context mContext;

    private LayoutInflater mInflater;

    public BaseListAdapter(Context context) {
        this(context, new ArrayList<E>());
    }

    public BaseListAdapter(Context context, List<E> list) {
        super();
        TAG = this.getClass().getSimpleName().toString();
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public List<E> getList() {
        return mList;
    }

    public void setList(List<E> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void add(E e) {
        this.mList.add(e);
        notifyDataSetChanged();
    }

    public void addAll(List<E> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.mList.remove(position);
        notifyDataSetChanged();
    }

    public void remove(E e) {
        this.mList.remove(e);
        notifyDataSetChanged();
    }

    public void removeAll(List<E> list) {
        this.mList.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public E getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = bindView(position, convertView, parent);
        // 绑定内部点击监听
        addInternalClickListener(convertView, position, mList.get(position));
        return convertView;
    }

    public abstract View bindView(int position, View convertView, ViewGroup parent);

    /**
     * 简化 ViewHolder 操作，直接获取 convertView 里对应 id 的 view
     *
     * @param convertView
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T parseView(View convertView, int id) {
        return (T) ViewHolder.get(convertView, id);
    }

    // adapter中的内部点击事件
    public Map<Integer, onInternalClickListener> canClickItem;

    private void addInternalClickListener(final View convertView, final Integer position, final Object valuesMap) {
        if (canClickItem != null) {
            for (Integer key : canClickItem.keySet()) {
                View inView = convertView.findViewById(key);
                final onInternalClickListener inviewListener = canClickItem.get(key);
                if (inView != null && inviewListener != null) {
                    inView.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            inviewListener.OnClickListener(convertView, v, position, valuesMap);
                        }
                    });
                }
            }
        }
    }

    public void setOnInViewClickListener(Integer key, onInternalClickListener onClickListener) {
        if (canClickItem == null)
            canClickItem = new HashMap<Integer, onInternalClickListener>();
        canClickItem.put(key, onClickListener);
    }

    public interface onInternalClickListener {
        public void OnClickListener(View parentV, View v, Integer position, Object values);
    }

    Toast mToast;

    public void showToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            ((Activity) mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });
        }
    }

    public void log(String msg) {
        Log.d(TAG, msg);
    }

    public void startActivity(Intent paramIntent, Class<?> cls) {
        paramIntent.setClass(mContext, cls);
        mContext.startActivity(paramIntent);
    }
}

/**
 * ViewHolder 的简化
 */
final class ViewHolder {
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
