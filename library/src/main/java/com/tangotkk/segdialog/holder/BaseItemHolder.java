package com.tangotkk.segdialog.holder;

import android.app.Dialog;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by kris on 16/8/21.
 */
public abstract class BaseItemHolder implements ItemHolder {


    @Override
    public void bindEvent(final Dialog dialog, View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClick(dialog, v);
            }
        });

        String clzName = this.getClass().getSimpleName();
        //bind click event
        try {
            Class<? extends ClickEventBinder> binderClz = (Class<? extends ClickEventBinder>) Class.forName(GenerateNameFormatter.formatClickBinderName(clzName));
            Method method = binderClz.getMethod("onClick", View.class);
            method.invoke(null, view);
        } catch (Exception e) {
            Log.e(e.getMessage(), "反射单击事件绑定器失败",e);
        }
    }
}
