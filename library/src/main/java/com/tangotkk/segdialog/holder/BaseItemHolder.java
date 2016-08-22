package com.tangotkk.segdialog.holder;

import android.app.Dialog;
import android.view.View;

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

    }
}
