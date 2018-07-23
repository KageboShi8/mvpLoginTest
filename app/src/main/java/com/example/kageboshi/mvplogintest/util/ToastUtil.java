package com.example.kageboshi.mvplogintest.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void show(Context context,String text){
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();
    }

    public static void show(Context context,int resId){
        Toast toast=Toast.makeText(context,null,Toast.LENGTH_SHORT);
        String string = context.getResources().getString(resId);
        if (null!=string){
            toast.setText(string);
        }
        toast.show();
    }
}
