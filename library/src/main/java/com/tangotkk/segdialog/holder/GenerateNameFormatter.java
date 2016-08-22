package com.tangotkk.segdialog.holder;

import java.util.Locale;

/**
 * Created by kris on 16/8/21.
 * 生成
 *
 */
public class GenerateNameFormatter {

    private static final String PACKAGE_NAME = GenerateNameFormatter.class.getPackage().getName();
    private static final String NAME_PREFIX = "Seg_";

    private static final String NAME_POSTFIX = "Builder";

    public static String formatClickBinderName(String clsName){
        return String.format(Locale.ENGLISH, "%s.%s%s%s", PACKAGE_NAME, NAME_PREFIX, clsName, NAME_POSTFIX);
    }


}
