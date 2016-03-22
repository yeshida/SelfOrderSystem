package com.centerm.selforder.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * 跟View相关的工具类
 * 
 * @author wanliang527
 * @date 2014-1-17
 * 
 */
public class ViewUtils {

    public final static LayoutParams MATCH_MATCH = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    public final static LayoutParams MATCH_WRAP = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    public final static LayoutParams WRAP_MATCH = new LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    public final static LayoutParams WRAP_WRAP = new LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    /**
     * 显示Toast
     * 
     * @param c
     * @param txt
     */
    public static void showToast(Context c, String txt) {
        Toast.makeText(c, txt, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     * 
     * @param c
     * @param stringId
     */
    public static void showToast(Context c, int stringId) {
        Toast.makeText(c, stringId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建一个自定义的Dialog。如果需要设置Dialog的窗口大小，使用Dialog.getWindow().getAttributes()
     * 
     * @param c
     * @param view
     * @return
     */
//    public static Dialog createDialog(Context c, View view) {
//        Dialog dialog = new Dialog(c, R.style.);
//        dialog.setContentView(view);
//        return dialog;
//    }
    
    
    /**
     * 获取LayoutInflater对象
     * 
     * @param context
     * @return
     */
    public static LayoutInflater getInflater(Context context) {
        return (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 设置View的背景图
     * 
     * @param v
     * @param obj
     *            可以是Drawable对象也可以是colorid、resId
     * @return 设置成功返回true，否则返回false
     */
    public static boolean setViewBackground(View v, Object obj) {
        if (v == null || obj == null)
            return false;
        if (obj instanceof Drawable) {
            v.setBackgroundDrawable((Drawable) obj);
            return true;
        }
        if (obj instanceof Integer) {
            try {
                v.setBackgroundResource((Integer) obj);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    v.setBackgroundColor((Integer) obj);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

}
