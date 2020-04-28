package com.http.client.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hongmingwei on 2018/1/11.
 * Email: 648600445@qq.com
 */

public class PreferenceTools {

    //action协议跳转记录
    protected static final String KEY_ACTION_LOG = "qilin_action_log";

    protected static final String FILE_NAME = "userInfo";

    /**
     * 修改action协议触发时间
     */
    public static boolean updateActionTime(Context context, long time){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(KEY_ACTION_LOG, time);
        return editor.commit();
    }

    /**
     * 查找action协议触发时间
     */
    public static long getActionTime(Context context){
        SharedPreferences sharePreference = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        long fir = sharePreference.getLong(KEY_ACTION_LOG, 0);
        return fir;
    }


}
