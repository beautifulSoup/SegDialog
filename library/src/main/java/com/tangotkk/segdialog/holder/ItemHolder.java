package com.tangotkk.segdialog.holder;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kris on 16/8/21.
 */
public interface ItemHolder {

    void bindEvent(Dialog dialog, View view);

    View createView(LayoutInflater inflater, ViewGroup parent);

    void onViewClick(Dialog dialog, View view);

}
