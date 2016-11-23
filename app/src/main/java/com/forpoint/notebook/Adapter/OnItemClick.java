package com.forpoint.notebook.Adapter;

import android.view.View;

/**
 * Created by Administrator on 2016/11/14.
 */
public interface OnItemClick {
    void onItemClick(View view , String data);
    void onLongItemClick(View view , String data);
}