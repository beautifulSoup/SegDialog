package com.tangotkk.segdialog.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kris on 16/8/21.
 */
public abstract class ViewItemHolder extends BaseItemHolder {

    View mView;

    public ViewItemHolder(View view){
        mView = view;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent) {
        return mView;
    }

}
