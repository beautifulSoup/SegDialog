package com.tangotkk.segdialog.builder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tangotkk.segdialog.R;
import com.tangotkk.segdialog.holder.ItemHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 16/8/22.
 */
public class DialogBuilder {

    List<ItemHolder> mListItemHolder = new ArrayList<>();

    @DrawableRes int mBackgroundRes = -1;

    int mMarginLeft = -1;

    int mMarginRight = -1;

    WeakReference<Activity> mARef;

    public DialogBuilder(Activity activity){
        mARef = new WeakReference<Activity>(activity);
    }

    public DialogBuilder addItem(ItemHolder item){
        mListItemHolder.add(item);
        return this;
    }


    public DialogBuilder setBackgroundRes(@DrawableRes int backRes){
        mBackgroundRes = backRes;
        return this;
    }

    public DialogBuilder setHorizontalMargin(int marginLeft, int marginRight){
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
        return this;
    }

    /**
     *
     * @return null when the Activity is gc
     */
    @Nullable
    public Dialog buildAndShow(){
        if(mARef.get() == null){
            return null;
        }
        AlertDialog dialog = new AlertDialog.Builder(mARef.get())
                .create();
        dialog.show();

        dialog.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#00000000"));
        LayoutInflater inflater = LayoutInflater.from(mARef.get());
        View contentView = inflater.inflate(R.layout.dialog_wrapper, null, false);

        renderMargin(dialog, contentView);
        renderBackground(contentView);
        renderChildren(dialog, inflater, contentView, mListItemHolder);
        dialog.getWindow().setContentView(contentView);
        return dialog;
    }



    protected void renderMargin(Dialog dialog, View view){
        if(mMarginLeft != -1 || mMarginRight != -1){
            // make the dialog match parent in horizontal direction
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setAttributes(lp);
            ViewGroup wrapper = (ViewGroup) view.findViewById(R.id.dialog_wrapper);
            RelativeLayout.LayoutParams wrapperLp = (RelativeLayout.LayoutParams) wrapper.getLayoutParams();
            wrapperLp.leftMargin = mMarginLeft != -1 ? mMarginLeft : 0;
            wrapperLp.rightMargin = mMarginRight != -1 ? mMarginRight : 0;
            view.setLayoutParams(wrapperLp);
        }
    }

    protected void renderBackground(View view){
        if(mBackgroundRes != -1){
            ViewGroup wrapper = (ViewGroup) view.findViewById(R.id.dialog_wrapper);
            wrapper.setBackgroundResource(mBackgroundRes);
        }
    }


    protected void renderChildren(Dialog dialog, LayoutInflater inflater, View view, List<ItemHolder> holders){
        ViewGroup wrapper = (ViewGroup) view.findViewById(R.id.dialog_wrapper);
        for(ItemHolder item : holders){
            View itemView = item.createView(inflater, wrapper);
            item.bindEvent(dialog, itemView);
            wrapper.addView(itemView);
        }
    }



}
