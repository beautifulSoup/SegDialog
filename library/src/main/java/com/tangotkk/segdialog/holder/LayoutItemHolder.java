package com.tangotkk.segdialog.holder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kris on 16/8/21.
 */
public abstract class LayoutItemHolder extends BaseItemHolder {

    @LayoutRes int mLayoutRes;

    public LayoutItemHolder(@LayoutRes int layoutRes){
        mLayoutRes = layoutRes;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(mLayoutRes, parent, false);
    }

}
