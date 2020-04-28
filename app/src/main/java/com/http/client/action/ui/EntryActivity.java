package com.http.client.action.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.http.client.MainActivity;
import com.http.client.action.core.ActionManager;
import com.http.client.utils.LogUtils;
import com.http.client.utils.StringUtils;

import java.util.List;

/**
 */
public class EntryActivity extends Activity {

    public static final String PACKAGE_NAME = "com.http.client";
    /**
     * tag
     */
    private static final String TAG = EntryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String dataPath = intent.getDataString();
            if (StringUtils.isNotBlank(dataPath)) {
                Log.e(TAG, "onCreate: =================啦啦啦啦=====");
                if (appMainActivityIsRunning()){
                    appToTopStack();
                    ActionManager manager = new ActionManager(MainActivity.getInstance(), dataPath);
                    if (manager.isValidAction()) {
                        manager.processAction();
                    }
                }else {
                    LogUtils.d(TAG, "dataPath =========  " + dataPath);
                    moveTaskToBack(true);
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }


    /**
     * 判断程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    public boolean appMainActivityIsRunning(){
        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(PACKAGE_NAME) &&
                    info.baseActivity.getShortClassName().equals(".MainActivity")) {
                return true;
            }
        }
        return false;
    }

    public void appToTopStack() {
        ActivityManager manager = (ActivityManager) EntryActivity.this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task_info = manager
                .getRunningTasks(20);
        String className = "";

        for (int i = 0; i < task_info.size(); i++)
        {

            if (PACKAGE_NAME.equals(task_info
                    .get(i).topActivity.getPackageName())){

                className = task_info.get(i).topActivity
                        .getClassName();

                //这里是指从后台返回到前台  前两个的是关键
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                try {
                    intent.setComponent(new ComponentName(
                            EntryActivity.this, Class.forName(className)));
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    EntryActivity.this.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
